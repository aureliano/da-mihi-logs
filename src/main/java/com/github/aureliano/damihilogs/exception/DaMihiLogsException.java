package com.github.aureliano.damihilogs.exception;

public class DaMihiLogsException extends RuntimeException {

	private static final long serialVersionUID = -3189219196670479175L;

	public DaMihiLogsException() {
		super();
	}

	public DaMihiLogsException(String message) {
		super(message);
	}

	public DaMihiLogsException(Throwable cause) {
		super(cause);
	}

	public DaMihiLogsException(Throwable cause, String message) {
		super(message, cause);
	}
}