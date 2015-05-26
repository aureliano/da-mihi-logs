package com.github.aureliano.damihilogs.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public class FileInputConfigTest {

	@Test
	public void testGetDefaults() {
		FileInputConfig c = new FileInputConfig();
		Assert.assertNull(c.getFile());
		Assert.assertEquals("UTF-8", c.getEncoding());
		Assert.assertNull(c.getStartPosition());
		Assert.assertFalse(c.isTailFile());
		Assert.assertEquals(new Long(1000), c.getTailDelay());
		Assert.assertEquals(new Long(0), c.getTailInterval());
	}
	
	@Test
	public void testConfiguration() {
		FileInputConfig c = new FileInputConfig()
			.withStartPosition(10)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailFile(true)
			.withTailDelay(new Long(2000))
			.withTailInterval(new Long(10000));
		
		Assert.assertEquals("/there/is/not/file", c.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertEquals(new Integer(10), c.getStartPosition());
		Assert.assertTrue(c.isTailFile());
		Assert.assertEquals(new Long(2000), c.getTailDelay());
		Assert.assertEquals(new Long(10000), c.getTailInterval());
	}
	
	@Test
	public void testClone() {
		FileInputConfig c1 = (FileInputConfig) new FileInputConfig()
			.withStartPosition(10)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailFile(true)
			.withTailDelay(new Long(2000))
			.withTailInterval(new Long(10000))
			.withConfigurationId("input.file.config")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig) { }
			});
		
		FileInputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getEncoding(), c2.getEncoding());
		Assert.assertEquals(c1.getTailDelay(), c2.getTailDelay());
		Assert.assertEquals(c1.getTailInterval(), c2.getTailInterval());
		Assert.assertEquals(c1.getFile(), c2.getFile());
		Assert.assertEquals(c1.getStartPosition(), c2.getStartPosition());
		Assert.assertEquals(c1.isTailFile(), c2.isTailFile());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
}