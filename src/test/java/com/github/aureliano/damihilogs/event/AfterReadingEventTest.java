package com.github.aureliano.defero.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.exception.DeferoException;

public class AfterReadingEventTest {

	@Test
	public void testGetLineCounter() {
		Assert.assertEquals(25, new AfterReadingEvent(25, false, null).getLineCounter());
	}
	
	@Test
	public void testIsAccepted() {
		Assert.assertTrue(new AfterReadingEvent(25, true, null).isAccepted());
		Assert.assertFalse(new AfterReadingEvent(25, false, null).isAccepted());
	}
	
	@Test
	public void testGetData() {
		Assert.assertNull(new AfterReadingEvent(25, false, null).getData());
		Assert.assertEquals("getData", new AfterReadingEvent(25, false, "getData").getData());
	}
	
	@Test
	public void testSetData() {
		AfterReadingEvent event = new AfterReadingEvent(25, false, null);
		
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