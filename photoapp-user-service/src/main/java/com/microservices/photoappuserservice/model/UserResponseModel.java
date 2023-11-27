package com.microservices.photoappuserservice.model;

import org.springframework.http.HttpStatus;

public class UserResponseModel {

	private HttpStatus status;
	private String code;
	private String firstName;
	private String lastName;
	private String email;
	private String userId;

	public UserResponseModel(HttpStatus status, String code) {
		super();
		this.status = status;
		this.code = code;
	}

	public UserResponseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
