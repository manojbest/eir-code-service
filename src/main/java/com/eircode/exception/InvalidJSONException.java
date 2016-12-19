package com.eircode.exception;

public class InvalidJSONException extends Exception {

	private static final long serialVersionUID = -7742596312985405760L;

	public InvalidJSONException() {
		super();
	}
	
	public InvalidJSONException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public InvalidJSONException(final String message) {
		super(message);
	}

	public InvalidJSONException(final Throwable throwable) {
		super(throwable);
	}
	
}
