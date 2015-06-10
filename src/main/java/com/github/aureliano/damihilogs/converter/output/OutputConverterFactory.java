package com.github.aureliano.damihilogs.converter.output;

import java.util.Arrays;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;

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
		} else {
			throw new DaMihiLogsException("Output config type '" + type + "' not supported. Expected one of: " + Arrays.toString(OutputConfigTypes.values()));
		}
	}
}