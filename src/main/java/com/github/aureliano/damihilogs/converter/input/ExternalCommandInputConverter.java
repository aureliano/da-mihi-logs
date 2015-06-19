package com.github.aureliano.damihilogs.converter.input;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInputConfig;
import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class ExternalCommandInputConverter extends AbstractInputConverter<ExternalCommandInputConfig> {

	public ExternalCommandInputConverter() {
		super();
	}

	@Override
	public ExternalCommandInputConfig convert(Map<String, Object> data) {
		ExternalCommandInputConfig conf = new ExternalCommandInputConfig();
		
		super.configureObject(conf, data);
		conf.withCommand(StringHelper.parse(data.get("command")));
		
		if (data.get("parameters") != null) {
			List<String> parameters = (List<String>) data.get("parameters");
			for (String parameter : parameters) {
				conf.addParameter(parameter);
			}
		}
		
		return conf;
	}
	
	@Override
	public String id() {
		return InputConfigTypes.EXTERNAL_COMMAND.name();
	}
}