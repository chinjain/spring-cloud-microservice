package com.microservices.photoappuserservice.model;

import java.time.LocalDateTime;

public class CustomErrorResponse {

	private final int status;
	private final String message;
	private final LocalDateTime timestamp;

	public CustomErrorResponse(int status, String message, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	

}
