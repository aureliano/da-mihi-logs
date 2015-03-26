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
		boolean gotException = false;
		
		try {
			e.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("You must provide a startup time for scheduling.", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
		
		e.withStartupTime(c.getTime());
		gotException = false;
		try {
			e.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Startup time for scheduling must be greater than now.", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
		
		c.add(Calendar.DAY_OF_MONTH, 1);
		e.withStartupTime(c.getTime()).validation();
	}
}