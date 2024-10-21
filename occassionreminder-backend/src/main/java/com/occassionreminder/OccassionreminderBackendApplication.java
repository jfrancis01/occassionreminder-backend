package com.occassionreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class OccassionreminderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OccassionreminderBackendApplication.class, args);
	}

}
