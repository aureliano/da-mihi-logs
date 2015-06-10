package com.github.aureliano.damihilogs.converter.schedule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallyAtSpecificTimeSchedule;

public class ExecutePeriodicallyAtSpecificTimeScheduleConverterTest {

	@Test
	public void testConvertExecutePeriodicallyAtSpecificTimeSchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_periodically_at_specific_time");
		map.put("hour", 10);
		map.put("minute", 45);
		map.put("second", 15);
		
		ExecutePeriodicallyAtSpecificTimeScheduleConverter converter = new ExecutePeriodicallyAtSpecificTimeScheduleConverter();
		
		ExecutePeriodicallyAtSpecificTimeSchedule scheduling = (ExecutePeriodicallyAtSpecificTimeSchedule) converter.convert(map);
		Assert.assertEquals(new Integer(10), scheduling.getHour());
		Assert.assertEquals(new Integer(45), scheduling.getMinute());
		Assert.assertEquals(new Integer(15), scheduling.getSecond());
	}
}