package com.github.aureliano.evtbridge.input.external_command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;

public class ExternalCommandInputConfigTest {

	@Test
	public void testGetDefaults() {
		ExternalCommandInputConfig c = new ExternalCommandInputConfig();
		assertNull(c.getConfigurationId());
		assertNull(c.getCommand());
		assertNotNull(c.getParameters());
		assertTrue(c.getParameters().isEmpty());
	}
	
	@Test
	public void testConfiguration() {
		ExternalCommandInputConfig c = new ExternalCommandInputConfig()
			.withConfigurationId("external-command-config")
			.withCommand("ls")
			.addParameter("-la");
		
		assertEquals("external-command-config", c.getConfigurationId());
		assertEquals("ls", c.getCommand());
		assertTrue(c.getParameters().size() == 1);
		assertEquals("-la", c.getParameters().get(0));
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
		assertEquals(c1.getCommand(), c2.getCommand());
		assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		assertEquals(c1.getParameters().size(), c2.getParameters().size());
		assertEquals(c1.getParameters().get(0), c2.getParameters().get(0));
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		assertEquals(InputConfigTypes.EXTERNAL_COMMAND.name(), new ExternalCommandInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		ObjectValidator validator = ObjectValidator.instance();
		ExternalCommandInputConfig c = this.createValidConfiguration();
		assertTrue(validator.validate(c).isEmpty());
		
		c.withCommand(null);
		Set<ConstraintViolation> violations = validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withCommand("");
		violations = validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private ExternalCommandInputConfig createValidConfiguration() {
		return new ExternalCommandInputConfig().withCommand("ls");
	}
}