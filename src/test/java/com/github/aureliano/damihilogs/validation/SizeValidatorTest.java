package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.Size;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class SizeValidatorTest {

	private SizeValidator validator;
	
	public SizeValidatorTest() {
		this.validator = new SizeValidator();
	}
	
	@Test
	public void testValidateWithMinError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("no");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to have size between 3 and 5 but got 2.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithMaxError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("Is it ok?");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to have size between 3 and 5 but got 9.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("yes!");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
	}
}