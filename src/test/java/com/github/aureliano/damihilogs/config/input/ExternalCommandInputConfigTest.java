package com.github.aureliano.damihilogs.config.input;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.validation.ConfigurationValidation;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;

public class ExternalCommandInputConfigTest {

	@Test
	public void testGetDefaults() {
		ExternalCommandInputConfig c = new ExternalCommandInputConfig();
		Assert.assertNull(c.getConfigurationId());
		Assert.assertNull(c.getCommand());
		Assert.assertNotNull(c.getParameters());
		Assert.assertTrue(c.getParameters().isEmpty());
	}
	
	@Test
	public void testConfiguration() {
		ExternalCommandInputConfig c = new ExternalCommandInputConfig()
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
		ExternalCommandInputConfig c1 = new ExternalCommandInputConfig()
			.withConfigurationId("external-command-config")
			.withCommand("ls")
			.addParameter("-la")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		ExternalCommandInputConfig c2 = c1.clone();
		Assert.assertEquals(c1.getCommand(), c2.getCommand());
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getParameters().size(), c2.getParameters().size());
		Assert.assertEquals(c1.getParameters().get(0), c2.getParameters().get(0));
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		Assert.assertEquals(InputConfigTypes.EXTERNAL_COMMAND.name(), new ExternalCommandInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		ExternalCommandInputConfig c = this.createValidConfiguration();
		Assert.assertTrue(ConfigurationValidation.applyValidation(c).isEmpty());
		
		c.withCommand(null);
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withCommand("");
		violations = ConfigurationValidation.applyValidation(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private ExternalCommandInputConfig createValidConfiguration() {
		return new ExternalCommandInputConfig().withCommand("ls");
	}
}