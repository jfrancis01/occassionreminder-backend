package com.occassionreminder.securityconfig;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

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
		http
		//.sessionManagement(smc->smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
		.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration myconfig = new CorsConfiguration();
				myconfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				myconfig.setAllowedMethods(Collections.singletonList("*"));
				//myconfig.setAllowCredentials(true);
				myconfig.setAllowedHeaders(Collections.singletonList("*"));
				myconfig.setMaxAge(3600L);
				return myconfig;
			}
		}))
		.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests
				.requestMatchers(H2_CONSOLE_URL).permitAll()
				.requestMatchers(REGISTER_URL, LOGIN_URL, "/error", "/invalidSession").permitAll()
				.requestMatchers(OCCASSIONS_LIST_URL, OCCASSIONS_EDIT, OCCASIONS_ADD, PROFILE_EDIT).authenticated())
		.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		http.formLogin(withDefaults()); // this is a login page with a user name and password
		http.httpBasic(withDefaults()); // this is header based
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
