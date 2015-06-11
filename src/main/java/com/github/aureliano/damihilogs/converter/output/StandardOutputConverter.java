package com.github.aureliano.damihilogs.converter.output;

import java.util.Map;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;

public class StandardOutputConverter extends AbstractOutputConverter<StandardOutputConfig> {

	public StandardOutputConverter() {
		super();
	}

	@Override
	public StandardOutputConfig convert(Map<String, Object> data) {
		StandardOutputConfig conf = new StandardOutputConfig();
		super.configureObject(conf, data);
		
		return conf;
	}
	
	@Override
	public String id() {
		return OutputConfigTypes.STANDARD_OUTPUT.name();
	}
}