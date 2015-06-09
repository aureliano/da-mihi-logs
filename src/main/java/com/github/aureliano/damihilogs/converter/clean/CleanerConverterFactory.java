package com.github.aureliano.damihilogs.converter.clean;

import java.util.Arrays;

import com.github.aureliano.damihilogs.clean.CleanerTypes;
import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class CleanerConverterFactory {

	private CleanerConverterFactory() {
		super();
	}
	
	public static IConfigurationConverter<ICleaner> createConverter(String type) {
		if (CleanerTypes.FILE.name().equalsIgnoreCase(type)) {
			return new FileCleanerConverter();
		} else if (CleanerTypes.LOG.name().equalsIgnoreCase(type)) {
			return new LogCleanerConverter();
		} else {
			throw new DaMihiLogsException("Cleaner type '" + type + "' not supported. Expected one of: " + Arrays.toString(CleanerTypes.values()));
		}
	}
}