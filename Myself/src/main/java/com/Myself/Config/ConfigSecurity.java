package com.Myself.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				req->{
					req.requestMatchers("api/v1/user/welcome").permitAll()
					.anyRequest().authenticated();
					
					
				}
				
				);
		  return http.build();
	}

}
