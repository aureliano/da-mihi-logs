package com.github.aureliano.evtbridge.input.url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.ConnectionSchema;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;

public class UrlInputConfigTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testGetDefaults() {
		UrlInputConfig c = new UrlInputConfig();
		assertEquals(ConnectionSchema.HTTP, c.getConnectionSchema());
		assertEquals(-1, c.getPort());
		assertNotNull(c.getParameters());
		assertTrue(c.getParameters().isEmpty());
		assertEquals(new Long(UrlInputConfig.DEFAULT_READ_TIMEOUT), c.getReadTimeout());
		assertEquals(new Integer(0), c.getByteOffSet());
		assertFalse(c.isAppendIfOutputFileExist());
		assertNull(c.getFileStartPosition());
		assertFalse(c.isNoCheckCertificate());
	}
	
	@Test
	public void testConfiguration() {
		UrlInputConfig c = new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTPS)
			.withHost("localhost")
			.withPort(8080)
			.withPath("logs")
			.addParameter("test", "Is it a test?")
			.withReadTimeout(new Long(5 * 1000))
			.withByteOffSet(199845)
			.withOutputFile("output_file.log")
			.withAppendIfOutputFileExist(true)
			.withFileStartPosition(45)
			.withUser("user_name")
			.withPassword("my-password")
			.withNoCheckCertificate(true);
		
		
		assertEquals(ConnectionSchema.HTTPS, c.getConnectionSchema());
		assertEquals("localhost", c.getHost());
		assertEquals(8080, c.getPort());
		assertEquals("logs", c.getPath());
		assertTrue(c.getParameters().size() == 1);
		assertEquals("Is it a test?", c.getParameters().get("test"));
		assertEquals(new Long(5000), c.getReadTimeout());
		assertEquals(new Integer(199845), c.getByteOffSet());
		assertEquals("output_file.log", c.getOutputFile().getPath());
		assertTrue(c.isAppendIfOutputFileExist());
		assertEquals(new Integer(45), c.getFileStartPosition());
		assertEquals("user_name", c.getUser());
		assertEquals("my-password", c.getPassword());
		assertTrue(c.isNoCheckCertificate());
	}
	
	@Test
	public void testClone() {
		UrlInputConfig c1 = (UrlInputConfig) new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTPS)
			.withHost("localhost")
			.withPort(8080)
			.withPath("logs")
			.addParameter("test", "Is it a test?")
			.withReadTimeout(new Long(5 * 1000))
			.withByteOffSet(199845)
			.withOutputFile("output_file.log")
			.withAppendIfOutputFileExist(true)
			.withFileStartPosition(45)
			.withUser("user_name")
			.withPassword("my-password")
			.withNoCheckCertificate(true)
			.withConfigurationId("url.input.config")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		UrlInputConfig c2 = c1.clone();
		
		assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		assertEquals(c1.getHost(), c2.getHost());
		assertEquals(c1.getPassword(), c2.getPassword());
		assertEquals(c1.getPath(), c2.getPath());
		assertEquals(c1.getReadTimeout(), c2.getReadTimeout());
		assertEquals(c1.getUser(), c2.getUser());
		assertEquals(c1.getByteOffSet(), c2.getByteOffSet());
		assertEquals(c1.getConnectionSchema(), c2.getConnectionSchema());
		assertEquals(c1.isAppendIfOutputFileExist(), c2.isAppendIfOutputFileExist());
		assertEquals(c1.getFileStartPosition(), c2.getFileStartPosition());
		assertEquals(c1.getOutputFile(), c2.getOutputFile());
		assertEquals(c1.getParameters().size(), c2.getParameters().size());
		assertEquals(c1.getPort(), c2.getPort());
		assertEquals(c1.isNoCheckCertificate(), c2.isNoCheckCertificate());
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		assertEquals(InputConfigTypes.URL.name(), new UrlInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		UrlInputConfig c = this.createValidConfiguration();
		assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateConnectionSchema();
		this._testValidateOutputFile();
		this._testValidateHost();
	
	}

	public void _testValidateConnectionSchema() {
		UrlInputConfig c = this.createValidConfiguration().withConnectionSchema(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private void _testValidateOutputFile() {
		UrlInputConfig c = new UrlInputConfig()
			.withHost("localhost").withConnectionSchema(ConnectionSchema.HTTP);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private void _testValidateHost() {
		UrlInputConfig c = this.createValidConfiguration().withHost(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private UrlInputConfig createValidConfiguration() {
		return new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTP)
			.withHost("localhost")
			.withOutputFile("/path/to/file");
	}
}