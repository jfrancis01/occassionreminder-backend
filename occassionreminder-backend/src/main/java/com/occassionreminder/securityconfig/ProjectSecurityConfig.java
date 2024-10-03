package com.occassionreminder.securityconfig;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {
	
	final String REGISTER_URL = "/occassionsreminder/register";
	final String LOGIN_URL = "/occassionsreminder/login";
	final String OCCASSIONS_LIST_URL = "/occassionsreminder/occassions";
	final String OCCASIONS_ADD = "/occassionsreminder/add";
	final String OCCASSIONS_EDIT = "/occassionsreminder/edit";
	final String PROFILE_EDIT = "/occassionsreminder/editUser";
	final String H2_CONSOLE_URL = "/h2/**";
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler crsfTokenRequestHandler = new CsrfTokenRequestAttributeHandler();
		http
		.securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
		.sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		//.sessionManagement(smc->smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
		.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration myconfig = new CorsConfiguration();
				myconfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				myconfig.setAllowedMethods(Collections.singletonList("*"));
				myconfig.setAllowCredentials(true);
				myconfig.setAllowedHeaders(Collections.singletonList("*"));
				myconfig.setMaxAge(3600L);
				return myconfig;
			}
		}))
		//.csrf(csrf -> csrf.disable())
				
				  .csrf(csrfConfig ->
				  csrfConfig.csrfTokenRequestHandler(crsfTokenRequestHandler)
				  .ignoringRequestMatchers(LOGIN_URL, REGISTER_URL, H2_CONSOLE_URL)
				  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				 
		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers(H2_CONSOLE_URL).permitAll()
				.requestMatchers(REGISTER_URL, LOGIN_URL, "/error", "/invalidSession").permitAll()
				.requestMatchers(OCCASSIONS_LIST_URL, OCCASSIONS_EDIT, OCCASIONS_ADD, PROFILE_EDIT).authenticated())
		.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		http.formLogin(flc -> flc.disable()); // this is a login page with a user name and password using Spring MVC
		http.httpBasic(withDefaults()); // this is header based
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
