package com.github.aureliano.evtbridge.converter.input;

import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;

public class InputConverterFactory {

	public InputConverterFactory() {
		super();
	}

	public static AbstractConfigInputConverter<?> createConverter(String type) {
		ApiServiceRegistrator service = ApiServiceRegistrator.instance();
		IConfigurationConverter<?> converter = service.createConverter(type);
		
		if (converter == null) {
			throw new ConfigurationConverterException("Input config type converter '" + type + "' not registered.");
		}
		return (AbstractConfigInputConverter<?>) converter;
	}
}