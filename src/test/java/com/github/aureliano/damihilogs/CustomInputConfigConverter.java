package com.github.aureliano.damihilogs;

import java.util.Map;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;

public class CustomInputConfigConverter implements IConfigurationConverter<CustomInputConfig> {

	@Override
	public CustomInputConfig convert(Map<String, Object> data) {
		return null;
	}

	@Override
	public String id() {
		return "CUSTOM_INPUT_CONFIG";
	}
}