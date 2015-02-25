package com.github.aureliano.defero.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.config.output.FileOutputConfig;

public class AfterWritingEventTest {

	@Test
	public void testGetOutputConfiguration() {
		Assert.assertNull(new AfterWritingEvent(null, null).getOutputConfiguration());
		
		AfterWritingEvent event = new AfterWritingEvent(new FileOutputConfig().withEncoding("ISO-8859-1").withAppend(true).withFile("/there/is/not/file"), null);
		FileOutputConfig cfg = (FileOutputConfig) event.getOutputConfiguration();
		
		Assert.assertEquals("ISO-8859-1", cfg.getEncoding());
		Assert.assertTrue(cfg.isAppend());
		Assert.assertEquals(cfg.getFile().getPath(), "/there/is/not/file");
	}
	
	@Test
	public void testGetData() {
		Assert.assertNull(new AfterWritingEvent(null, null).getData());
		Assert.assertEquals(new AfterWritingEvent(null, "test").getData(), "test");
	}
}