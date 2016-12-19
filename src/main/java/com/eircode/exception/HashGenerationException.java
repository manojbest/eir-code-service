package com.eircode.exception;

public class HashGenerationException extends Exception {

	private static final long serialVersionUID = -6872596312085405760L;

	public HashGenerationException() {
		super();
	}
	
	public HashGenerationException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public HashGenerationException(final String message) {
		super(message);
	}

	public HashGenerationException(final Throwable throwable) {
		super(throwable);
	}
}

