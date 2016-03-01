package com.github.aureliano.evtbridge.converter.helper;

import java.util.Map;

import com.github.aureliano.evtbridge.converter.ConfigurationConverter;
import com.github.aureliano.evtbridge.converter.ConfigurationSourceType;
import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.helper.FileHelper;

public final class ConversionHelper {

	private ConversionHelper() {}
	
	public static EventCollectorConfiguration loadConfiguration(String path, ConfigurationSourceType type) {
		switch (type) {
		case YAML:
			return convertFromYaml(path);
		case JSON:
			return convertFromJson(path);
		default:
			throw new ConfigurationConverterException("Configuration source type '" + type + "' not supported.");
		}
	}
	
	private static EventCollectorConfiguration convertFromJson(String path) {
		String text = FileHelper.readFile(path);
		@SuppressWarnings("unchecked")
		Map<String, Object> data = DataHelper.jsonStringToObject(text, Map.class);
		
		return new ConfigurationConverter().convert(data);
	}
	
	private static EventCollectorConfiguration convertFromYaml(String path) {
		String text = FileHelper.readFile(path);
		@SuppressWarnings("unchecked")
		Map<String, Object> data = DataHelper.yamlStringToObject(text, Map.class);
		
		return new ConfigurationConverter().convert(data);
	}
}