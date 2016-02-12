package com.github.aureliano.evtbridge.output.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;

public class FileOutputConfigTest {
	
	ObjectValidator validator = ObjectValidator.instance();

	@Test
	public void testGetDefaults() {
		FileOutputConfig c = new FileOutputConfig();
		assertNull(c.getFile());
		assertEquals("UTF-8", c.getEncoding());
		assertFalse(c.isAppend());
		assertTrue(c.isUseBuffer());
	}
	
	@Test
	public void testConfiguration() {
		FileOutputConfig c = new FileOutputConfig()
			.withAppend(true)
			.withEncoding("ISO-8859-1")
			.withFile("/there/is/not/file");
		
		assertEquals("/there/is/not/file", c.getFile().getPath());
		assertEquals("ISO-8859-1", c.getEncoding());
		assertTrue(c.isAppend());
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
		assertEquals(c1.getFile(), c2.getFile());
		assertEquals(c1.getEncoding(), c2.getEncoding());
		assertEquals(c1.isAppend(), c2.isAppend());
		assertEquals(c1.isUseBuffer(), c2.isUseBuffer());
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
	}
	
	@Test
	public void testOutputType() {
		assertEquals(OutputConfigTypes.FILE_OUTPUT.name(), new FileOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		FileOutputConfig c = this.createValidConfiguration();
		assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateFile();
	}
	
	private void _testValidateFile() {
		FileOutputConfig c = new FileOutputConfig();
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}

	private FileOutputConfig createValidConfiguration() {
		return new FileOutputConfig().withFile("/path/to/file");
	}
}