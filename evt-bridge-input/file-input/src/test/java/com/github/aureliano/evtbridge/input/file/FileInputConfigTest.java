package com.github.aureliano.evtbridge.input.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.Min;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;

public class FileInputConfigTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testGetDefaults() {
		FileInputConfig c = new FileInputConfig();
		assertNull(c.getFile());
		assertEquals("UTF-8", c.getEncoding());
		assertEquals(new Integer(0), c.getStartPosition());
	}
	
	@Test
	public void testConfiguration() {
		FileInputConfig c = new FileInputConfig()
			.withStartPosition(10)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file");
		
		assertEquals("/there/is/not/file", c.getFile().getPath());
		assertEquals("ISO-8859-1", c.getEncoding());
		assertEquals(new Integer(10), c.getStartPosition());
	}
	
	@Test
	public void testClone() {
		FileInputConfig c1 = (FileInputConfig) new FileInputConfig()
			.withStartPosition(10)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withConfigurationId("input.file.config")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		FileInputConfig c2 = c1.clone();
		
		assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		assertEquals(c1.getEncoding(), c2.getEncoding());
		assertEquals(c1.getFile(), c2.getFile());
		assertEquals(c1.getStartPosition(), c2.getStartPosition());
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		assertEquals(InputConfigTypes.FILE_INPUT.name(), new FileInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		FileInputConfig c = this.createValidConfiguration();
		assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateFile();
		this._testValidateStartPosition();
	}
	
	private void _testValidateFile() {
		FileInputConfig c = new FileInputConfig();
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private void _testValidateStartPosition() {
		FileInputConfig c = this.createValidConfiguration().withStartPosition(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
		
		c.withStartPosition(-1);
		violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(Min.class, violations.iterator().next().getValidator());
	}
	
	private FileInputConfig createValidConfiguration() {
		return new FileInputConfig().withFile("/path/to/file").withStartPosition(5);
	}
}