package com.github.aureliano.damihilogs.converter.clean;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.clean.CleanerTypes;
import com.github.aureliano.damihilogs.clean.FileCleaner;
import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;

public class FileCleanerConverter implements IConfigurationConverter<ICleaner> {

	public FileCleanerConverter() {
		super();
	}

	@Override
	public ICleaner convert(Map<String, Object> data) {
		FileCleaner cleaner = null;
		String value = StringHelper.parse(data.get("directory"));
		if (!StringHelper.isEmpty(value)) {
			cleaner = new FileCleaner(new File(value));
		} else {
			cleaner = new FileCleaner(null);
		}
		
		value = StringHelper.parse(data.get("fileNameRegex"));
		cleaner.withFileNameRegex(value);
		
		if (data.get("removeFilesAfter") != null) {
			value = StringHelper.parse(data.get("timeUnit"));
			if (!StringHelper.isEmpty(value)) {
				if (!TimeHelper.isValidTimeUnit(value)) {
					throw new DaMihiLogsException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
				}
				
				Map<String, Object> removeConfig = (Map<String, Object>) data.get("removeFilesAfter");
				String time = StringHelper.parse(removeConfig.get("value"));
				
				if (StringHelper.isEmpty(time) || !StringHelper.isNumeric(time)) {
					throw new DaMihiLogsException("Property removeFilesAfter => value was expected to match \\d+ pattern in cleaner configuration.");
				}
				
				if (value.equalsIgnoreCase(TimeUnit.MILLISECONDS.name())
						|| value.equalsIgnoreCase(TimeUnit.MICROSECONDS.name())
						|| value.equalsIgnoreCase(TimeUnit.NANOSECONDS.name())) {

					throw new DaMihiLogsException("Unsupported time unit. The smallest time unit supported for cleaner is " +
							TimeUnit.MINUTES + " but got " + value);
				}
				
				String methodName = "removeFilesAfter" + StringHelper.capitalize(value.toLowerCase());
				Class<?>[] types = new Class<?>[] { Integer.class };
				Integer[] parameters = new Integer[] {Integer.parseInt(time)};
				
				ReflectionHelper.callMethod(cleaner, methodName, types, parameters);
			}
		}
		
		return cleaner;
	}
	
	@Override
	public String id() {
		return CleanerTypes.FILE.name();
	}
}