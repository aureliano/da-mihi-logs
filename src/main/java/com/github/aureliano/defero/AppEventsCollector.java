package com.github.aureliano.defero;

import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.helper.ConfigHelper;
import com.github.aureliano.defero.profile.Profiler;
import com.github.aureliano.defero.reader.DataReaderFactory;
import com.github.aureliano.defero.reader.IDataReader;

public class AppEventsCollector {

	private static final Logger logger = Logger.getLogger(AppEventsCollector.class.getName());
	
	private EventCollectorConfiguration configuration;
	
	public AppEventsCollector() {
		super();
	}
	
	public void execute() {
		Profiler profiler = new Profiler();
		profiler.start();
		
		ConfigHelper.inputConfigValidation(this.configuration.getInputConfig());
		IDataReader dataReader = DataReaderFactory.createDataReader(
				this.configuration.getInputConfig()).withParser(this.configuration.getParser());
		
		Object data = null;
		while ((data = dataReader.nextData()) != null) {
			System.out.println(data);
		}
		
		Properties properties = Profiler.parse(Profiler.diff(profiler, profiler.stop()));
		
		logger.info("Time elapsed: " + properties.getProperty("profile.time.elapsed"));
		logger.info("Available processors: " + properties.getProperty("profile.processor.available"));
		logger.info("Free memory: " + properties.getProperty("profile.memory.free"));
		logger.info("Max memory: " + properties.getProperty("profile.memory.max"));
		logger.info("Total memory: " + properties.getProperty("profile.memomry.total"));
		
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