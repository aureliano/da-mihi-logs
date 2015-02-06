package com.github.aureliano.defero;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.helper.ConfigHelper;
import com.github.aureliano.defero.reader.DataReaderFactory;
import com.github.aureliano.defero.reader.IDataReader;

public class AppEventsCollector {

	private EventCollectorConfiguration configuration;
	
	public AppEventsCollector() {
		super();
	}
	
	public void execute() {
		ConfigHelper.inputConfigValidation(this.configuration.getInputConfig());
		IDataReader dataReader = DataReaderFactory.createDataReader(
				this.configuration.getInputConfig()).withParser(this.configuration.getParser());
		
		Object data = null;
		while ((data = dataReader.nextData()) != null) {
			System.out.println(data);
		}
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public AppEventsCollector withConfiguration(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
}