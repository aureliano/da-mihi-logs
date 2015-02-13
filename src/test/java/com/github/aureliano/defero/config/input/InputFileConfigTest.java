package com.github.aureliano.defero.config.input;

import org.junit.Assert;
import org.junit.Test;

public class InputFileConfigTest {

	@Test
	public void testGetDefaults() {
		InputFileConfig c = new InputFileConfig();
		Assert.assertNull(c.getFile());
		Assert.assertEquals("UTF-8", c.getEncoding());
		Assert.assertEquals(0, c.getStartPosition());
		Assert.assertFalse(c.isTailFile());
		Assert.assertEquals(1000, c.getTailDelay());
		Assert.assertEquals(0, c.getTailInterval());
	}
	
	@Test
	public void testConfiguration() {
		InputFileConfig c = new InputFileConfig()
			.withStartPosition(10)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailFile(true)
			.withTailDelay(2000)
			.withTailInterval(10000);
		
		Assert.assertEquals("/there/is/not/file", c.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertEquals(10, c.getStartPosition());
		Assert.assertTrue(c.isTailFile());
		Assert.assertEquals(2000, c.getTailDelay());
		Assert.assertEquals(10000, c.getTailInterval());
	}
}