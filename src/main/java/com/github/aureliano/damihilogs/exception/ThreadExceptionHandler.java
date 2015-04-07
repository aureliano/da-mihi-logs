package com.github.aureliano.damihilogs.exception;

import java.util.List;

import com.github.aureliano.damihilogs.command.DataIterationCommand;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.helper.ExceptionHandlerHelper;

public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

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
		
		ExceptionHandlerHelper.configureCommandLogExecutionForException(this.command, ex);
	}
}