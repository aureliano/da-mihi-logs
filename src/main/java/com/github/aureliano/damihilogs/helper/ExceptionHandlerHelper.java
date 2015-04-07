package com.github.aureliano.damihilogs.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.command.DataIterationCommand;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public final class ExceptionHandlerHelper {

	private ExceptionHandlerHelper() {
		super();
	}
	
	public static void executeHandlingException(DataIterationCommand command) {
		IConfigInput inputConfig = command.getDataReader().getInputConfiguration();
		List<IExceptionHandler> handlers = inputConfig.getExceptionHandlers();
		
		Thread thread = Thread.currentThread();
		
		for (IExceptionHandler handler : handlers) {
			handler.captureException(thread, inputConfig);
		}
		
		try {
			command.execute();
		} catch (Exception ex) {
			ExceptionHandlerHelper.configureCommandLogExecutionForException(command, ex);
		}
	}
	
	public static void configureCommandLogExecutionForException(DataIterationCommand command, Throwable exception) {
		IConfigInput inputConfig = command.getDataReader().getInputConfiguration();
		Map<String, Object> logExecution = command.getLogExecution();
		
		if (logExecution == null) {
			logExecution = new HashMap<String, Object>();
			command.setLogExecution(logExecution);
		}
		
		String prefix = "input.config." + inputConfig.getConfigurationId() + ".";
		logExecution.put(prefix + "exception", exception.getMessage());
		logExecution.put(prefix + "stackTrace", Arrays.asList(exception.getStackTrace()));
	}
}