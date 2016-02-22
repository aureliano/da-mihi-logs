package com.github.aureliano.evtbridge.converter.output;

import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.AbstractConfigOutputConverter;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;

public final class OutputConverterFactory {

	private OutputConverterFactory() {
		super();
	}

	public static AbstractConfigOutputConverter<?> createConverter(String type) {
		ApiServiceRegistrator service = ApiServiceRegistrator.instance();
		IConfigurationConverter<?> converter = service.createConverter(type);
		
		if (converter == null) {
			throw new ConfigurationConverterException("Output config type converter '" + type + "' not registered.");
		}
		return (AbstractConfigOutputConverter<?>) converter;
	}
}