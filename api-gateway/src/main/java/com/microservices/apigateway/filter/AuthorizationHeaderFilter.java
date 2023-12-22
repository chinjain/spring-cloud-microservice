package com.microservices.apigateway.filter;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	@Autowired
	private Environment environment;
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}

	public static class Config {

	}

	@Override
	public org.springframework.cloud.gateway.filter.GatewayFilter apply(Config config) {
		return (exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization Header", org.springframework.http.HttpStatus.UNAUTHORIZED);
			}

			String authzHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authzHeader.replace("Bearer ", "");
			
			if(!isJwtValid(jwt)) {
				return onError(exchange, "Jwt token is not valid",org.springframework.http.HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err,
			org.springframework.http.HttpStatus scUnauthorized) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(scUnauthorized);
		return response.setComplete();
	}

	private boolean isJwtValid(String token) {
		System.err.println(token);
		boolean isValid = true;
		String tokenSecret = environment.getProperty("token.secret");
		byte[] key = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKey signKey = new SecretKeySpec(key, SignatureAlgorithm.HS512.getJcaName());
		String subject = null;
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(signKey).build();

		try {
			System.out.println("AuthorizationHeaderFilter.isJwtValid()");
			Jwt<Header, Claims> parseToken = jwtParser.parse(token);
			subject = parseToken.getBody().getSubject();
		} catch (Exception e) {
			isValid = false;
		}
		
		if(subject == null || subject.isEmpty()) {
			isValid = false;
		}
		

		return isValid;
	}
}
