package com.eircode.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -6502596312985405760L;
	
	public ValidationException() {
		super();
	}
	
	public ValidationException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public ValidationException(final String message) {
		super(message);
	}

	public ValidationException(final Throwable throwable) {
		super(throwable);
	}
}
