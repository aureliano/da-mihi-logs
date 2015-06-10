package com.github.aureliano.damihilogs.converter.input;

import java.util.Map;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;

public class StandardInputConverter extends AbstractInputConverter<IConfigInput> {

	public StandardInputConverter() {
		super();
	}

	@Override
	public IConfigInput convert(Map<String, Object> data) {
		StandardInputConfig conf = new StandardInputConfig();
		super.configureObject(conf, data);
		
		return conf;
	}
}