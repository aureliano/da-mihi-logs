package com.github.aureliano.evtbridge.output.standard;

import java.util.Map;

import com.github.aureliano.evtbridge.core.config.AbstractConfigOutputConverter;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;

public class StandardOutputConverter extends AbstractConfigOutputConverter<StandardOutputConfig> {

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
		return OutputConfigTypes.STANDARD.name();
	}
}