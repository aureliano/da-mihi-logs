package com.github.aureliano.damihilogs.event;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.FileInputConfig;

public class StepParseEventTest {
	
	@Test
	public void testGetInputConfiguration() {
		Assert.assertNull(new StepParseEvent(null, 0, null, null).getInputConfiguration());
		
		StepParseEvent event = new StepParseEvent(new FileInputConfig().withEncoding("ISO-8859-1").withFile("/there/is/not/file"), 0, null, null);
		FileInputConfig cfg = (FileInputConfig) event.getInputConfiguration();
		
		Assert.assertEquals("ISO-8859-1", cfg.getEncoding());
		Assert.assertEquals(cfg.getFile().getPath(), "/there/is/not/file");
	}
	
	@Test
	public void testGetParseAttempts() {
		Assert.assertEquals(0, new StepParseEvent(null, 0, null, null).getParseAttempts());
		Assert.assertEquals(30, new StepParseEvent(null, 30, null, null).getParseAttempts());
	}

	@Test
	public void testGetLine() {
		Assert.assertNull(null, new StepParseEvent(null, 0, null, null).getLine());
		Assert.assertEquals("line", new StepParseEvent(null, 0, "line", null).getLine());
	}

	public void testGetCurrentData() {
		Assert.assertNull(null, new StepParseEvent(null, 0, null, null).getCurrentData());
		Assert.assertEquals("current data", new StepParseEvent(null, 0, null, "current data").getCurrentData());
	}
}