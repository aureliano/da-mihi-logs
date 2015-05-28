package com.github.aureliano.damihilogs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import com.github.aureliano.damihilogs.reader.AbstractDataReader;

public class CustomDataReader extends AbstractDataReader {

	private Integer newValue;
	
	public CustomDataReader() {
		super();
	}

	@Override
	public String nextData() {
		String line = this.readNextLine();
		if (line == null) {
			super.markedToStop = true;
			return null;
		}
		
		String data = null;		
		super.executeBeforeReadingMethodListeners();
		
		data = super.prepareLogEvent(line);
		super.executeAfterReadingMethodListeners(data);
		
		return "{\"property\":" + data + "}";
	}

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = super.inputConfiguration.getConfigurationId();
		
		map.put("input.config." + id + ".my.new.property", this.newValue);
		
		return map;
	}

	@Override
	public void endResources() {
		// Finalize any resource you have open.
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		CustomInputConfig configuration = (CustomInputConfig) super.inputConfiguration;
		String key = "input.config." + configuration.getConfigurationId() + ".my.new.property";
		
		String value = properties.getProperty(key);
		if (value != null) {
			if ((configuration.isUseLastExecutionRecords()) &&
					(configuration.getMyNewProperty() == null)) {
				configuration.withMyNewProperty(Integer.parseInt(value));
			}
		}
	}

	@Override
	protected String readNextLine() {
		if (this.newValue != null) {
			return null;
		}
		
		CustomInputConfig configuration = (CustomInputConfig) super.inputConfiguration;
		Integer old = configuration.getMyNewProperty();
		
		if (old != null) {
			System.out.println(" >>> Last execution my new property was equal to " + old);
		}
		
		this.newValue = new Random(System.currentTimeMillis()).nextInt(9);
		
		return this.newValue.toString();
	}
}