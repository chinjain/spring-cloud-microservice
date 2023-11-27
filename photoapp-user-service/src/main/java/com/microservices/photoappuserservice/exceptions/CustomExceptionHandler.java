package com.microservices.photoappuserservice.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.microservices.photoappuserservice.model.CustomErrorResponse;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleException(BadRequestException ex, WebRequest request) {

		CustomErrorResponse errorResponse = new CustomErrorResponse(

				HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()

		);
		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);

	}

}
