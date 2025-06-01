package com.authservice.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authservice.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private JWTFilter filter;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
			private String[] openUrl = {
			"/api/v1/auth/register",
			"/api/v1/auth/login", 
    		"/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
            };
			
		@Bean
		public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
				return config.getAuthenticationManager();
			
			}
		    
		@Bean
		public AuthenticationProvider authProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(customUserDetailsService);
			authProvider.setPasswordEncoder(getEncodedPassword());
			return authProvider;
		}
	
		@Bean
		public PasswordEncoder getEncodedPassword() {
				return new BCryptPasswordEncoder();
		}
	
		@Bean
		public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
	
			http  // Disable CSRF
	        .authorizeHttpRequests(auth -> 
	            auth.requestMatchers(openUrl).permitAll()
	           .requestMatchers("/api/v1/welcome/message").hasAnyRole("USER","ADMIN")
	            .anyRequest().authenticated()).authenticationProvider(authProvider())
	        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);


	    return http.csrf().disable().build();
		}

}

