package com.github.aureliano.damihilogs.converter.input;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class ExternalCommandInputConverter extends AbstractInputConverter<ExternalCommandInput> {

	public ExternalCommandInputConverter() {
		super();
	}

	@Override
	public ExternalCommandInput convert(Map<String, Object> data) {
		ExternalCommandInput conf = new ExternalCommandInput();
		
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
}