package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Map;

import com.github.aureliano.damihilogs.converter.ConverterType;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;

public class SchedulerConverter implements IConfigurationConverter<EventCollectionSchedule> {

	public SchedulerConverter() {
		super();
	}

	@Override
	public EventCollectionSchedule convert(Map<String, Object> data) {
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