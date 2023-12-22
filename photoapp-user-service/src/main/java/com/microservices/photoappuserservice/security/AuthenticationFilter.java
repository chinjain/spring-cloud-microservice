package com.microservices.photoappuserservice.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.photoappuserservice.model.LoginRequestModel;
import com.microservices.photoappuserservice.model.UserDto;
import com.microservices.photoappuserservice.userservices.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/*
	 * Spring will perform the authentication on the given username and password
	 * with the help oof this class
	 */

	private String SECRET_KEY = "hfgry463hf746hf573ydh475fhy5739hfgry463hf746hf573ydh475fhy5739hfgry463hf746hf573ydh475fhy5739";

	private UserService userService;
	private Environment environment;

	public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,Environment environment) {
		super(authenticationManager);
		this.userService = userService;
		this.environment = environment;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			System.out.println("AuthenticationFilter.attemptAuthentication()");
			System.out.println(request.getInputStream().toString());

			LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDetails = userService.getUserByEmail(username);
		Instant now = Instant.now();
		byte[] secretKey = Base64.getEncoder().encode(SECRET_KEY.getBytes());
		SecretKey key = new SecretKeySpec(secretKey, SignatureAlgorithm.HS512.getJcaName());

		String token = Jwts.builder().setSubject(userDetails.getUserID())
				.setExpiration(Date.from(now.plusMillis(Long.valueOf(environment.getProperty("token.expiry"))))).setIssuedAt(Date.from(now))
				.signWith(key, SignatureAlgorithm.HS512).compact();

		response.addHeader("token", "Bearer " + token);
		response.addHeader("userId", userDetails.getUserID());
	}

}
