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
			Assert.assertEquals("Scheduling type 'null' not supported. Expected one of: " + Arrays.toString(SchedulerConverter.SCHEDULING_TYPES), ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecuteOnceAtSpecificTimeScheduleError() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executeOnceAtSpecificTime");
		
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
		map.put("type", "executeOnceAtSpecificTime");
		map.put("startupTime", DATE_FORMATTER.format(date));
		
		ExecuteOnceAtSpecificTimeSchedule scheduling = (ExecuteOnceAtSpecificTimeSchedule) this.converter.convert(map);
		Assert.assertEquals(DATE_FORMATTER.format(date), DATE_FORMATTER.format(scheduling.getStartupTime()));
	}
	
	@Test
	public void testConvertPeriodicallyAtSpecificTimeScheduleErrorHour() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodicallyAtSpecificTime");
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property hour was expected to match \\d+ pattern in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertPeriodicallyAtSpecificTimeScheduleErrorMinute() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodicallyAtSpecificTime");
		map.put("hour", 10);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property minute was expected to match \\d+ pattern in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertPeriodicallyAtSpecificTimeScheduleErrorSecond() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodicallyAtSpecificTime");
		map.put("hour", 10);
		map.put("minute", 45);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property second was expected to match \\d+ pattern in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecutePeriodicallyAtSpecificTimeSchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodicallyAtSpecificTime");
		map.put("hour", 10);
		map.put("minute", 45);
		map.put("second", 15);
		
		ExecutePeriodicallyAtSpecificTimeSchedule scheduling = (ExecutePeriodicallyAtSpecificTimeSchedule) this.converter.convert(map);
		Assert.assertEquals(new Integer(10), scheduling.getHour());
		Assert.assertEquals(new Integer(45), scheduling.getMinute());
		Assert.assertEquals(new Integer(15), scheduling.getSecond());
	}
	
	@Test
	public void testConvertExecutePeriodicallyScheduleErrorDelay() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodically");
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property delay was expected to match \\d+ pattern in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecutePeriodicallyScheduleErrorPeriod() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodically");
		map.put("delay", 1);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property period was expected to match \\d+ pattern in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecutePeriodicallyScheduleErrorTimeUnit() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodically");
		map.put("delay", 1);
		map.put("period", 2);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property timeUnit was expected to be one of: " + SchedulerConverter.TIME_UNITS + " but got null", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecutePeriodicallySchedule() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "executePeriodically");
		map.put("delay", 1);
		map.put("period", 2);
		map.put("timeUnit", TimeUnit.HOURS.name().toLowerCase());
		
		ExecutePeriodicallySchedule scheduling = (ExecutePeriodicallySchedule) this.converter.convert(map);
		Assert.assertEquals(new Long(1), scheduling.getDelay());
		Assert.assertEquals(new Long(2), scheduling.getPeriod());
		Assert.assertEquals(TimeUnit.HOURS, scheduling.getTimeUnit());
	}
}