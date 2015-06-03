package com.github.aureliano.damihilogs.exception;

import com.github.aureliano.damihilogs.config.input.IConfigInput;

public interface IExceptionHandler {

	public abstract void captureException(Runnable runnable, IConfigInput inputConfig, Throwable throwable);
}