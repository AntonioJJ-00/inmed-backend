package com.inmed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InmedBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmedBackendApplication.class, args);
	}

}
