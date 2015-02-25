package com.github.aureliano.damihilogs.event;

import org.junit.Assert;
import org.junit.Test;

public class StepParseEventTest {

	@Test
	public void testGetParseAttempts() {
		Assert.assertEquals(0, new StepParseEvent(0, null, null).getParseAttempts());
		Assert.assertEquals(30, new StepParseEvent(30, null, null).getParseAttempts());
	}

	@Test
	public void testGetLine() {
		Assert.assertNull(null, new StepParseEvent(0, null, null).getLine());
		Assert.assertEquals("line", new StepParseEvent(0, "line", null).getLine());
	}

	public void testGetCurrentData() {
		Assert.assertNull(null, new StepParseEvent(0, null, null).getCurrentData());
		Assert.assertEquals("current data", new StepParseEvent(0, null, "current data").getCurrentData());
	}
}