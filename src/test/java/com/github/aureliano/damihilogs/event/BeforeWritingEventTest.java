package com.github.aureliano.defero.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.config.output.FileOutputConfig;

public class BeforeWritingEventTest {

	@Test
	public void testGetOutputConfiguration() {
		Assert.assertNull(new BeforeWritingEvent(null, null).getOutputConfiguration());
		
		BeforeWritingEvent event = new BeforeWritingEvent(new FileOutputConfig().withEncoding("ISO-8859-1").withAppend(true).withFile("/there/is/not/file"), null);
		FileOutputConfig cfg = (FileOutputConfig) event.getOutputConfiguration();
		
		Assert.assertEquals("ISO-8859-1", cfg.getEncoding());
		Assert.assertTrue(cfg.isAppend());
		Assert.assertEquals(cfg.getFile().getPath(), "/there/is/not/file");
	}

	@Test
	public void testGetData() {
		Assert.assertNull(new BeforeWritingEvent(null, null).getData());
		Assert.assertEquals(new BeforeWritingEvent(null, "test").getData(), "test");
	}
}