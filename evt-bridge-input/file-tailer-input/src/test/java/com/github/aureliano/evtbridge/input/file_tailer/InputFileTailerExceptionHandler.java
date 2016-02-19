package com.github.aureliano.evtbridge.input.file_tailer;

import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;

public class InputFileTailerExceptionHandler implements IExceptionHandler {

	@Override
	public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
}