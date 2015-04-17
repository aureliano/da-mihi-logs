package com.github.aureliano.damihilogs.exception;

import java.util.List;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.command.DataIterationCommand;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.helper.ExceptionHandlerHelper;

public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

	private static final Logger logger = Logger.getLogger(ThreadExceptionHandler.class);
	
	private DataIterationCommand command;
	
	public ThreadExceptionHandler(DataIterationCommand command) {
		this.command = command;
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		IConfigInput inputConfig = this.command.getDataReader().getInputConfiguration();
		List<IExceptionHandler> handlers = inputConfig.getExceptionHandlers();
		
		for (IExceptionHandler handler : handlers) {
			handler.captureException(thread, inputConfig);
		}

		logger.error(ex.getMessage(), ex);
		ExceptionHandlerHelper.configureCommandLogExecutionForException(this.command, ex);
	}
}