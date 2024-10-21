package com.occassionreminder.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.occassionreminder.constants.MyConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader(MyConstants.JWT_HEADER);
		if(null != jwt) {
			try {
				Environment env = getEnvironment();
				if(null != env) {
					String secret = env.getProperty(MyConstants.JWT_SECRET_KEY, MyConstants.DEFUALT_SECRET_KEY);
					SecretKey secrectKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
					if(null != secrectKey) {
						Claims claims = Jwts.parser().verifyWith(secrectKey)
								.build().parseSignedClaims(jwt).getPayload();
						
						String username = String.valueOf(claims.get("username"));
						String authorities = String.valueOf(claims.get("authorities"));
						Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, 
								AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			catch(Exception e) {
				throw new BadCredentialsException("Invalid Token");
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
		System.out.println("From Validator:" + request.getServletPath().toString());
		return request.getServletPath().equals("/occassionsreminder/login");
	}

}
