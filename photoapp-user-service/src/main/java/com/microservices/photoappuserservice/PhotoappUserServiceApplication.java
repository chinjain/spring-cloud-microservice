package com.microservices.photoappuserservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.microservices.photoappuserservice.model.UserModelRequest;
import com.microservices.photoappuserservice.usercontroller.UserController;

import feign.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PhotoappUserServiceApplication implements CommandLineRunner {

	@Autowired
	private UserController controller;

	public static void main(String[] args) {
		SpringApplication.run(PhotoappUserServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 11; i++) {
			UserModelRequest modelRequest = new UserModelRequest();
			modelRequest.setFirstName("Sergeo" + "i");
			modelRequest.setEmail("sergeo" + i + "@gmail.com");
			modelRequest.setLastName("Kargapolac" + i);
			modelRequest.setPassword("123456");

			controller.createUser(modelRequest);
		}

		System.out.println("PhotoappUserServiceApplication.run()");
		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println(configuration.getMsg());
//		System.out.println("Value from Config server ->" + this.msg);

	}

	@Bean
	Logger.Level feignLogger() {
		return Logger.Level.FULL;
	}

}
