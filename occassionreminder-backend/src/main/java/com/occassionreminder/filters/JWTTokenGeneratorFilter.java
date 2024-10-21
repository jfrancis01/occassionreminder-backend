package com.occassionreminder.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.occassionreminder.constants.MyConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null != authentication) {
			Environment env = getEnvironment();
			if(null != env) {
				String secret = env.getProperty(MyConstants.JWT_SECRET_KEY, MyConstants.DEFUALT_SECRET_KEY);
				SecretKey secrectKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
				String jwt = Jwts.builder().issuer("Occassions Reminder").subject("JWT Token")
				.claim("username", authentication.getName())
				.claim("authorities", authentication.getAuthorities().stream().map(
						GrantedAuthority::getAuthority
						).collect(Collectors.joining(",")))
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + 3600 * 1000))
				.signWith(secrectKey).compact();
				response.setHeader(MyConstants.JWT_HEADER, jwt);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	/**
	 * Can be overridden in subclasses for custom filtering control,
	 * returning {@code true} to avoid filtering of the given request.
	 * <p>The default implementation always returns {@code false}.
	 * @param request current HTTP request
	 * @return whether the given request should <i>not</i> be filtered
	 * @throws ServletException in case of errors
	 */
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/occassionsreminder/login");
	}

}
