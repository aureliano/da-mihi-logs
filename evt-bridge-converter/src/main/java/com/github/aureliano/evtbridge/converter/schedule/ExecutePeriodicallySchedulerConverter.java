package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.helper.TimeHelper;
import com.github.aureliano.evtbridge.core.schedule.ExecutePeriodicallyScheduler;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class ExecutePeriodicallySchedulerConverter implements IConfigurationConverter<IScheduler> {

	public ExecutePeriodicallySchedulerConverter() {
		super();
	}

	@Override
	public IScheduler convert(Map<String, Object> data) {
		ExecutePeriodicallyScheduler scheduling = new ExecutePeriodicallyScheduler();
		
		String value = StringHelper.parse(data.get("delay"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new ConfigurationConverterException("Property delay was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withDelay(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("period"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new ConfigurationConverterException("Property period was expected to match \\d+ pattern in scheduler configuration.");
			}
			scheduling.withPeriod(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("timeUnit"));
		if (!StringHelper.isEmpty(value)) {
			if (!TimeHelper.isValidTimeUnit(value)) {
				throw new ConfigurationConverterException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
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