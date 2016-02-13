package com.github.aureliano.evtbridge.input.url;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class UrlInputException extends EventBridgeException {

	private static final long serialVersionUID = -5445301051511512334L;

	public UrlInputException() {
		super();
	}

	public UrlInputException(String message) {
		super(message);
	}

	public UrlInputException(Throwable cause) {
		super(cause);
	}

	public UrlInputException(Throwable cause, String message) {
		super(cause, message);
	}
}