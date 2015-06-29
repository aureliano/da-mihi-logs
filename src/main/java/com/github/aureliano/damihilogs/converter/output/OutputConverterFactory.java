package com.github.aureliano.damihilogs.converter.output;

import java.util.Arrays;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.reg.ApiServiceRegistrator;

public final class OutputConverterFactory {

	private OutputConverterFactory() {
		super();
	}

	public static AbstractOutputConverter<?> createConverter(String type) {
		if (OutputConfigTypes.ELASTIC_SEARCH.name().equalsIgnoreCase(type)) {
			return new ElasticSearchOutputConverter();
		} else if (OutputConfigTypes.FILE_OUTPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new FileOutputConverter();
		} else if (OutputConfigTypes.STANDARD_OUTPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new StandardOutputConverter();
		} else if (OutputConfigTypes.JDBC_OUTPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new JdbcOutputConverter();
		} else if (OutputConfigTypes.TWITTER_OUTPUT.name().startsWith(StringHelper.toString(type).toUpperCase())) {
			return new TwitterOutputConverter();
		} else {
			ApiServiceRegistrator service = ApiServiceRegistrator.instance();
			IConfigurationConverter<?> converter = service.createConverter(type);
			
			if (converter == null) {
				throw new DaMihiLogsException("Output config type '" + type + "' not supported. Expected one of: " + Arrays.toString(OutputConfigTypes.values()));
			}
			return (AbstractOutputConverter<?>) converter;
		}
	}
}