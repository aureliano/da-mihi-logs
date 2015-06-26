package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallySchedule;
import com.github.aureliano.damihilogs.schedule.SchedulerTypes;

public class ExecutePeriodicallyScheduleConverter implements IConfigurationConverter<EventCollectionSchedule> {

	public ExecutePeriodicallyScheduleConverter() {
		super();
	}

	@Override
	public EventCollectionSchedule convert(Map<String, Object> data) {
		ExecutePeriodicallySchedule scheduling = new ExecutePeriodicallySchedule();
		
		String value = StringHelper.parse(data.get("delay"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new DaMihiLogsException("Property delay was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withDelay(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("period"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new DaMihiLogsException("Property period was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withPeriod(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("timeUnit"));
		if (!StringHelper.isEmpty(value)) {
			if (!TimeHelper.isValidTimeUnit(value)) {
				throw new DaMihiLogsException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
			}
			scheduling.withTimeUnit(TimeUnit.valueOf(value.toUpperCase()));
		}
		
		return scheduling;
	}
	
	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_PERIODICALLY.name();
	}
}