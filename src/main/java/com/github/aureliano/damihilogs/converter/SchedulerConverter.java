package com.github.aureliano.damihilogs.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;
import com.github.aureliano.damihilogs.schedule.ExecuteOnceAtSpecificTimeSchedule;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallyAtSpecificTimeSchedule;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallySchedule;
import com.github.aureliano.damihilogs.schedule.SchedulerTypes;

public class SchedulerConverter implements IConfigurationConverter<EventCollectionSchedule> {
	
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SchedulerConverter() {
		super();
	}

	@Override
	public EventCollectionSchedule convert(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		
		if (SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name().equalsIgnoreCase(type)) {
			return this.createExecuteOnceAtSpecificTimeSchedule(data);
		} else if (SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name().equalsIgnoreCase(type)) {
			return this.createExecutePeriodicallyAtSpecificTimeSchedule(data);
		} else if (SchedulerTypes.EXECUTE_PERIODICALLY.name().equalsIgnoreCase(type)) {
			return this.createExecutePeriodicallySchedule(data);
		} else {
			throw new DaMihiLogsException("Scheduling type '" + type + "' not supported. Expected one of: " + Arrays.toString(SchedulerTypes.values()));
		}
	}
	
	private ExecuteOnceAtSpecificTimeSchedule createExecuteOnceAtSpecificTimeSchedule(Map<String, Object> data) {
		String value = StringHelper.parse(data.get("startupTime"));
		if (StringHelper.isEmpty(value)) {
			throw new DaMihiLogsException("Property startupTime was expected in scheduler configuration.");
		}
		
		Date date = null;
		try {
			date = DATE_FORMATTER.parse(StringHelper.parse(data.get("startupTime")));
		} catch (ParseException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return new ExecuteOnceAtSpecificTimeSchedule().withStartupTime(date);
	}
	
	private ExecutePeriodicallyAtSpecificTimeSchedule createExecutePeriodicallyAtSpecificTimeSchedule(Map<String, Object> data) {
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
	
	private EventCollectionSchedule createExecutePeriodicallySchedule(Map<String, Object> data) {
		ExecutePeriodicallySchedule scheduling = new ExecutePeriodicallySchedule();
		
		String value = StringHelper.parse(data.get("delay"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property delay was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withDelay(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("period"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
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
}