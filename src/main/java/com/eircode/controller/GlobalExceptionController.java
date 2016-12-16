package com.eircode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import com.eircode.exception.ValidationException;

@ControllerAdvice
@RestController
public class GlobalExceptionController {
	
	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Exception> handleException(final ValidationException ex) {
		logger.error("> handleException");
		logger.error("- Exception: ", ex);
		logger.error("< handleException");
		return new ResponseEntity<Exception>(ex, HttpStatus.NOT_FOUND);
	}
 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Exception> handleException(final Exception ex) {
		logger.error("> handleException");
		logger.error("- Exception: ", ex);
		logger.error("< handleException");
		return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
