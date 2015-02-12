package com.github.aureliano.defero.config.output;

import org.junit.Assert;
import org.junit.Test;

public class FileOutputConfigTest {

	@Test
	public void testGetDefaults() {
		FileOutputConfig c = new FileOutputConfig();
		Assert.assertNull(c.getFile());
		Assert.assertEquals("UTF-8", c.getEncoding());
		Assert.assertFalse(c.isAppend());
	}
	
	@Test
	public void testConfiguration() {
		FileOutputConfig c = new FileOutputConfig()
			.withAppend(true)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file");
		
		Assert.assertEquals("/there/is/not/file", c.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertTrue(c.isAppend());
	}
}