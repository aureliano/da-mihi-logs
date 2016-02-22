package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Arrays;

import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public final class SchedulerConverterFactory {

	private SchedulerConverterFactory() {
		super();
	}
	
	public static IConfigurationConverter<IScheduler> createConverter(String type) {
		if (SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name().equalsIgnoreCase(type)) {
			return new ExecuteOnceAtSpecificTimeSchedulerConverter();
		} else if (SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name().equalsIgnoreCase(type)) {
			return new ExecutePeriodicallyAtSpecificTimeSchedulerConverter();
		} else if (SchedulerTypes.EXECUTE_PERIODICALLY.name().equalsIgnoreCase(type)) {
			return new ExecutePeriodicallySchedulerConverter();
		} else {
			throw new ConfigurationConverterException("Scheduler type '" + type + "' not supported. Expected one of: " + Arrays.toString(SchedulerTypes.values()));
		}
	}
}