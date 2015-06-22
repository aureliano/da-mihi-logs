package com.github.aureliano.damihilogs.config.output;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;
import com.github.aureliano.damihilogs.validation.ObjectValidator;

public class FileOutputConfigTest {
	
	ObjectValidator validator = ObjectValidator.instance();

	@Test
	public void testGetDefaults() {
		FileOutputConfig c = new FileOutputConfig();
		Assert.assertNull(c.getFile());
		Assert.assertEquals("UTF-8", c.getEncoding());
		Assert.assertFalse(c.isAppend());
		Assert.assertTrue(c.isUseBuffer());
	}
	
	@Test
	public void testConfiguration() {
		FileOutputConfig c = new FileOutputConfig()
			.withAppend(true)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file");
		
		Assert.assertEquals("/there/is/not/file", c.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", c.getEncoding());
		Assert.assertTrue(c.isAppend());
	}
	
	@Test
	public void testClone() {
		FileOutputConfig c1 = new FileOutputConfig()
			.withAppend(true)
			.withUseBuffer(false)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file")
			.putMetadata("test", "my test");
		
		FileOutputConfig c2 = c1.clone();
		Assert.assertEquals(c1.getFile(), c2.getFile());
		Assert.assertEquals(c1.getEncoding(), c2.getEncoding());
		Assert.assertEquals(c1.isAppend(), c2.isAppend());
		Assert.assertEquals(c1.isUseBuffer(), c2.isUseBuffer());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
	}
	
	@Test
	public void testOutputType() {
		Assert.assertEquals(OutputConfigTypes.FILE_OUTPUT.name(), new FileOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		FileOutputConfig c = this.createValidConfiguration();
		Assert.assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateFile();
	}
	
	private void _testValidateFile() {
		FileOutputConfig c = new FileOutputConfig();
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}

	private FileOutputConfig createValidConfiguration() {
		return new FileOutputConfig().withFile("/path/to/file");
	}
}