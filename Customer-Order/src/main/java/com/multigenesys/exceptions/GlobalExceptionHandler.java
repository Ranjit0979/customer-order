package com.multigenesys.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<MyErrorDetails> globalException(CustomException ex, WebRequest req) {
		MyErrorDetails errorDetails = new MyErrorDetails();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(ex.getMessage());
		errorDetails.setDescription(req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<MyErrorDetails> globalException(RuntimeException ex, WebRequest req) {
		MyErrorDetails errorDetails = new MyErrorDetails();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(ex.getMessage());
		errorDetails.setDescription(req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> globalException(Exception ex, WebRequest req) {
		MyErrorDetails errorDetails = new MyErrorDetails();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(ex.getMessage());
		errorDetails.setDescription(req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
