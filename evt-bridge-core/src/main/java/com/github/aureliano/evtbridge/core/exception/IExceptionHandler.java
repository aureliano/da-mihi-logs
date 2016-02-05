package com.github.aureliano.evtbridge.core.exception;

import com.github.aureliano.evtbridge.core.config.IConfigInput;

public interface IExceptionHandler {

	public abstract void captureException(Runnable runnable, IConfigInput inputConfig, Throwable throwable);
}