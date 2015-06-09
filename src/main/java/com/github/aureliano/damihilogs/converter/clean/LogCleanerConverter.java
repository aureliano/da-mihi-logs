package com.github.aureliano.damihilogs.converter.clean;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.clean.LogCleaner;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;

public class LogCleanerConverter implements IConfigurationConverter<ICleaner> {

	public LogCleanerConverter() {
		super();
	}

	@Override
	public ICleaner convert(Map<String, Object> data) {
		LogCleaner cleaner = new LogCleaner();
		if (data.get("removeLogDataFilesAfter") != null) {
			this.configureDataLogCleaner(cleaner, (Map<String, Object>) data.get("removeLogDataFilesAfter"));
		}
		if (data.get("removeLogEchoFilesAfter") != null) {
			this.configureEchoLogCleaner(cleaner, (Map<String, Object>) data.get("removeLogEchoFilesAfter"));
		}
		
		return cleaner;
	}
	
	private void configureDataLogCleaner(LogCleaner cleaner, Map<String, Object> data) {
		String value = StringHelper.parse(data.get("fileNameRegex"));
		cleaner.withDataFileNameRegex(value);
		
		value = StringHelper.parse(data.get("timeUnit"));
		if (!StringHelper.isEmpty(value)) {
			if (!TimeHelper.isValidTimeUnit(value)) {
				throw new DaMihiLogsException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
			}
			
			String time = StringHelper.parse(data.get("value"));
			
			if (StringHelper.isEmpty(time) || !time.matches("\\d+")) {
				throw new DaMihiLogsException("Property removeLogDataFilesAfter => value was expected to match \\d+ pattern in cleaner configuration.");
			}
			
			if (value.equalsIgnoreCase(TimeUnit.MILLISECONDS.name())
					|| value.equalsIgnoreCase(TimeUnit.MICROSECONDS.name())
					|| value.equalsIgnoreCase(TimeUnit.NANOSECONDS.name())) {

				throw new DaMihiLogsException("Unsupported time unit. The smallest time unit supported for cleaner is " +
						TimeUnit.MINUTES + " but got " + value);
			}
			
			String methodName = "removeLogDataFilesAfter" + StringHelper.capitalize(value.toLowerCase());
			Class<?>[] types = new Class<?>[] { Integer.class };
			Integer[] parameters = new Integer[] {Integer.parseInt(time)};
			
			ReflectionHelper.callMethod(cleaner, methodName, types, parameters);
		}
	}
	
	private void configureEchoLogCleaner(LogCleaner cleaner, Map<String, Object> data) {
		String value = StringHelper.parse(data.get("fileNameRegex"));
		cleaner.withDataFileNameRegex(value);
		
		value = StringHelper.parse(data.get("timeUnit"));
		if (!StringHelper.isEmpty(value)) {
			if (!TimeHelper.isValidTimeUnit(value)) {
				throw new DaMihiLogsException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
			}
			
			String time = StringHelper.parse(data.get("value"));
			
			if (StringHelper.isEmpty(time) || !time.matches("\\d+")) {
				throw new DaMihiLogsException("Property removeLogEchoFilesAfter => value was expected to match \\d+ pattern in cleaner configuration.");
			}
			
			if (value.equalsIgnoreCase(TimeUnit.MILLISECONDS.name())
					|| value.equalsIgnoreCase(TimeUnit.MICROSECONDS.name())
					|| value.equalsIgnoreCase(TimeUnit.NANOSECONDS.name())) {

				throw new DaMihiLogsException("Unsupported time unit. The smallest time unit supported for cleaner is " +
						TimeUnit.MINUTES + " but got " + value);
			}
			
			String methodName = "removeLogEchoFilesAfter" + StringHelper.capitalize(value.toLowerCase());
			Class<?>[] types = new Class<?>[] { Integer.class };
			Integer[] parameters = new Integer[] {Integer.parseInt(time)};
			
			ReflectionHelper.callMethod(cleaner, methodName, types, parameters);
		}
	}
}