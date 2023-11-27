package com.microservices.photoappaccountservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoappAccountServicesApplication implements CommandLineRunner {
	


	public static void main(String[] args) {
		SpringApplication.run(PhotoappAccountServicesApplication.class, args);
	}

	
	/*
	 * To store the Users detail we are using this right now !!
	 */
	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
