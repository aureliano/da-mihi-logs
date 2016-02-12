package com.github.aureliano.evtbridge.input.external_command;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ExternalCommandException extends EventBridgeException {

	private static final long serialVersionUID = -4448242518583571954L;

	public ExternalCommandException() {
		super();
	}

	public ExternalCommandException(String message) {
		super(message);
	}

	public ExternalCommandException(Throwable cause) {
		super(cause);
	}

	public ExternalCommandException(Throwable cause, String message) {
		super(cause, message);
	}
}