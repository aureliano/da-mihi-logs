package com.github.aureliano.damihilogs.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public class ExternalCommandInputTest {

	@Test
	public void testGetDefaults() {
		ExternalCommandInput c = new ExternalCommandInput();
		Assert.assertNull(c.getConfigurationId());
		Assert.assertNull(c.getCommand());
		Assert.assertNotNull(c.getParameters());
		Assert.assertTrue(c.getParameters().isEmpty());
	}
	
	@Test
	public void testConfiguration() {
		ExternalCommandInput c = new ExternalCommandInput()
			.withConfigurationId("external-command-config")
			.withCommand("ls")
			.addParameter("-la");
		
		Assert.assertEquals("external-command-config", c.getConfigurationId());
		Assert.assertEquals("ls", c.getCommand());
		Assert.assertTrue(c.getParameters().size() == 1);
		Assert.assertEquals("-la", c.getParameters().get(0));
	}
	
	@Test
	public void testClone() {
		ExternalCommandInput c1 = new ExternalCommandInput()
			.withConfigurationId("external-command-config")
			.withCommand("ls")
			.addParameter("-la")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig) { }
			});
		
		ExternalCommandInput c2 = c1.clone();
		Assert.assertEquals(c1.getCommand(), c2.getCommand());
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getParameters().size(), c2.getParameters().size());
		Assert.assertEquals(c1.getParameters().get(0), c2.getParameters().get(0));
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
}