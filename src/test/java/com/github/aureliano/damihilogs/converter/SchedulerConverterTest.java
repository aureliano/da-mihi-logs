package com.github.aureliano.damihilogs.converter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.schedule.ExecuteOnceAtSpecificTimeSchedule;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallyAtSpecificTimeSchedule;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallySchedule;
import com.github.aureliano.damihilogs.schedule.SchedulerTypes;

public class SchedulerConverterTest {

private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private SchedulerConverter converter;
	
	public SchedulerConverterTest() {
		this.converter = new SchedulerConverter();
	}
	
	@Test
	public void testConvertExecuteScheduleNonExistent() {
		try {
			this.converter.convert(new HashMap<String, Object>());
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Scheduling type 'null' not supported. Expected one of: " + Arrays.toString(SchedulerTypes.values()), ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecuteOnceAtSpecificTimeScheduleError() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_once_at_specific_time");
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property startupTime was expected in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecuteOnceAtSpecificTimeSchedule() {
		Date date = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_once_at_specific_time");
		map.put("startupTime", DATE_FORMATTER.format(date));
		
		ExecuteOnceAtSpecificTimeSchedule scheduling = (ExecuteOnceAtSpecificTimeSchedule) this.converter.convert(map);
		Assert.assertEquals(DATE_FORMATTER.format(date), DATE_FORMATTER.format(scheduling.getStartupTime()));
	}
	
	@Test
	public void testConvertExecutePeriodicallyAtSpecificTimeSchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_periodically_at_specific_time");
		map.put("hour", 10);
		map.put("minute", 45);
		map.put("second", 15);
		
		ExecutePeriodicallyAtSpecificTimeSchedule scheduling = (ExecutePeriodicallyAtSpecificTimeSchedule) this.converter.convert(map);
		Assert.assertEquals(new Integer(10), scheduling.getHour());
		Assert.assertEquals(new Integer(45), scheduling.getMinute());
		Assert.assertEquals(new Integer(15), scheduling.getSecond());
	}
	
	@Test
	public void testConvertExecutePeriodicallySchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_periodically");
		map.put("delay", 1);
		map.put("period", 2);
		map.put("timeUnit", TimeUnit.HOURS.name().toLowerCase());
		
		ExecutePeriodicallySchedule scheduling = (ExecutePeriodicallySchedule) this.converter.convert(map);
		Assert.assertEquals(new Long(1), scheduling.getDelay());
		Assert.assertEquals(new Long(2), scheduling.getPeriod());
		Assert.assertEquals(TimeUnit.HOURS, scheduling.getTimeUnit());
	}
}