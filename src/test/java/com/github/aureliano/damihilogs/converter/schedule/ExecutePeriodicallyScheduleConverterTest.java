package com.github.aureliano.damihilogs.converter.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallySchedule;

public class ExecutePeriodicallyScheduleConverterTest {

	@Test
	public void testConvertExecutePeriodicallySchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_periodically");
		map.put("delay", 1);
		map.put("period", 2);
		map.put("timeUnit", TimeUnit.HOURS.name().toLowerCase());
		
		ExecutePeriodicallyScheduleConverter converter = new ExecutePeriodicallyScheduleConverter();
		
		ExecutePeriodicallySchedule scheduling = (ExecutePeriodicallySchedule) converter.convert(map);
		Assert.assertEquals(new Long(1), scheduling.getDelay());
		Assert.assertEquals(new Long(2), scheduling.getPeriod());
		Assert.assertEquals(TimeUnit.HOURS, scheduling.getTimeUnit());
	}
}