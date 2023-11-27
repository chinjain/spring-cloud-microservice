package com.microservices.photoappuserservice.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto implements Serializable {

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
	private String email;

	private String userID;

	private String encryptedPassword;

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto(
			@NotNull(message = "First name can not be null") @Size(min = 2, message = "Not be less than 2 character") String firstName,
			@NotNull(message = "First name can not be null") @Size(min = 2, message = "Not be less than 2 character") String lastName,
			@NotNull(message = "Password can not be null") @Size(min = 8, max = 16, message = "password must be equal or greater than 8 character and less than equal to 16 character") String password,
			@NotNull(message = "First name can not be null") @Email String email, String userID,
			String encryptedPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.userID = userID;
		this.encryptedPassword = encryptedPassword;
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email="
				+ email + ", userID=" + userID + ", encryptedPassword=" + encryptedPassword + "]";
	}

}
