package com.example.servertracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerTrackerApplication.class, args);
	}
	@GetMapping("/victory")
	public ResponseEntity<?> victory(){

		return new ResponseEntity<>("Win Hackthon", HttpStatusCode.valueOf(200));
	}

}
