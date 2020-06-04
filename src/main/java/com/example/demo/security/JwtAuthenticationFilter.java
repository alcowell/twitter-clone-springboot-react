package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class obtained Jwt token from request header and store
 * authenticated user's details in security context,<tt>SecurityContextHolder</tt>.
 *
 * @author yosuk
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * Validate Jwt token taken from request header and
	 * get user id from jwt token.
	 */
	@Autowired
	private JwtTokenProvider tokenProvider;

	/**
	 * Get the user's details from database.
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Output error when you fail to set user authentication
	 * in security context.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	/**
	 * Set the authenticated user's details in security context.
	 * User's details are loaded from database by using user's ID obtained
	 * from Jwt token.
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param FilterChain
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);

			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromJwt(jwt);

				UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch (Exception e) {
			logger.error("Could not set user authentication in security context",e);
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Get Jwt token from user's request header.
	 * @param HttpRequest
	 * @return Jwttoken/null If the request has jwttoken in its header,
	 *                        return Jwt token, otherwise null returned.
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
