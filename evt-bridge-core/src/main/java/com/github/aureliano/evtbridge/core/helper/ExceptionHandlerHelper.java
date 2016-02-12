package com.github.aureliano.evtbridge.core.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.core.command.DataIterationCommand;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;

public final class ExceptionHandlerHelper {

	private static final Logger logger = Logger.getLogger(ExceptionHandlerHelper.class);
	
	private ExceptionHandlerHelper() {
		super();
	}
	
	public static void executeHandlingException(DataIterationCommand command) {
		try {
			command.execute();
		} catch (Exception ex) {
			handleException(command, ex);
			ExceptionHandlerHelper.configureCommandLogExecutionForException(command, ex);
		}
	}
	
	public static void configureCommandLogExecutionForException(DataIterationCommand command, Throwable exception) {
		IConfigInput inputConfig = (IConfigInput) command.getDataReader().getConfiguration();
		Map<String, Object> logExecution = command.getLogExecution();
		
		if (logExecution == null) {
			logExecution = new HashMap<String, Object>();
			command.setLogExecution(logExecution);
		}
		
		String prefix = "input.config." + inputConfig.getConfigurationId() + ".";
		logExecution.put(prefix + "exception", exception.getMessage());
		logExecution.put(prefix + "stackTrace", Arrays.asList(exception.getStackTrace()));
	}
	
	private static void handleException(DataIterationCommand command, Exception ex) {
		IConfigInput inputConfig = (IConfigInput) command.getDataReader().getConfiguration();
		List<IExceptionHandler> handlers = inputConfig.getExceptionHandlers();
		
		Thread thread = Thread.currentThread();
		
		for (IExceptionHandler handler : handlers) {
			handler.captureException(thread, inputConfig, ex);
		}
		
		if (handlers.isEmpty()) {
			logger.error(ex.getMessage(), ex);
		}
	}
}