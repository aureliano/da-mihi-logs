package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Iterator;
import java.util.Map;

import com.github.aureliano.evtbridge.converter.ConverterType;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;

public class SchedulerConverter implements IConfigurationConverter<IScheduler> {

	public SchedulerConverter() {
		super();
	}

	@Override
	public IScheduler convert(Map<String, Object> map) {
		Map<String, Object> configuration = DataHelper.getAsHash(map, "scheduler");
		if (configuration == null) {
			return null;
		}
		Iterator<String> keys = configuration.keySet().iterator();

		String type = (keys.hasNext()) ? keys.next() : "";
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) configuration.get(type);
		
		return SchedulerConverterFactory.createConverter(type).convert(data);
	}
	
	@Override
	public String id() {
		return ConverterType.SCHEDULER.name();
	}
}