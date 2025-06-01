package com.authservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.DTO.APIResponse;
import com.authservice.DTO.LoginDto;
import com.authservice.DTO.UserDTO;
import com.authservice.Service.AuthService;
import com.authservice.Service.JWTService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthService authService;
	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> register(@RequestBody UserDTO dto){
		
		APIResponse<String> response = authService.register(dto);
		return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto dto){
		APIResponse<String> response = new APIResponse<>();
		
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
		
		try {
			 Authentication authenticate = authManager.authenticate(token);
			 
			 if(authenticate.isAuthenticated()) {
				 
				  String jwtToken = jwtService.generateToken(dto.getUsername(),
			      authenticate.getAuthorities().iterator().next().getAuthority());
				  
				 response.setMessage("Login Sucessful");
				 response.setStatus(200);
				 response.setData(jwtToken); // return JWT
				 return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 response.setMessage("Failed");
		 response.setStatus(401);
		 response.setData("Un-Authorized Access");
		 return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
	 }
		
}


// http://localhost:8080/api/v1/auth/welcome