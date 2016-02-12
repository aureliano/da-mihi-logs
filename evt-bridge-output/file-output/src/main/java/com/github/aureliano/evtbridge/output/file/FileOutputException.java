package com.github.aureliano.evtbridge.output.file;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class FileOutputException extends EventBridgeException {

	private static final long serialVersionUID = 6038438694310336494L;

	public FileOutputException() {
		super();
	}

	public FileOutputException(String message) {
		super(message);
	}

	public FileOutputException(Throwable cause) {
		super(cause);
	}

	public FileOutputException(Throwable cause, String message) {
		super(cause, message);
	}
}