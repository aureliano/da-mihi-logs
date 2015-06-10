package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.TimeHelper;
import com.github.aureliano.damihilogs.schedule.ExecuteOnceAtSpecificTimeSchedule;

public class ExecuteOnceAtSpecificTimeScheduleConverterTest {

	private ExecuteOnceAtSpecificTimeScheduleConverter converter;
	
	public ExecuteOnceAtSpecificTimeScheduleConverterTest() {
		this.converter = new ExecuteOnceAtSpecificTimeScheduleConverter();
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
		map.put("startupTime", TimeHelper.formatDateTime(date));
		
		ExecuteOnceAtSpecificTimeSchedule scheduling = (ExecuteOnceAtSpecificTimeSchedule) this.converter.convert(map);
		Assert.assertEquals(TimeHelper.formatDateTime(date), TimeHelper.formatDateTime(scheduling.getStartupTime()));
	}
}