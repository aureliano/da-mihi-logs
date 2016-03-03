package com.github.aureliano.evtbridge.converter.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.ConverterType;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;

public class InputConverter implements IConfigurationConverter<List<IConfigInput>> {

	public InputConverter() {
		super();
	}

	@Override
	public List<IConfigInput> convert(Map<String, Object> data) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("inputConfigs");
		
		if (list == null) {
			return Collections.emptyList();
		}
		
		List<IConfigInput> inputs = new ArrayList<IConfigInput>();
		
		for (Map<String, Object> outputMap : list) {
			inputs.add(this.convertInput(outputMap));
		}
		
		return inputs;
	}
	
	@Override
	public String id() {
		return ConverterType.INPUT.name();
	}

	private IConfigInput convertInput(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		return (IConfigInput) InputConverterFactory.createConverter(type).convert(data);
	}
}