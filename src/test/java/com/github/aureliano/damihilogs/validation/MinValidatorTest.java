package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.Min;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class MinValidatorTest {

	private MinValidator validator;
	
	public MinValidatorTest() {
		this.validator = new MinValidator();
	}
	
	@Test
	public void testValidateWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Min.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a minimum value of 3 for field configurationId but got 0.", constraint.getMessage());
		Assert.assertEquals(Min.class, constraint.getValidator());
		
		configuration = new CustomInputConfig().withConfigurationId("ok");
		
		constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a minimum value of 3 for field configurationId but got 2.", constraint.getMessage());
		Assert.assertEquals(Min.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("ok!");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Min.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
	}
}