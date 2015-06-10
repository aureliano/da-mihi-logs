package com.github.aureliano.damihilogs.schedule;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.schedule.ExecuteOnceAtSpecificTimeSchedule;

public class ExecuteOnceAtSpecificTimeScheduleTest {

	@Test
	public void testValidation() {
		ExecuteOnceAtSpecificTimeSchedule e = new ExecuteOnceAtSpecificTimeSchedule();
		Calendar c = Calendar.getInstance();
		
		try {
			e.validation();
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("You must provide a startup time for scheduling.", ex.getMessage());
		}

		c.add(Calendar.DAY_OF_MONTH, -1);
		e.withStartupTime(c.getTime());
		try {
			e.validation();
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Startup time for scheduling must be greater than now.", ex.getMessage());
		}
		
		c.add(Calendar.DAY_OF_MONTH, 2);
		e.withStartupTime(c.getTime()).validation();
	}
}