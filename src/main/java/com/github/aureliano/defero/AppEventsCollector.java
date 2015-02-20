package com.github.aureliano.defero;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
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
	
	public AppEventsCollector() {
		super();
	}
	
	public void execute() {
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		Profiler profiler = new Profiler();
		profiler.start();
		
		this.validation();
		logger.info("Start execution for input type " + this.configuration.getInputConfig().inputType());
				
		long lastLine = this.dataIteration();
		this.printLogToOutput(profiler, lastLine);
	}
	
	private long dataIteration() {
		IDataReader dataReader = DataReaderFactory
			.createDataReader(this.configuration.getInputConfig())
				.withMatcher(this.configuration.getMatcher())
				.withParser(this.configuration.getParser())
				.withFilter(this.configuration.getFilter())
				.withListeners(this.configuration.getDataReadingListeners());
		IDataWriter dataWriter = DataWriterFactory
			.createDataWriter(this.configuration.getOutputConfig())
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
		
		return dataReader.lastLine();
	}
	
	private void validation() {
		ConfigHelper.inputConfigValidation(this.configuration.getInputConfig());
		ConfigHelper.outputConfigValidation(this.configuration.getOutputConfig());
		
		if (this.configuration.getParser() == null) {
			throw new DeferoException("Parser must pe provided.");
		}
	}
	
	private void printLogToOutput(Profiler profiler, long lastLine) {
		Properties properties = Profiler.parse(Profiler.diff(profiler, profiler.stop()));
		properties.put("input.type", this.configuration.getInputConfig().inputType());
		properties.put("input.last.line", String.valueOf(lastLine));
		
		File log = LoggerHelper.saveExecutionLog(properties);
		logger.info("Execution log output saved at " + log.getPath());		
		logger.info("Execution successful!");
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public AppEventsCollector withConfiguration(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
}