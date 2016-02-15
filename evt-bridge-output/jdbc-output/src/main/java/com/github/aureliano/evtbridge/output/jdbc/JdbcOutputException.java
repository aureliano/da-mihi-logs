package com.github.aureliano.evtbridge.output.jdbc;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class JdbcOutputException extends EventBridgeException {

	private static final long serialVersionUID = 7782987682594151029L;

	public JdbcOutputException() {
		super();
	}

	public JdbcOutputException(String message) {
		super(message);
	}

	public JdbcOutputException(Throwable cause) {
		super(cause);
	}

	public JdbcOutputException(Throwable cause, String message) {
		super(cause, message);
	}
}