package com.github.aureliano.defero.config.input;

import org.junit.Assert;
import org.junit.Test;

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
			.addParameter("-la");
		
		ExternalCommandInput c2 = c1.clone();
		Assert.assertEquals(c1.getCommand(), c2.getCommand());
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getParameters().size(), c2.getParameters().size());
		Assert.assertEquals(c1.getParameters().get(0), c2.getParameters().get(0));
	}
}