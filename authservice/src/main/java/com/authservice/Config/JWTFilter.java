package com.authservice.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authservice.Service.CustomUserDetailsService;
import com.authservice.Service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		   String authHeader = request.getHeader("Authorization");
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            String jwt = authHeader.substring(7);
	            String username = jwtService.validateTokenAndRetrieveSubject(jwt);

	            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                var userDetails = customUserDetailsService.loadUserByUsername(username);
	                var authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());

	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        filterChain.doFilter(request, response);
		
	}

}
