package com.eircode.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -6502596312985405760L;
	
	 public ValidationException(String message, String errorCode, Throwable cause) {
	        super(message, cause);
	 }
	 
	 public ValidationException(String message) {
	        super(message);
	 }

}
