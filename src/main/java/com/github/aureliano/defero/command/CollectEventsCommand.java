package com.github.aureliano.defero.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.StandardInputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.helper.ConfigHelper;
import com.github.aureliano.defero.reader.DataReaderFactory;
import com.github.aureliano.defero.reader.IDataReader;
import com.github.aureliano.defero.writer.DataWriterFactory;
import com.github.aureliano.defero.writer.IDataWriter;

public class CollectEventsCommand {

	private EventCollectorConfiguration configuration;
	private static List<Map<String, Object>> logExecutions;
	
	private static final Logger logger = Logger.getLogger(CollectEventsCommand.class.getName());
	
	public CollectEventsCommand(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		CollectEventsCommand.logExecutions = new ArrayList<Map<String,Object>>();
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
		if (this.configuration.getParser() == null) {
			throw new DeferoException("Parser must pe provided.");
		}
		
		for (IConfigOutput outputConfig : this.configuration.getOutputConfigs()) {
			ConfigHelper.outputConfigValidation(outputConfig);
		}
		
		List<DataIterationCommand> commands = new ArrayList<DataIterationCommand>();
		
		for (short i = 0; i < this.configuration.getInputConfigs().size(); i++) {
			IConfigInput inputConfig = this.configuration.getInputConfigs().get(i);
			ConfigHelper.inputConfigValidation(inputConfig);
			
			if (inputConfig.getConfigurationId() == null) {
				inputConfig.withConfigurationId("configuration_id_" + (i + 1));
			}
			
			logger.info("Start execution for input " + inputConfig.getConfigurationId());
			commands.add(new DataIterationCommand(this.createDataReader(inputConfig), this.createDataWriters()));
		}
		
		if (this.configuration.isMultiThreadingEnabled()) {
			if (commands.size() > 1) {
				this.parallelExecution(commands);
				return;
			}
			
			logger.warning("Despite a multi-threading was requested it'll be ignored because there is only one input. Executing serially.");
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
			Thread t = new Thread(command);
			t.start();
		
			threads.add(t);
		}
		
		boolean anyThreadAlive = true;
		while (anyThreadAlive) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				throw new DeferoException(ex);
			}
			
			boolean alive = false;
			for (Thread thread : threads) {
				if (thread.isAlive()) {
					alive = true;
					break;
				}
			}
			
			anyThreadAlive = alive;
		}
	}
	
	private IDataReader createDataReader(IConfigInput inputConfig) {
		return DataReaderFactory
			.createDataReader(inputConfig)
				.withMatcher(this.configuration.getMatcher())
				.withParser(this.configuration.getParser())
				.withFilter(this.configuration.getFilter())
				.withListeners(this.configuration.getDataReadingListeners());
	}
	
	private List<IDataWriter> createDataWriters() {
		List<IDataWriter> dataWriters = new ArrayList<IDataWriter>();
		
		for (IConfigOutput outputConfig : this.configuration.getOutputConfigs()) {
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