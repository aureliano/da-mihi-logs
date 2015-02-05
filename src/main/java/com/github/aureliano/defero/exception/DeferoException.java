package com.github.aureliano.defero.exception;

public class DeferoException extends RuntimeException {

	private static final long serialVersionUID = -3189219196670479175L;

	public DeferoException() {
		super();
	}

	public DeferoException(String message) {
		super(message);
	}

	public DeferoException(Throwable cause) {
		super(cause);
	}

	public DeferoException(Throwable cause, String message) {
		super(message, cause);
	}
}