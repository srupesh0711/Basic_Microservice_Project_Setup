package com.Myself.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

}
