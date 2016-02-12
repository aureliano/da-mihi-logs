package com.github.aureliano.evtbridge.input.file_tailer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;

public class FileTailerInputConfigTest {

	@Test
	public void testGetDefaults() {
		FileTailerInputConfig c = new FileTailerInputConfig();
		assertNull(c.getFile());
		assertEquals("UTF-8", c.getEncoding());
		assertEquals(new Long(1000), c.getTailDelay());
		assertNull(c.getTailInterval());
		assertEquals(TimeUnit.MILLISECONDS, c.getTimeUnit());
	}
	
	@Test
	public void testConfiguration() {
		FileTailerInputConfig c = new FileTailerInputConfig()
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailDelay(new Long(2000))
			.withTailInterval(new Long(10000));
		
		assertEquals("/there/is/not/file", c.getFile().getPath());
		assertEquals("ISO-8859-1", c.getEncoding());
		assertEquals(new Long(2000), c.getTailDelay());
		assertEquals(new Long(10000), c.getTailInterval());
	}
	
	@Test
	public void testClone() {
		FileTailerInputConfig c1 = (FileTailerInputConfig) new FileTailerInputConfig()
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.withTailDelay(new Long(2000))
			.withTailInterval(new Long(10000))
			.withTimeUnit(TimeUnit.SECONDS)
			.withConfigurationId("input.file.config")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		FileTailerInputConfig c2 = c1.clone();
		
		assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		assertEquals(c1.getEncoding(), c2.getEncoding());
		assertEquals(c1.getTailDelay(), c2.getTailDelay());
		assertEquals(c1.getTailInterval(), c2.getTailInterval());
		assertEquals(c1.getFile(), c2.getFile());
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		assertEquals(InputConfigTypes.FILE_TAILER.name(), new FileTailerInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		ObjectValidator validator = ObjectValidator.instance();
		FileTailerInputConfig c = this.createValidConfiguration();
		assertTrue(validator.validate(c).isEmpty());
		
		this._testValidateFile();
	}
	
	private void _testValidateFile() {
		ObjectValidator validator = ObjectValidator.instance();
		FileTailerInputConfig c = new FileTailerInputConfig();
		
		Set<ConstraintViolation> violations = validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private FileTailerInputConfig createValidConfiguration() {
		return new FileTailerInputConfig().withFile("/path/to/file");
	}
}