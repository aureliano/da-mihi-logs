package com.github.aureliano.evtbridge.converter.exception;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ConfigurationConverterException extends EventBridgeException {

	private static final long serialVersionUID = 8819899772894365399L;

	public ConfigurationConverterException() {
		super();
	}

	public ConfigurationConverterException(String message) {
		super(message);
	}

	public ConfigurationConverterException(Throwable cause) {
		super(cause);
	}

	public ConfigurationConverterException(Throwable cause, String message) {
		super(cause, message);
	}
}