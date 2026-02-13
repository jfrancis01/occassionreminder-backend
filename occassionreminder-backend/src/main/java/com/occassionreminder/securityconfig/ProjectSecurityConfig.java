package com.occassionreminder.securityconfig;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
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
	
	@Autowired
	private Environment environment;
	
	final String REGISTER_URL = "/occassionsreminder/register";
	final String WLECOME_PUBLIC_URL = "/occassionsreminder/welcome";
	final String LOGIN_URL = "/occassionsreminder/login";
	final String OCCASSIONS_LIST_URL = "/occassionsreminder/occassions";
	final String OCCASSION_GET= "/occassionsreminder/occassion";
	final String OCCASIONS_ADD = "/occassionsreminder/add";
	final String OCCASSIONS_EDIT = "/occassionsreminder/edit";
	final String OCCASSIONS_DELETE = "/occassionsreminder/delete";
	final String USER_GET= "/occassionsreminder/getUser";
	final String USER_EDIT= "/occassionsreminder/editUser";
	final String H2_CONSOLE_URL = "/h2/**";
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler crsfTokenRequestHandler = new CsrfTokenRequestAttributeHandler();
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
		http
		//.securityContext(contextConfig -> contextConfig.requireExplicitSave(false)) // removed and only used for JSESSIONID
		.sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
				  .ignoringRequestMatchers(REGISTER_URL, H2_CONSOLE_URL, WLECOME_PUBLIC_URL)
				  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				  	 
		.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers(H2_CONSOLE_URL, REGISTER_URL, WLECOME_PUBLIC_URL, "/error").permitAll()
				.requestMatchers(LOGIN_URL,OCCASSIONS_LIST_URL, OCCASSION_GET, OCCASSIONS_EDIT, OCCASIONS_ADD, OCCASSIONS_DELETE, USER_GET, USER_EDIT).authenticated())
		.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		http.oauth2ResourceServer(rsc -> rsc.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
		return http.build();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
        // You can use a factory method with your issuer URI
        //String issuerUri = "https://your-authorization-server.com";
        return JwtDecoders.fromIssuerLocation(environment.getProperty("ISSUER_URI"));
    }		
	
}
