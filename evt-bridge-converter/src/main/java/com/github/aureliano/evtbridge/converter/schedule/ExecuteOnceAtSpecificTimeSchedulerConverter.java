package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Date;
import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.helper.TimeHelper;
import com.github.aureliano.evtbridge.core.schedule.ExecuteOnceAtSpecificTimeScheduler;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class ExecuteOnceAtSpecificTimeSchedulerConverter implements IConfigurationConverter<IScheduler> {

	public ExecuteOnceAtSpecificTimeSchedulerConverter() {
		super();
	}

	@Override
	public IScheduler convert(Map<String, Object> data) {
		String value = StringHelper.parse(data.get("startupTime"));
		if (StringHelper.isEmpty(value)) {
			throw new ConfigurationConverterException("Property startupTime was expected in scheduler configuration.");
		}
		
		Date date = TimeHelper.parseToDateTime(StringHelper.parse(data.get("startupTime")));
		return new ExecuteOnceAtSpecificTimeScheduler().withStartupTime(date);
	}
	
	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name();
	}
}