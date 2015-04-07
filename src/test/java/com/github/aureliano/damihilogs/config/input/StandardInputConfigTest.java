package com.github.aureliano.damihilogs.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public class StandardInputConfigTest {

	@Test
	public void testConfiguration() {
		StandardInputConfig c = (StandardInputConfig) new StandardInputConfig()
			.withEncoding("ISO-8859-1")
			.withConfigurationId("standard.input.config");
		
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertEquals("standard.input.config", c.getConfigurationId());
	}
	
	@Test
	public void testClone() {
		StandardInputConfig c1 = (StandardInputConfig) new StandardInputConfig()
			.withEncoding("ISO-8859-1")
			.withConfigurationId("standard.input.config")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig) { }
			});
		
		StandardInputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getEncoding(), c2.getEncoding());
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
}