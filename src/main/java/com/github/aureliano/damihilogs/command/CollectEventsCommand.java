package com.github.aureliano.damihilogs.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.exception.ThreadExceptionHandler;
import com.github.aureliano.damihilogs.executor.reader.IDataReader;
import com.github.aureliano.damihilogs.executor.writer.IDataWriter;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.helper.ExceptionHandlerHelper;
import com.github.aureliano.damihilogs.helper.LoggerHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.matcher.SingleLineMatcher;
import com.github.aureliano.damihilogs.parser.PlainTextParser;
import com.github.aureliano.damihilogs.reg.ApiServiceRegistrator;

public class CollectEventsCommand {

	private EventCollectorConfiguration configuration;
	private List<Map<String, Object>> logExecutions;
	
	private static final Logger logger = Logger.getLogger(CollectEventsCommand.class);
	
	public CollectEventsCommand(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		this.logExecutions = new ArrayList<Map<String,Object>>();
	}
	
	public void execute() {
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		this.prepareExecution();
		this.executeCollectors();
	}
	
	private void prepareExecution() {
		if (this.configuration.getInputConfigs().isEmpty()) {
			this.configuration.addInputConfig(new StandardInputConfig());
		}
		
		if (this.configuration.getOutputConfigs().isEmpty()) {
			this.configuration.addOutputConfig(new StandardOutputConfig());
		}
	}
	
	private void executeCollectors() {		
		for (IConfigOutput outputConfig : this.configuration.getOutputConfigs()) {
			ConfigHelper.outputConfigValidation(outputConfig);
		}
		
		List<DataIterationCommand> commands = new ArrayList<DataIterationCommand>();
		
		for (short i = 0; i < this.configuration.getInputConfigs().size(); i++) {
			IConfigInput inputConfig = this.configuration.getInputConfigs().get(i);
			if (StringHelper.isEmpty(inputConfig.getConfigurationId())) {
				inputConfig.withConfigurationId(ConfigHelper.newUniqueConfigurationName());
			}
			
			ConfigHelper.inputConfigValidation(inputConfig);
			ConfigHelper.copyMetadata(this.configuration, inputConfig);
			
			logger.info("Start execution for input " + inputConfig.getConfigurationId());
			commands.add(new DataIterationCommand(
				this.createDataReader(inputConfig), this.createDataWriters(), inputConfig.getConfigurationId()));
		}
		
		if (this.configuration.isMultiThreadingEnabled()) {
			if (commands.size() > 1) {
				this.parallelExecution(commands);
				this.copyLogExecutions(commands);
				return;
			}
			
			logger.warn("Despite a multi-threading was requested it'll be ignored because there is only one input. Executing serially.");
		}
		
		this.serialExecution(commands);
		this.copyLogExecutions(commands);
	}
	
	private void copyLogExecutions(List<DataIterationCommand> commands) {
		for (DataIterationCommand command : commands) {
			Map<String, Object> logExecution = command.getLogExecution();
			this.addLogExecution(logExecution);
		}
	}
	
	private void serialExecution(List<DataIterationCommand> commands) {
		for (DataIterationCommand command : commands) {
			Thread.currentThread().setName(String.format("Thread-%s-%s", this.configuration.getCollectorId(), command.getId()));
			ExceptionHandlerHelper.executeHandlingException(command);
		}
	}
	
	private void parallelExecution(List<DataIterationCommand> commands) {
		List<Thread> threads = new ArrayList<Thread>();
		
		for (DataIterationCommand command : commands) {
			Thread t = this.createThread(command);
			
			t.start();
			t.setUncaughtExceptionHandler(new ThreadExceptionHandler(command));
			
			threads.add(t);
		}
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				throw new DaMihiLogsException(ex);
			}
		}
	}
	
	private Thread createThread(DataIterationCommand command) {
		Thread t = null;
		if (!StringHelper.isEmpty(command.getId())) {
			t = new Thread(command, String.format("Thread-%s-%s", this.configuration.getCollectorId(), command.getId()));
		} else {
			throw new DaMihiLogsException("Executor with no id.");
		}
		
		return t;
	}
	
	private IDataReader createDataReader(IConfigInput inputConfig) {
		IDataReader dataReader = (IDataReader) ApiServiceRegistrator.instance().createExecutor(inputConfig);
		
		if (inputConfig.getMatcher() == null) {
			inputConfig.withMatcher(new SingleLineMatcher());
		}
		
		if (inputConfig.isUseLastExecutionRecords()) {
			Properties properties = LoggerHelper.getLastExecutionLog(this.configuration.getCollectorId(), true);
			if (properties != null) {
				dataReader.loadLastExecutionLog(properties);
			}
		}
		return dataReader;
	}
	
	private List<IDataWriter> createDataWriters() {
		List<IDataWriter> dataWriters = new ArrayList<IDataWriter>();
		
		for (IConfigOutput config : this.configuration.getOutputConfigs()) {
			IConfigOutput outputConfig = (IConfigOutput) config.clone();
			
			if (outputConfig.getParser() == null) {
				outputConfig.withParser(new PlainTextParser());
			}
			
			if (outputConfig.getFilter() == null) {
				outputConfig.withFilter(new DefaultEmptyFilter());
			}
			
			IDataWriter dataWriter = (IDataWriter) ApiServiceRegistrator.instance().createExecutor(outputConfig);
			dataWriters.add(dataWriter);
		}
		
		return dataWriters;
	}
	
	public final synchronized void addLogExecution(Map<String, Object> logExecution) {
		this.logExecutions.add(logExecution);
	}
	
	public List<Map<String, Object>> getLogExecutions() {
		return this.logExecutions;
	}
}