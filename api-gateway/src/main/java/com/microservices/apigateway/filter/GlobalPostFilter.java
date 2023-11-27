package com.microservices.apigateway.filter;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalPostFilter implements GlobalFilter,Ordered{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		Logger log = org.slf4j.LoggerFactory.getLogger(GlobalPostFilter.class);
		
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			// Some Code to execute this with one thread 
			log.info("My Last Post Filter is executed......");
		}));
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
