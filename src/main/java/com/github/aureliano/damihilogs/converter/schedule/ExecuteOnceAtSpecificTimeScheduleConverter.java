package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Date;
import java.util.Map;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;
import com.github.aureliano.damihilogs.schedule.ExecuteOnceAtSpecificTimeSchedule;
import com.github.aureliano.damihilogs.schedule.SchedulerTypes;

public class ExecuteOnceAtSpecificTimeScheduleConverter implements IConfigurationConverter<EventCollectionSchedule> {

	public ExecuteOnceAtSpecificTimeScheduleConverter() {
		super();
	}

	@Override
	public EventCollectionSchedule convert(Map<String, Object> data) {
		String value = StringHelper.parse(data.get("startupTime"));
		if (StringHelper.isEmpty(value)) {
			throw new DaMihiLogsException("Property startupTime was expected in scheduler configuration.");
		}
		
		Date date = TimeHelper.parseToDateTime(StringHelper.parse(data.get("startupTime")));
		return new ExecuteOnceAtSpecificTimeSchedule().withStartupTime(date);
	}
	
	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name();
	}
}