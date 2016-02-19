package com.github.aureliano.evtbridge.input.external_command;

import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;

public class ExternalCommandInputConverter extends AbstractConfigInputConverter<ExternalCommandInputConfig> {

	public ExternalCommandInputConverter() {
		super();
	}

	@Override
	public ExternalCommandInputConfig convert(Map<String, Object> data) {
		ExternalCommandInputConfig conf = new ExternalCommandInputConfig();
		
		super.configureObject(conf, data);
		conf.withCommand(StringHelper.parse(data.get("command")));
		
		if (data.get("parameters") != null) {
			@SuppressWarnings("unchecked")
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