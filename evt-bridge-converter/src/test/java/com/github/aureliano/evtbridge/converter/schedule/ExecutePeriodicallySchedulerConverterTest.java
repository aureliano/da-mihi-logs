package com.github.aureliano.evtbridge.converter.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.core.schedule.ExecutePeriodicallyScheduler;

public class ExecutePeriodicallySchedulerConverterTest {

	@Test
	public void testConvertExecutePeriodicallySchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_periodically");
		map.put("delay", 1);
		map.put("period", 2);
		map.put("timeUnit", TimeUnit.HOURS.name().toLowerCase());
		
		ExecutePeriodicallySchedulerConverter converter = new ExecutePeriodicallySchedulerConverter();
		
		ExecutePeriodicallyScheduler scheduling = (ExecutePeriodicallyScheduler) converter.convert(map);
		Assert.assertEquals(new Long(1), scheduling.getDelay());
		Assert.assertEquals(new Long(2), scheduling.getPeriod());
		Assert.assertEquals(TimeUnit.HOURS, scheduling.getTimeUnit());
	}
}