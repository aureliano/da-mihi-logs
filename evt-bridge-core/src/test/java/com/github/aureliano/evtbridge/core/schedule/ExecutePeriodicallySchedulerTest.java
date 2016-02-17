package com.github.aureliano.evtbridge.core.schedule;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ExecutePeriodicallySchedulerTest {

	private ExecutePeriodicallyScheduler scheduler;
	
	@Before
	public void beforeTest() {
		this.scheduler = new ExecutePeriodicallyScheduler()
			.withDelay(0L)
			.withPeriod(0L)
			.withTimeUnit(TimeUnit.HOURS);
	}
	
	@Test
	public void testDelayValidation() {
		long delay = -1;
		boolean gotException = false;
		this.scheduler.withDelay(delay);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			Assert.assertEquals("Invalid delay for scheduling. Got " + delay + " but expected >= 0", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
	}
	
	@Test
	public void testPeriodValidation() {
		long period = -1;
		boolean gotException = false;
		this.scheduler.withPeriod(period);
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			Assert.assertEquals("Invalid period for scheduling. Got " + period + " but expected >= 0", ex.getMessage());
			gotException = true;
		}
		
		if (!gotException) {
			Assert.fail();
		}
	}
	
	@Test
	public void testTimeUnitValidation() {
		this.scheduler.withTimeUnit(null);
		boolean gotException = false;
		
		try {
			this.scheduler.validation();
		} catch (EventBridgeException ex) {
			Assert.assertEquals("Time unit not provided for scheduling.", ex.getMessage());
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
		Assert.assertEquals(
			SchedulerTypes.EXECUTE_PERIODICALLY.name(),
			new ExecutePeriodicallyScheduler().id()
		);
	}
}