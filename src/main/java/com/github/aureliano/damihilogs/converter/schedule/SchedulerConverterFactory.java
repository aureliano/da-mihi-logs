package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Arrays;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;
import com.github.aureliano.damihilogs.schedule.SchedulerTypes;

public final class SchedulerConverterFactory {

	private SchedulerConverterFactory() {
		super();
	}
	
	public static IConfigurationConverter<EventCollectionSchedule> createConverter(String type) {
		if (SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name().equalsIgnoreCase(type)) {
			return new ExecuteOnceAtSpecificTimeScheduleConverter();
		} else if (SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name().equalsIgnoreCase(type)) {
			return new ExecutePeriodicallyAtSpecificTimeScheduleConverter();
		} else if (SchedulerTypes.EXECUTE_PERIODICALLY.name().equalsIgnoreCase(type)) {
			return new ExecutePeriodicallyScheduleConverter();
		} else {
			throw new DaMihiLogsException("Scheduler type '" + type + "' not supported. Expected one of: " + Arrays.toString(SchedulerTypes.values()));
		}
	}
}