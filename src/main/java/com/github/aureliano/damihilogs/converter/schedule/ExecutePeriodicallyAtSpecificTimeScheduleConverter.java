package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Map;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallyAtSpecificTimeSchedule;

public class ExecutePeriodicallyAtSpecificTimeScheduleConverter implements IConfigurationConverter<EventCollectionSchedule> {

	public ExecutePeriodicallyAtSpecificTimeScheduleConverter() {
		super();
	}

	@Override
	public EventCollectionSchedule convert(Map<String, Object> data) {
		ExecutePeriodicallyAtSpecificTimeSchedule scheduling = new ExecutePeriodicallyAtSpecificTimeSchedule();
		
		String value = StringHelper.parse(data.get("hour"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property hour was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withHour(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("minute"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property minute was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withMinute(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("second"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property second was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withSecond(Integer.parseInt(value));
		}
		
		return scheduling;
	}
}