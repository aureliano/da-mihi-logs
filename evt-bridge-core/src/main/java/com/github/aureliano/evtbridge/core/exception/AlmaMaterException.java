package com.github.aureliano.evtbridge.core.exception;

public class AlmaMaterException extends RuntimeException {

	private static final long serialVersionUID = -3189219196670479175L;

	public AlmaMaterException() {
		super();
	}

	public AlmaMaterException(String message) {
		super(message);
	}

	public AlmaMaterException(Throwable cause) {
		super(cause);
	}

	public AlmaMaterException(Throwable cause, String message) {
		super(message, cause);
	}
}