package com.github.aureliano.damihilogs.converter.input;

import java.util.Arrays;

import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;

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
		} else {
			throw new DaMihiLogsException("Input config type '" + type + "' not supported. Expected one of: " + Arrays.toString(InputConfigTypes.values()));
		}
	}
}