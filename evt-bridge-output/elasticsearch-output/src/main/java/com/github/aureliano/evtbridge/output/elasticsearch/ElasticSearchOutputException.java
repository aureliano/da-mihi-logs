package com.github.aureliano.evtbridge.output.elasticsearch;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ElasticSearchOutputException extends EventBridgeException {

	private static final long serialVersionUID = -2436896075727465741L;

	public ElasticSearchOutputException() {
		super();
	}

	public ElasticSearchOutputException(String message) {
		super(message);
	}

	public ElasticSearchOutputException(Throwable cause) {
		super(cause);
	}

	public ElasticSearchOutputException(Throwable cause, String message) {
		super(cause, message);
	}
}