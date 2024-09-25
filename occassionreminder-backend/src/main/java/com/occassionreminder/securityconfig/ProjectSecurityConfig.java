package com.occassionreminder.securityconfig;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
	
	final String REGISTER_URL = "/occassionsreminder/register";
	final String LOGIN_URL = "/occassionsreminder/login";
	final String OCCASSIONS_LIST_URL = "/occassionsreminder/occassions";
	final String OCCASIONS_ADD = "/occassionsreminder/add";
	final String OCCASSIONS_EDIT = "/occassionsreminder/edit";
	final String H2_CONSOLE_URL = "/h2/**";
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests
				.requestMatchers(H2_CONSOLE_URL).permitAll()
				.requestMatchers(REGISTER_URL, LOGIN_URL, "/error").permitAll()
				.requestMatchers(OCCASSIONS_LIST_URL, OCCASSIONS_EDIT, OCCASIONS_ADD).authenticated())
		.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		http.formLogin(withDefaults()); // this is a login page with a user name and password
		http.httpBasic(withDefaults()); // this is header based
		return http.build();
	}
}
