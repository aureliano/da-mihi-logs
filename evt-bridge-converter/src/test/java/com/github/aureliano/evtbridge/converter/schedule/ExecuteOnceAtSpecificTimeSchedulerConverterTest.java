package com.github.aureliano.evtbridge.converter.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.core.helper.TimeHelper;
import com.github.aureliano.evtbridge.core.schedule.ExecuteOnceAtSpecificTimeScheduler;

public class ExecuteOnceAtSpecificTimeSchedulerConverterTest {

	private ExecuteOnceAtSpecificTimeSchedulerConverter converter;
	
	public ExecuteOnceAtSpecificTimeSchedulerConverterTest() {
		this.converter = new ExecuteOnceAtSpecificTimeSchedulerConverter();
	}
	
	@Test
	public void testConvertExecuteOnceAtSpecificTimeScheduleError() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_once_at_specific_time");
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (ConfigurationConverterException ex) {
			Assert.assertEquals("Property startupTime was expected in scheduler configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testConvertExecuteOnceAtSpecificTimeSchedule() {
		Date date = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "execute_once_at_specific_time");
		map.put("startupTime", TimeHelper.formatDateTime(date));
		
		ExecuteOnceAtSpecificTimeScheduler scheduling = (ExecuteOnceAtSpecificTimeScheduler) this.converter.convert(map);
		Assert.assertEquals(TimeHelper.formatDateTime(date), TimeHelper.formatDateTime(scheduling.getStartupTime()));
	}
}