package com.occassionreminder.securityconfig;

import static org.springframework.security.config.Customizer.withDefaults;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.occassionreminder.filters.CsrfCookieFilter;
import com.occassionreminder.filters.JWTTokenGeneratorFilter;
import com.occassionreminder.filters.JWTTokenValidationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {
	
	final String REGISTER_URL = "/occassionsreminder/register";
	final String LOGIN_URL = "/occassionsreminder/login";
	final String OCCASSIONS_LIST_URL = "/occassionsreminder/occassions";
	final String OCCASSION_GET= "/occassionsreminder/occassion";
	final String OCCASIONS_ADD = "/occassionsreminder/add";
	final String OCCASSIONS_EDIT = "/occassionsreminder/edit";
	final String USER_GET= "/occassionsreminder/getUser";
	final String USER_EDIT= "/occassionsreminder/editUser";
	final String H2_CONSOLE_URL = "/h2/**";
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler crsfTokenRequestHandler = new CsrfTokenRequestAttributeHandler();
		http
		//.securityContext(contextConfig -> contextConfig.requireExplicitSave(false)) // removed and only used for JSESSIONID
		//.sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration myconfig = new CorsConfiguration();
				myconfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				myconfig.setAllowedMethods(Collections.singletonList("*"));
				myconfig.setAllowCredentials(true);
				myconfig.setAllowedHeaders(Collections.singletonList("*"));
				myconfig.setExposedHeaders(Arrays.asList("Authorization"));
				myconfig.setMaxAge(3600L);
				return myconfig;
			}
		}))
		//.csrf(csrf -> csrf.disable())
				
				  .csrf(csrfConfig ->
				  csrfConfig.csrfTokenRequestHandler(crsfTokenRequestHandler)
				  .ignoringRequestMatchers(REGISTER_URL, H2_CONSOLE_URL)
				  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				  
		.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)		 
		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JWTTokenValidationFilter(), BasicAuthenticationFilter.class)
		.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers(H2_CONSOLE_URL).permitAll()
				.requestMatchers(REGISTER_URL, "/error", "/invalidSession").permitAll()
				.requestMatchers(LOGIN_URL,OCCASSIONS_LIST_URL, OCCASSION_GET, OCCASSIONS_EDIT, OCCASIONS_ADD, USER_GET, USER_EDIT).authenticated())
		.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		//http.formLogin(flc -> flc.disable()); // this is a login page with a user name and password using Spring MVC
		http.formLogin(withDefaults());
		http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())); // this is header based
		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
