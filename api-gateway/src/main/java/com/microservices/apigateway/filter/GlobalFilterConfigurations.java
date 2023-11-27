package com.microservices.apigateway.filter;

import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class GlobalFilterConfigurations {

	Logger log = org.slf4j.LoggerFactory.getLogger(GlobalFilterConfigurations.class);

	@Order(1)
	@Bean
	public GlobalFilter secondPreFilter() {
		return (exchange, chain) -> {
			log.trace("GlobalFilterConfigurations.secondPreFilter");
			log.info("My Second pre filter executed ..........");

			return chain.filter(exchange);
		};

	}

	@Order(3)
	@Bean
	public GlobalFilter thirdPreFilter() {
		return (exchange, chain) -> {
			log.trace("GlobalFilterConfigurations.secondPreFilter");
			log.info("My Third pre filter executed ..........");

			return chain.filter(exchange);
		};

	}

	@Order(2)
	@Bean
	public GlobalFilter fourthPreFilter() {
		return (exchange, chain) -> {
			log.trace("GlobalFilterConfigurations.secondPreFilter");
			log.info("My Fourth pre filter executed ..........");

			return chain.filter(exchange);
		};

	}

}
