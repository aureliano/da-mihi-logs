package com.github.aureliano.damihilogs.schedule;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class ExecutePeriodicallyAtSpecificTimeScheduleTest {
	
	private ExecutePeriodicallyAtSpecificTimeSchedule scheduler;
	
	@Before
	public void beforeTest() {
		this.scheduler = new ExecutePeriodicallyAtSpecificTimeSchedule()
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
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Invalid hour for scheduling. Got " + hour + " but expected >= 0 and <= 23", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
		
		hour = 24;
		gotException = false;
		this.scheduler.withHour(hour);
		
		try {
			this.scheduler.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Invalid hour for scheduling. Got " + hour + " but expected >= 0 and <= 23", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
	}
	
	@Test
	public void testMinuteValidation() {
		int minute = -1;
		boolean gotException = false;
		this.scheduler.withMinute(minute);
		
		try {
			this.scheduler.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Invalid minute for scheduling. Got " + minute + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
		
		minute = 60;
		gotException = false;
		this.scheduler.withMinute(minute);
		
		try {
			this.scheduler.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Invalid minute for scheduling. Got " + minute + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
	}
	
	@Test
	public void testSecondValidation() {
		int second = -1;
		boolean gotException = false;
		this.scheduler.withSecond(second);
		
		try {
			this.scheduler.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Invalid second for scheduling. Got " + second + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
		
		second = 60;
		gotException = false;
		this.scheduler.withSecond(second);
		
		try {
			this.scheduler.validation();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Invalid second for scheduling. Got " + second + " but expected >= 0 and <= 59", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
	}
	
	@Test
	public void testValidation() {
		this.scheduler.validation();
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name(), new ExecutePeriodicallyAtSpecificTimeSchedule().id());
	}
}