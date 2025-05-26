package com.Microservice_1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/message")
	public String getMessage() {
		return "hello Sadiya";
	}

}
