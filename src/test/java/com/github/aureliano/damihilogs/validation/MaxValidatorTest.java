package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.Max;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class MaxValidatorTest {

	private MaxValidator validator;
	
	public MaxValidatorTest() {
		this.validator = new MaxValidator();
	}
	
	@Test
	public void testValidateWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("123456");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a maximum value of 5 for field configurationId but got 6.", constraint.getMessage());
		Assert.assertEquals(Max.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("12345");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
	}
}