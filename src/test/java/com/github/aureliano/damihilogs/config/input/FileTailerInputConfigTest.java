package com.github.aureliano.damihilogs.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public class FileTailerInputConfigTest {

	@Test
	public void testGetDefaults() {
		FileTailerInputConfig c = new FileTailerInputConfig();
		Assert.assertNull(c.getFile());
		Assert.assertEquals("UTF-8", c.getEncoding());
		Assert.assertEquals(new Long(1000), c.getTailDelay());
		Assert.assertNull(c.getTailInterval());
	}
	
	@Test
	public void testConfiguration() {
		FileTailerInputConfig c = new FileTailerInputConfig()
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailDelay(new Long(2000))
			.withTailInterval(new Long(10000));
		
		Assert.assertEquals("/there/is/not/file", c.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertEquals(new Long(2000), c.getTailDelay());
		Assert.assertEquals(new Long(10000), c.getTailInterval());
	}
	
	@Test
	public void testClone() {
		FileTailerInputConfig c1 = (FileTailerInputConfig) new FileTailerInputConfig()
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailDelay(new Long(2000))
			.withTailInterval(new Long(10000))
			.withConfigurationId("input.file.config")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		FileTailerInputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getEncoding(), c2.getEncoding());
		Assert.assertEquals(c1.getTailDelay(), c2.getTailDelay());
		Assert.assertEquals(c1.getTailInterval(), c2.getTailInterval());
		Assert.assertEquals(c1.getFile(), c2.getFile());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
}