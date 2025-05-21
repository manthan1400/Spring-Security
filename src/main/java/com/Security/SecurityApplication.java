package com.Security;

import com.Security.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {

	private static final Logger logger = LoggerFactory.getLogger(SecurityApplication.class);

	public static void main(String[] args) {


		SpringApplication.run(SecurityApplication.class, args);

		System.out.println("THis is spring security");
	}

}
