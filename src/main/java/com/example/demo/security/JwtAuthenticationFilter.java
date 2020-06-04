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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class
 * @author yosuk
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * This class validate Jwt token taken from request header and
	 * get user id from jwt token.
	 */
	@Autowired
	private JwtTokenProvider tokenProvider;

	/**
	 *
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//
			String jwt = getJwtFromRequest(request);

			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromJwt(jwt);

				UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				UsernamePasswordAuthenticationToken
			}
		}

	}

	/**
	 *
	 * @param 	request 			HttpRequest class
	 * @return 	Jwttoken or null	If the request has jwttoken in its header, return jwttoken, otherwise null returned.
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		//
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
