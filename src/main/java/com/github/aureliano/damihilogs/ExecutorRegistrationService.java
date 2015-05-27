package com.github.aureliano.damihilogs;

import java.util.HashMap;
import java.util.Map;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.reader.ExternalCommandDataReader;
import com.github.aureliano.damihilogs.reader.FileDataReader;
import com.github.aureliano.damihilogs.reader.FileTailerDataReader;
import com.github.aureliano.damihilogs.reader.StandardDataReader;
import com.github.aureliano.damihilogs.reader.UrlDataReader;
import com.github.aureliano.damihilogs.writer.ElasticSearchDataWriter;
import com.github.aureliano.damihilogs.writer.FileDataWriter;
import com.github.aureliano.damihilogs.writer.StandardDataWriter;

public final class ExecutorRegistrationService {

	private static ExecutorRegistrationService instance;
	
	private Map<Class<?>, Class<?>> executors;
	
	private ExecutorRegistrationService() {
		this.executors = this.loadDefaultExecutors();
	}
	
	public void addExecutor(Class<? extends IConfiguration> configuration, Class<? extends IExecutor> executor) {
		if (configuration == null) {
			throw new DaMihiLogsException("Configuration must be provided.");
		} else if (executor == null) {
			throw new DaMihiLogsException("Executor must be provided.");
		}
		
		this.executors.put(configuration, executor);
	}
	
	public IExecutor createExecutor(IConfiguration configuration) {
		Class<?> executorClass = this.executors.get(configuration.getClass());
		if (executorClass == null) {
			throw new DaMihiLogsException("There isn't an executor (reader/writer) registered to " + configuration.getClass().getName());
		}
		
		try {
			IExecutor executor = (IExecutor) executorClass.newInstance();
			return executor.withConfiguration(configuration);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public static ExecutorRegistrationService instance() {
		if (instance == null) {
			instance = new ExecutorRegistrationService();
		}
		
		return instance;
	}
	
	private Map<Class<?>, Class<?>> loadDefaultExecutors() {
		Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
		
		map.put(FileInputConfig.class, FileDataReader.class);
		map.put(FileTailerInputConfig.class, FileTailerDataReader.class);
		map.put(StandardInputConfig.class, StandardDataReader.class);
		map.put(UrlInputConfig.class, UrlDataReader.class);
		map.put(ExternalCommandInput.class, ExternalCommandDataReader.class);
		map.put(StandardOutputConfig.class, StandardDataWriter.class);
		map.put(FileOutputConfig.class, FileDataWriter.class);
		map.put(ElasticSearchOutputConfig.class, ElasticSearchDataWriter.class);
		
		return map;
	}
}