package com.github.aureliano.evtbridge.converter.input;

import java.util.Arrays;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.IConfigurationConverter;
import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;

public class InputConverterFactory {

	public InputConverterFactory() {
		super();
	}

	public static AbstractInputConverter<?> createConverter(String type) {
		if (InputConfigTypes.FILE_INPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new FileInputConverter();
		} else if (InputConfigTypes.FILE_TAILER.name().equalsIgnoreCase(type)) {
			return new FileTailerInputConverter();
		} else if (InputConfigTypes.EXTERNAL_COMMAND.name().equalsIgnoreCase(type)) {
			return new ExternalCommandInputConverter();
		} else if (InputConfigTypes.STANDARD_INPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new StandardInputConverter();
		} else if (InputConfigTypes.URL.name().equalsIgnoreCase(type)) {
			return new UrlInputConverter();
		} else if (InputConfigTypes.JDBC_INPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new JdbcInputConverter();
		} else if (InputConfigTypes.TWITTER_INPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new TwitterInputConverter();
		} else {
			ApiServiceRegistrator service = ApiServiceRegistrator.instance();
			IConfigurationConverter<?> converter = service.createConverter(type);
			
			if (converter == null) {
				throw new ConfigurationConverterException("Input config type '" + type + "' not supported. Expected one of: " + Arrays.toString(InputConfigTypes.values()));
			}
			return (AbstractInputConverter<?>) converter;
		}
	}
}