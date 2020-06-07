package com.example.demo.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * This class generate and verifying Jwt token after a user logs in successfully.
 * And validated Jwt token sent in the Authorization header of the requests.
 *
 * @author yosuk
 *
 */
@Component
public class JwtTokenProvider {

	/**
	 * Logger used for outputting errors because of invalid Jwt token.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	/**
	 * Secret key used for signature of Jwt token.
	 */
	@Value("${app.jwtSecret")
	private String jwtSecret;

	/**
	 * Time until Jwt token becomes invalid.
	 */
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	/**
	 * This method generate Jwt token based on authenticated user information,
	 * current time and secret key.
	 *
	 * @param authentication An authenticated principal once the request has been processed.
	 * @return JwtsToken Jwt token consists of three elements: headers, body and signature.
	 */
	public String generateToken(Authentication authentication) {
	
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		//Setting expiry date of valid Jwt token
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				//Body part of Jwt has subjects generated based on user's id.
				.setSubject(Long.toString(userPrincipal.getId()))

				//Body also has issue generated based on date.
				.setIssuedAt(new Date())

				//This Jwts token has expiration date set by current time and jwtExpirationInMs.
				.setExpiration(expiryDate)

				//Token has signature generated based on algorithm and jwtSecret.
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	/**
	 * Get user's Id from Jwt token.
	 * @param token
	 * @return userId
	 */
	public Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		return Long.parseLong(claims.getSubject());
	}

	/**
	 * This method validates Jwt tokens. If it is invalid, this method outputs errors
	 * corresponding to the causes.
	 *
	 * @param authToken Jwt tokens send by users.
	 * @return true/false The result of validating Jwt tokens send by users.
	 *                     Return true if it is valid.
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}catch (SignatureException e) {
			logger.error("Invalid JWT signature");
		}catch (MalformedJwtException e) {
			logger.error("Invalid JWT token");
		}catch (ExpiredJwtException e) {
			logger.error("Expired JWT token");
		}catch (UnsupportedJwtException e) {
			logger.error("Unsupportd JWT token");
		}catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty");
		}
		return false;
	}

}
