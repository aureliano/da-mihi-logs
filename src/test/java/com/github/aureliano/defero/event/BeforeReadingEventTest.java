package com.github.aureliano.defero.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.config.input.InputFileConfig;

public class BeforeReadingEventTest {

	@Test
	public void testGetLineCounter() {
		Assert.assertEquals(25, new BeforeReadingEvent(null, 25, 0).getLineCounter());
	}

	@Test
	public void testGetMaxParseAttempts() {
		Assert.assertEquals(17, new BeforeReadingEvent(null, 25, 17).getMaxParseAttempts());
	}

	@Test
	public void testGetInputConfiguration() {
		Assert.assertNull(new BeforeReadingEvent(null, 0, 0).getInputConfiguration());
		
		BeforeReadingEvent event = new BeforeReadingEvent(new InputFileConfig().withEncoding("ISO-8859-1").withStartPosition(31).withFile("/there/is/not/file"), 0, 0);
		InputFileConfig cfg = (InputFileConfig) event.getInputConfiguration();
		
		Assert.assertEquals("ISO-8859-1", cfg.getEncoding());
		Assert.assertEquals(31, cfg.getStartPosition());
		Assert.assertEquals(cfg.getFile().getPath(), "/there/is/not/file");
	}
}