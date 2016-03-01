package com.github.aureliano.evtbridge.app;

public enum ErrorCode {

	COMMAND_NOT_FOUND(100),
	SCHEMA_PARAM_ERROR(200);
	
	private int code;
	
	private ErrorCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}