package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.ConverterType;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;

public class SchedulerConverter implements IConfigurationConverter<IScheduler> {

	public SchedulerConverter() {
		super();
	}

	@Override
	public IScheduler convert(Map<String, Object> data) {
		Map<String, Object> map = DataHelper.getAsHash(data, "scheduler");
		if (map == null) {
			return null;
		}
		
		String type = StringHelper.parse(map.get("type"));
		return SchedulerConverterFactory.createConverter(type).convert(map);
	}
	
	@Override
	public String id() {
		return ConverterType.SCHEDULER.name();
	}
}