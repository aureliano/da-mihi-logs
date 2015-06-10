package com.github.aureliano.damihilogs.converter.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class InputConverter implements IConfigurationConverter<List<IConfigInput>> {

	public InputConverter() {
		super();
	}

	@Override
	public List<IConfigInput> convert(Map<String, Object> data) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("inputs");
		if (list == null) {
			return Collections.emptyList();
		}
		
		List<IConfigInput> inputs = new ArrayList<IConfigInput>();
		
		for (Map<String, Object> outputMap : list) {
			inputs.add(this.convertInput(outputMap));
		}
		
		return inputs;
	}

	private IConfigInput convertInput(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		return (IConfigInput) InputConverterFactory.createConverter(type).convert(data);
	}
}