package com.github.aureliano.damihilogs.event;

import org.junit.Assert;
import org.junit.Test;

public class AfterReadingEventTest {

	@Test
	public void testGetLineCounter() {
		Assert.assertEquals(25, new AfterReadingEvent(25, null).getLineCounter());
	}
	
	@Test
	public void testGetData() {
		Assert.assertNull(new AfterReadingEvent(25, null).getData());
		Assert.assertEquals("getData", new AfterReadingEvent(25, "getData").getData());
	}
}