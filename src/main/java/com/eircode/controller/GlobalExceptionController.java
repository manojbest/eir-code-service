package com.eircode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import com.eircode.exception.BusinessException;
import com.eircode.exception.ValidationException;

@ControllerAdvice
@RestController
public class GlobalExceptionController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Exception> handleException(final ValidationException ex) {
		logger.error("> handleException");
		logger.error("- Exception: ", ex);
		logger.error("< handleException");
		return new ResponseEntity<Exception>(ex, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Exception> handleException(final BusinessException ex) {
		logger.error("> handleException");
		logger.error("- Exception: ", ex);
		logger.error("< handleException");
		return new ResponseEntity<Exception>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
	}
 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Exception> handleException(final Exception ex) {
		logger.error("> handleException");
		logger.error("- Exception: ", ex);
		logger.error("< handleException");
		return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
