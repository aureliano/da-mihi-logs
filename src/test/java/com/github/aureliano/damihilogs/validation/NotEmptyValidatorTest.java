package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class NotEmptyValidatorTest {

	private NotEmptyValidator validator;
	
	public NotEmptyValidatorTest() {
		this.validator = new NotEmptyValidator();
	}
	
	@Test
	public void testValidateNullWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId(null);
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(NotEmpty.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected to find a not empty text for field configurationId.", constraint.getMessage());
		Assert.assertEquals(NotEmpty.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateEmptyWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(NotEmpty.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected to find a not empty text for field configurationId.", constraint.getMessage());
		Assert.assertEquals(NotEmpty.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("custom-id");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(NotEmpty.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
	}
}