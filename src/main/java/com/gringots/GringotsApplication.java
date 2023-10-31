package com.gringots;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.gringots.config")
public class GringotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GringotsApplication.class, args);
	}

}
