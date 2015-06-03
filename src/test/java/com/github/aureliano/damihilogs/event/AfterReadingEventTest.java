package com.github.aureliano.damihilogs.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.FileInputConfig;

public class AfterReadingEventTest {

	@Test
	public void testGetInputConfiguration() {
		Assert.assertNull(new AfterReadingEvent(null, 0, null).getInputConfiguration());
		
		AfterReadingEvent event = new AfterReadingEvent(new FileInputConfig().withEncoding("ISO-8859-1").withFile("/there/is/not/file"), 0, null);
		FileInputConfig cfg = (FileInputConfig) event.getInputConfiguration();
		
		Assert.assertEquals("ISO-8859-1", cfg.getEncoding());
		Assert.assertEquals(cfg.getFile().getPath(), "/there/is/not/file");
	}
	
	@Test
	public void testGetLineCounter() {
		Assert.assertEquals(25, new AfterReadingEvent(null, 25, null).getLineCounter());
	}
	
	@Test
	public void testGetData() {
		Assert.assertNull(new AfterReadingEvent(null, 25, null).getData());
		Assert.assertEquals("getData", new AfterReadingEvent(null, 25, "getData").getData());
	}
}