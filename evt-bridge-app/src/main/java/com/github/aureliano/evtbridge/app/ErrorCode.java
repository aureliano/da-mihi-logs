package com.github.aureliano.evtbridge.app;

public enum ErrorCode {

	COMMAND_NOT_FOUND(100),
	SCHEMA_PARAM_ERROR(200),
	RUN_CONFIGURATION_FILE_NOT_PROVIDED(300),
	RUN_FILE_DOES_NOT_EXIST(301),
	RUN_FILE_IS_DIRECTORY(302),
	RUN_FILE_IS_NOT_REGULAR_FILE(303),
	RUN_INVALID_FILE_TYPE(304);
	
	private int code;
	
	private ErrorCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}