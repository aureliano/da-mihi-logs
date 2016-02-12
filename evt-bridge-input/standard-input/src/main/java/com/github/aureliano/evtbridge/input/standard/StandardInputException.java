package com.github.aureliano.evtbridge.input.standard;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class StandardInputException extends EventBridgeException {

	private static final long serialVersionUID = 1052013751420263162L;

	public StandardInputException() {
		super();
	}

	public StandardInputException(String message) {
		super(message);
	}

	public StandardInputException(Throwable cause) {
		super(cause);
	}

	public StandardInputException(Throwable cause, String message) {
		super(cause, message);
	}
}