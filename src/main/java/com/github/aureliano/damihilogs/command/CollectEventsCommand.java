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
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.helper.LoggerHelper;
import com.github.aureliano.damihilogs.parser.PlainTextParser;
import com.github.aureliano.damihilogs.reader.DataReaderFactory;
import com.github.aureliano.damihilogs.reader.IDataReader;
import com.github.aureliano.damihilogs.writer.DataWriterFactory;
import com.github.aureliano.damihilogs.writer.IDataWriter;

public class CollectEventsCommand {

	private EventCollectorConfiguration configuration;
	private static List<Map<String, Object>> logExecutions;
	private String collectorId;
	
	private static final Logger logger = Logger.getLogger(CollectEventsCommand.class);
	
	public CollectEventsCommand(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		CollectEventsCommand.logExecutions = new ArrayList<Map<String,Object>>();
	}
	
	public void execute(String collectorId) {
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		this.collectorId = collectorId;
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
			ConfigHelper.inputConfigValidation(inputConfig);
			ConfigHelper.copyMetadata(this.configuration, inputConfig);
			
			if (inputConfig.getConfigurationId() == null) {
				inputConfig.withConfigurationId("configuration_id_" + (i + 1));
			}
			
			logger.info("Start execution for input " + inputConfig.getConfigurationId());
			commands.add(new DataIterationCommand(
				this.createDataReader(inputConfig), this.createDataWriters(), inputConfig.getConfigurationId()));
		}
		
		if (this.configuration.isMultiThreadingEnabled()) {
			if (commands.size() > 1) {
				this.parallelExecution(commands);
				return;
			}
			
			logger.warn("Despite a multi-threading was requested it'll be ignored because there is only one input. Executing serially.");
		}
		
		this.serialExecution(commands);
	}
	
	private void serialExecution(List<DataIterationCommand> commands) {
		for (DataIterationCommand command : commands) {
			command.execute();
		}
	}
	
	private void parallelExecution(List<DataIterationCommand> commands) {
		List<Thread> threads = new ArrayList<Thread>();
		
		for (DataIterationCommand command : commands) {
			Thread t = this.createThread(command);
			
			t.start();
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
		if (command.getId() != null && !command.getId().equals("")) {
			t = new Thread(command, "Thread-" + command.getId());
		} else {
			t = new Thread(command);
		}
		
		return t;
	}
	
	private IDataReader createDataReader(IConfigInput inputConfig) {
		IDataReader dataReader = DataReaderFactory
			.createDataReader(inputConfig)
				.withMatcher(this.configuration.getMatcher())
				.withListeners(this.configuration.getDataReadingListeners());
		
		if (inputConfig.isUseLastExecutionRecords()) {
			Properties properties = LoggerHelper.getLastExecutionLog(collectorId);
			if (properties != null) {
				dataReader.loadLastExecutionLog(LoggerHelper.getLastExecutionLog(collectorId));
				CollectEventsCommand.addLogExecution(dataReader.getReadingProperties());
			}
		}
		return dataReader;
	}
	
	private List<IDataWriter> createDataWriters() {
		List<IDataWriter> dataWriters = new ArrayList<IDataWriter>();
		
		for (IConfigOutput outputConfig : this.configuration.getOutputConfigs()) {
			if (outputConfig.getParser() == null) {
				outputConfig.withParser(new PlainTextParser());
			}
			
			if (outputConfig.getFilter() == null) {
				outputConfig.withFilter(new DefaultEmptyFilter());
			}
			
			ConfigHelper.copyMetadata(this.configuration, outputConfig);
			
			IDataWriter dataWriter = DataWriterFactory
					.createDataWriter(outputConfig)
						.withOutputFormatter(this.configuration.getOutputFormatter())
						.withListeners(this.configuration.getDataWritingListeners());
			dataWriters.add(dataWriter);
		}
		
		return dataWriters;
	}
	
	public static final synchronized void addLogExecution(Map<String, Object> logExecution) {
		CollectEventsCommand.logExecutions.add(logExecution);
	}
	
	public List<Map<String, Object>> getLogExecutions() {
		return CollectEventsCommand.logExecutions;
	}
}