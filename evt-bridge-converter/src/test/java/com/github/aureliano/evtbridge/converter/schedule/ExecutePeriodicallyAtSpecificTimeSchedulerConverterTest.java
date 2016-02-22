package com.github.aureliano.evtbridge.converter.schedule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.core.schedule.ExecutePeriodicallyAtSpecificTimeScheduler;

public class ExecutePeriodicallyAtSpecificTimeSchedulerConverterTest {

	@Test
	public void testConvertExecutePeriodicallyAtSpecificTimeSchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_periodically_at_specific_time");
		map.put("hour", 10);
		map.put("minute", 45);
		map.put("second", 15);
		
		ExecutePeriodicallyAtSpecificTimeSchedulerConverter converter = new ExecutePeriodicallyAtSpecificTimeSchedulerConverter();
		
		ExecutePeriodicallyAtSpecificTimeScheduler scheduling = (ExecutePeriodicallyAtSpecificTimeScheduler) converter.convert(map);
		Assert.assertEquals(new Integer(10), scheduling.getHour());
		Assert.assertEquals(new Integer(45), scheduling.getMinute());
		Assert.assertEquals(new Integer(15), scheduling.getSecond());
	}
}