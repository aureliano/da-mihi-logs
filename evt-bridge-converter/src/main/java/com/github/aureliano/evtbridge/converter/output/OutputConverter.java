package com.github.aureliano.evtbridge.converter.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.converter.ConverterType;
import com.github.aureliano.evtbridge.core.config.IConfigOutput;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;

public class OutputConverter implements IConfigurationConverter<List<IConfigOutput>> {

	public OutputConverter() {
		super();
	}

	@Override
	public List<IConfigOutput> convert(Map<String, Object> data) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("outputConfigs");
		
		if (list == null) {
			return Collections.emptyList();
		}
		
		List<IConfigOutput> outputs = new ArrayList<IConfigOutput>();
		
		for (Map<String, Object> outputMap : list) {
			outputs.add(this.convertOutput(outputMap));
		}
		
		return outputs;
	}

	private IConfigOutput convertOutput(Map<String, Object> configuration) {
		Iterator<String> keys = configuration.keySet().iterator();

		String type = (keys.hasNext()) ? keys.next() : "";
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) configuration.get(type);
		
		return (IConfigOutput) OutputConverterFactory.createConverter(type).convert(data);
	}
	
	@Override
	public String id() {
		return ConverterType.OUTPUT.name();
	}
}