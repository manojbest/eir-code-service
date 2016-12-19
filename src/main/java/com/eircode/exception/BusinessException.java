package com.eircode.exception;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = -3132596316085405760L;

	public BusinessException() {
		super();
	}
	
	public BusinessException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final Throwable throwable) {
		super(throwable);
	}

}
