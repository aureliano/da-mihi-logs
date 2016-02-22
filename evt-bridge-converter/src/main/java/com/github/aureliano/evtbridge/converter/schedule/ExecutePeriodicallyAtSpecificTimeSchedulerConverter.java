package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.schedule.ExecutePeriodicallyAtSpecificTimeScheduler;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class ExecutePeriodicallyAtSpecificTimeSchedulerConverter implements IConfigurationConverter<IScheduler> {

	public ExecutePeriodicallyAtSpecificTimeSchedulerConverter() {
		super();
	}

	@Override
	public IScheduler convert(Map<String, Object> data) {
		ExecutePeriodicallyAtSpecificTimeScheduler scheduling = new ExecutePeriodicallyAtSpecificTimeScheduler();
		
		String value = StringHelper.parse(data.get("hour"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new ConfigurationConverterException("Property hour was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withHour(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("minute"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new ConfigurationConverterException("Property minute was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withMinute(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("second"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new ConfigurationConverterException("Property second was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withSecond(Integer.parseInt(value));
		}
		
		return scheduling;
	}
	
	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name();
	}
}