package com.authservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
		@Bean
		public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
	
		http.authorizeHttpRequests( 
				req->{
					//req.anyRequest().permitAll();	#if use this both urls are open
					 
					req.requestMatchers("/api/v1/auth/welcome").permitAll().anyRequest().authenticated();
					// if it use this only open welcome url imp for that
					}
				);		
	return http.build();
	}
}

