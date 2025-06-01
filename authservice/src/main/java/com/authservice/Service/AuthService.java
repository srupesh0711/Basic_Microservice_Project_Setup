package com.authservice.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.DTO.APIResponse;
import com.authservice.DTO.UserDTO;
import com.authservice.Entity.User;
import com.authservice.Repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public APIResponse<String> register(UserDTO userDto){
		
		APIResponse<String> response = new APIResponse<>();
		// check whether username exists
		
		if(userRepository.existsByUsername(userDto.getUsername())) {
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User with Username already exists ");
			return response;
		}
		// check whether email exists
		
		if(userRepository.existsByEmail(userDto.getEmail())) {
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User with Email Id already exists");
			return response;
		}
		// encode the password before saving that to the database
		
		String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
		
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setPassword(encryptedPassword);
		user.setRole("ROLE_ADMIN");
		
		 User savedUser = userRepository.save(user);
		 if(savedUser==null) {
			 // custom exception
		 }
	
		// finally save the user user and return repsponse as APIResponse
		 
			response.setMessage("Registration Completed");
			response.setStatus(201);
			response.setData("User has been Registered");
			return response;
		
	}
	

}
