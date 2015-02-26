package com.github.aureliano.damihilogs.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.FileOutputConfig;

public class AfterWritingEventTest {

	@Test
	public void testGetOutputConfiguration() {
		Assert.assertNull(new AfterWritingEvent(null, false, null).getOutputConfiguration());
		
		AfterWritingEvent event = new AfterWritingEvent(new FileOutputConfig().withEncoding("ISO-8859-1").withAppend(true).withFile("/there/is/not/file"), false, null);
		FileOutputConfig cfg = (FileOutputConfig) event.getOutputConfiguration();
		
		Assert.assertEquals("ISO-8859-1", cfg.getEncoding());
		Assert.assertTrue(cfg.isAppend());
		Assert.assertEquals(cfg.getFile().getPath(), "/there/is/not/file");
	}
	
	@Test
	public void testIsAccepted() {
		Assert.assertTrue(new AfterWritingEvent(null, true, null).isAccepted());
		Assert.assertFalse(new AfterWritingEvent(null, false, null).isAccepted());
	}
	
	@Test
	public void testGetData() {
		Assert.assertNull(new AfterWritingEvent(null, false, null).getData());
		Assert.assertEquals(new AfterWritingEvent(null, false, "test").getData(), "test");
	}
}