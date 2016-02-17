package com.github.aureliano.evtbridge.core.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.schedule.ExecuteOnceAtSpecificTimeScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class ExecuteOnceAtSpecificTimeSchedulerTest {

	@Test
	public void testValidation() {
		ExecuteOnceAtSpecificTimeScheduler e = new ExecuteOnceAtSpecificTimeScheduler();
		Calendar c = Calendar.getInstance();
		
		try {
			e.validation();
			fail("An exception was expected.");
		} catch (EventBridgeException ex) {
			assertEquals("You must provide a startup time for scheduling.", ex.getMessage());
		}

		c.add(Calendar.DAY_OF_MONTH, -1);
		e.withStartupTime(c.getTime());
		try {
			e.validation();
			fail("An exception was expected.");
		} catch (EventBridgeException ex) {
			assertEquals("Startup time for scheduling must be greater than now.", ex.getMessage());
		}
		
		c.add(Calendar.DAY_OF_MONTH, 2);
		e.withStartupTime(c.getTime()).validation();
	}
	
	@Test
	public void testId() {
		assertEquals(
			SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name(),
			new ExecuteOnceAtSpecificTimeScheduler().id()
		);
	}
}