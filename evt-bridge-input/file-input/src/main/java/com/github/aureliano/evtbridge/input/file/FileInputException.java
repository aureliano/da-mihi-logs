package com.github.aureliano.evtbridge.input.file;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class FileInputException extends EventBridgeException {

	private static final long serialVersionUID = -5207803546478400857L;

	public FileInputException() {
		super();
	}

	public FileInputException(String message) {
		super(message);
	}

	public FileInputException(Throwable cause) {
		super(cause);
	}

	public FileInputException(Throwable cause, String message) {
		super(cause, message);
	}
}