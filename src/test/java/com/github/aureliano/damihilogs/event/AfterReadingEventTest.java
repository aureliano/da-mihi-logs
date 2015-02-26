package com.github.aureliano.damihilogs.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DeferoException;

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
	
	@Test
	public void testSetData() {
		AfterReadingEvent event = new AfterReadingEvent(25, null);
		
		event.setData(null);
		Assert.assertNull(event.getData());
		
		event.setData("test");
		Assert.assertEquals("test", event.getData());
		
		event.setData(null);
		Assert.assertNull(event.getData());
		
		event.setData("new text");
		Assert.assertEquals("new text", event.getData());
		
		try {
			event.setData(45);
		} catch (DeferoException ex) {
			Assert.assertEquals("You cannot set java.lang.Integer to java.lang.String", ex.getMessage());
		}
	}
}