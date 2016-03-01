package com.github.aureliano.evtbridge.input.standard;

import java.util.Map;

import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;

public class StandardInputConverter extends AbstractConfigInputConverter<IConfigInput> {

	public StandardInputConverter() {
		super();
	}

	@Override
	public IConfigInput convert(Map<String, Object> data) {
		StandardInputConfig conf = new StandardInputConfig();
		super.configureObject(conf, data);
		
		return conf;
	}
	
	@Override
	public String id() {
		return InputConfigTypes.STANDARD.name();
	}
}