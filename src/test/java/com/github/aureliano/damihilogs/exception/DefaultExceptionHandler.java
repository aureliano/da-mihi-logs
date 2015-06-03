package com.github.aureliano.damihilogs.exception;

import com.github.aureliano.damihilogs.config.input.IConfigInput;

public class DefaultExceptionHandler implements IExceptionHandler {

	@Override
	public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
}