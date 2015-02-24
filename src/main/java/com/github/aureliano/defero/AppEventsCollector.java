package com.github.aureliano.defero;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.StandardInputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.helper.ConfigHelper;
import com.github.aureliano.defero.helper.LoggerHelper;
import com.github.aureliano.defero.profile.Profiler;
import com.github.aureliano.defero.reader.DataReaderFactory;
import com.github.aureliano.defero.reader.IDataReader;
import com.github.aureliano.defero.writer.DataWriterFactory;
import com.github.aureliano.defero.writer.IDataWriter;

public class AppEventsCollector {

	private static final Logger logger = Logger.getLogger(AppEventsCollector.class.getName());
	
	private EventCollectorConfiguration configuration;
	private boolean persistExecutionLog;
	
	public AppEventsCollector() {
		this.persistExecutionLog = true;
	}
	
	public void execute() {
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		Profiler profiler = new Profiler();
		profiler.start();
		
		this.prepareExecution();
		List<Map<String, Object>> executionLogs = this.executeCollectors();
		
		if (this.persistExecutionLog) {
			this.printLogToOutput(profiler, executionLogs);
		}
	}
	
	private List<Map<String, Object>> executeCollectors() {
		if (this.configuration.getParser() == null) {
			throw new DeferoException("Parser must pe provided.");
		}
		
		List<Map<String, Object>> executionLogs = new ArrayList<Map<String,Object>>();
		
		for (short i = 0; i < this.configuration.getInputConfigs().size(); i++) {
			IConfigInput inputConfig = this.configuration.getInputConfigs().get(i);
			ConfigHelper.inputConfigValidation(inputConfig);
			
			if (inputConfig.getConfigurationId() == null) {
				inputConfig.withConfigurationId("configuration_id_" + (i + 1));
			}
			
			logger.info("Start execution for input " + inputConfig.getConfigurationId());
			
			for (IConfigOutput outputConfig : this.configuration.getOutputConfigs()) {
				ConfigHelper.outputConfigValidation(outputConfig);
				executionLogs.add(this.dataIteration(inputConfig, outputConfig));
			}
			
			System.out.println();
		}
		
		return executionLogs;
	}
	
	private Map<String, Object> dataIteration(IConfigInput inputConfig, IConfigOutput outputConfig) {
		IDataReader dataReader = DataReaderFactory
			.createDataReader(inputConfig)
				.withMatcher(this.configuration.getMatcher())
				.withParser(this.configuration.getParser())
				.withFilter(this.configuration.getFilter())
				.withListeners(this.configuration.getDataReadingListeners());
		IDataWriter dataWriter = DataWriterFactory
			.createDataWriter(outputConfig)
				.withOutputFormatter(this.configuration.getOutputFormatter())
				.withListeners(this.configuration.getDataWritingListeners());
		
		while (dataReader.keepReading()) {
			Object data = dataReader.nextData();
			if (data != null) {
				dataWriter.write(data);
			}
		}
		
		dataReader.endResources();
		dataWriter.endResources();
		
		return dataReader.executionLog();
	}
	
	private void printLogToOutput(Profiler profiler, List<Map<String, Object>> executionLogs) {
		Properties properties = Profiler.parse(Profiler.diff(profiler, profiler.stop()));
		
		for (Map<String, Object> executionLog : executionLogs) {
			for (String key : executionLog.keySet()) {
				properties.put(key, String.valueOf(executionLog.get(key)));
			}
		}
		
		File log = LoggerHelper.saveExecutionLog(properties, true);
		logger.info("Execution log output saved at " + log.getPath());		
		logger.info("Execution successful!");
	}
	
	private void prepareExecution() {
		if (this.configuration.getInputConfigs().isEmpty()) {
			this.configuration.addInputConfig(new StandardInputConfig());
		}
		
		if (this.configuration.getOutputConfigs().isEmpty()) {
			this.configuration.addOutputConfig(new StandardOutputConfig());
		}
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public AppEventsCollector withConfiguration(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
	
	public boolean isPersistExecutionLog() {
		return persistExecutionLog;
	}
	
	public AppEventsCollector withPersistExecutionLog(boolean persistExecutionLog) {
		this.persistExecutionLog = persistExecutionLog;
		return this;
	}
}