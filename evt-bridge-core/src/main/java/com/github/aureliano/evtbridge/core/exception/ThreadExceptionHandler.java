package com.github.aureliano.evtbridge.core.exception;

import java.util.List;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.core.command.DataIterationCommand;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.helper.ExceptionHandlerHelper;

public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

	private static final Logger logger = Logger.getLogger(ThreadExceptionHandler.class);
	
	private DataIterationCommand command;
	
	public ThreadExceptionHandler(DataIterationCommand command) {
		this.command = command;
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		IConfigInput inputConfig = (IConfigInput) this.command.getDataReader().getConfiguration();
		List<IExceptionHandler> handlers = inputConfig.getExceptionHandlers();
		
		for (IExceptionHandler handler : handlers) {
			handler.captureException(thread, inputConfig, ex);
		}

		if (handlers.isEmpty()) {
			logger.error(ex.getMessage(), ex);
		}
		ExceptionHandlerHelper.configureCommandLogExecutionForException(this.command, ex);
	}
}