package com.github.aureliano.damihilogs.report.model;

public class ExceptionModel {

	private String seed;
	private String message;
	private String stackTrace;
	
	public ExceptionModel() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public String getSeed() {
		return seed;
	}

	public ExceptionModel withSeed(String seed) {
		this.seed = seed;
		return this;
	}

	public ExceptionModel withMessage(String message) {
		this.message = message;
		return this;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public ExceptionModel withStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
		return this;
	}
}