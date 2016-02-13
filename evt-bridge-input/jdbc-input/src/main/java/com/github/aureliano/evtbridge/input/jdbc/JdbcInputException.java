package com.github.aureliano.evtbridge.input.jdbc;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class JdbcInputException extends EventBridgeException {

	private static final long serialVersionUID = -180739357075015563L;

	public JdbcInputException() {
		super();
	}

	public JdbcInputException(String message) {
		super(message);
	}

	public JdbcInputException(Throwable cause) {
		super(cause);
	}

	public JdbcInputException(Throwable cause, String message) {
		super(cause, message);
	}
}