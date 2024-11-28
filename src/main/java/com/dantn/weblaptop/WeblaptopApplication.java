package com.dantn.weblaptop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class WeblaptopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeblaptopApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return String.format("Hello world!");
	}
}
