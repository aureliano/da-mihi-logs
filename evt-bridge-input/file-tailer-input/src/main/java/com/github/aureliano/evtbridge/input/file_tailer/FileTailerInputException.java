package com.github.aureliano.evtbridge.input.file_tailer;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class FileTailerInputException extends EventBridgeException {

	private static final long serialVersionUID = 9116855260652112740L;

	public FileTailerInputException() {
		super();
	}

	public FileTailerInputException(String message) {
		super(message);
	}

	public FileTailerInputException(Throwable cause) {
		super(cause);
	}

	public FileTailerInputException(Throwable cause, String message) {
		super(cause, message);
	}
}