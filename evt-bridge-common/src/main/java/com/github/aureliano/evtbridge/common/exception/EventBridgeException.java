package com.github.aureliano.evtbridge.common.exception;

public class EventBridgeException extends RuntimeException {

	private static final long serialVersionUID = -3189219196670479175L;

	public EventBridgeException() {
		super();
	}

	public EventBridgeException(String message) {
		super(message);
	}

	public EventBridgeException(Throwable cause) {
		super(cause);
	}

	public EventBridgeException(Throwable cause, String message) {
		super(message, cause);
	}
}