package com.microservices.photoappuserservice.model;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModelRequest {

	@NotNull(message = "First name can not be null")
	@Size(min = 2, message = "Not be less than 2 character")
	private String firstName;

	@NotNull(message = "First name can not be null")
	@Size(min = 2, message = "Not be less than 2 character")
	private String lastName;

	@NotNull(message = "Password can not be null")
	@Size(min = 8, max = 16, message = "password must be equal or greater than 8 character and less than equal to 16 character")
	private String password;

	@NotNull(message = "First name can not be null")
	@Email
	@Column(unique = true,nullable = false)
	private String email;

	public UserModelRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserModelRequest(String firstName, String lastName, String password, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
