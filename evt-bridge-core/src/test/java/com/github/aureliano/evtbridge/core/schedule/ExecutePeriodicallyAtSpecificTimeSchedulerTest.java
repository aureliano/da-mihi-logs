package com.github.aureliano.evtbridge.core.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ExecutePeriodicallyAtSpecificTimeSchedulerTest {
	
	private ExecutePeriodicallyAtSpecificTimeScheduler scheduler;
	
	@Before
	public void beforeTest() {
		this.scheduler = new ExecutePeriodicallyAtSpecificTimeScheduler()
			.withHour(0)
			.withMinute(0)
			.withSecond(0);
	}

	@Test
	public void testHourValidation() {
		int hour = -1;
		boolean gotException = false;
		this.scheduler.withHour(hour);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			assertEquals("Invalid hour for scheduling. Got " + hour + " but expected >= 0 and <= 23", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			fail();
		}
		
		hour = 24;
		gotException = false;
		this.scheduler.withHour(hour);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			assertEquals("Invalid hour for scheduling. Got " + hour + " but expected >= 0 and <= 23", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			fail();
		}
	}
	
	@Test
	public void testMinuteValidation() {
		int minute = -1;
		boolean gotException = false;
		this.scheduler.withMinute(minute);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			assertEquals("Invalid minute for scheduling. Got " + minute + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			fail();
		}
		
		minute = 60;
		gotException = false;
		this.scheduler.withMinute(minute);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			assertEquals("Invalid minute for scheduling. Got " + minute + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			fail();
		}
	}
	
	@Test
	public void testSecondValidation() {
		int second = -1;
		boolean gotException = false;
		this.scheduler.withSecond(second);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			assertEquals("Invalid second for scheduling. Got " + second + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			fail();
		}
		
		second = 60;
		gotException = false;
		this.scheduler.withSecond(second);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			assertEquals("Invalid second for scheduling. Got " + second + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			fail();
		}
	}
	
	@Test
	public void testValidation() {
		this.scheduler.validation();
	}
	
	@Test
	public void testId() {
		assertEquals(
			SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name(),
			new ExecutePeriodicallyAtSpecificTimeScheduler().id()
		);
	}
}