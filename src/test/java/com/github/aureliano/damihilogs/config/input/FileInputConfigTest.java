package com.github.aureliano.damihilogs.config.input;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.Min;
import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;
import com.github.aureliano.damihilogs.validation.ObjectValidator;

public class FileInputConfigTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testGetDefaults() {
		FileInputConfig c = new FileInputConfig();
		Assert.assertNull(c.getFile());
		Assert.assertEquals("UTF-8", c.getEncoding());
		Assert.assertEquals(new Integer(0), c.getStartPosition());
	}
	
	@Test
	public void testConfiguration() {
		FileInputConfig c = new FileInputConfig()
			.withStartPosition(10)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file");
		
		Assert.assertEquals("/there/is/not/file", c.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertEquals(new Integer(10), c.getStartPosition());
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
		
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getEncoding(), c2.getEncoding());
		Assert.assertEquals(c1.getFile(), c2.getFile());
		Assert.assertEquals(c1.getStartPosition(), c2.getStartPosition());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		Assert.assertEquals(InputConfigTypes.FILE_INPUT.name(), new FileInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		FileInputConfig c = this.createValidConfiguration();
		Assert.assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateFile();
		this._testValidateStartPosition();
	}
	
	private void _testValidateFile() {
		FileInputConfig c = new FileInputConfig();
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private void _testValidateStartPosition() {
		FileInputConfig c = this.createValidConfiguration().withStartPosition(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
		
		c.withStartPosition(-1);
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(Min.class, violations.iterator().next().getValidator());
	}
	
	private FileInputConfig createValidConfiguration() {
		return new FileInputConfig().withFile("/path/to/file").withStartPosition(5);
	}
}