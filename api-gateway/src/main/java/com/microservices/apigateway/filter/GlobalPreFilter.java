package com.microservices.apigateway.filter;

import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalPreFilter implements GlobalFilter,Ordered {

	final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalPreFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("My pre filter executed");
		String requestPath = exchange.getRequest().getPath().toString();
		log.info("Request Path = >"  + exchange.getRequest().getURI().toString());
		
		HttpHeaders headers = exchange.getRequest().getHeaders();
		Set<String> headerName = headers.keySet();
		
		
		
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
