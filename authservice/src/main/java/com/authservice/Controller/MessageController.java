package com.authservice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class MessageController {
	@GetMapping("/welcome")
	public String getWelcome() {
		return "welcome";
	}
	@GetMapping("/hello")
	public String getHello() {
		return "hello ji";
	}

}
// http://localhost:8080/api/v1/auth/welcome