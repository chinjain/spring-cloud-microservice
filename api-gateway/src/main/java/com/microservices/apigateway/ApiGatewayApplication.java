package com.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.microservices.apigateway.filter")
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

//	@Bean
//	public RouteLocator routeLocator(RouteLocatorBuilder locatorBuilder) {
//		return locatorBuilder.routes().route("users-ws", r -> r.path("/users/**").uri("lb://users-ws"))
//				.route("account-service", r -> r.path("/account/**").uri("lb://account-service")).build();
//	}

}
