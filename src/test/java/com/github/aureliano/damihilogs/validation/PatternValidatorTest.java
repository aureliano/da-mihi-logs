package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.Pattern;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.DefaultExceptionHandler;

public class PatternValidatorTest {

	private PatternValidator validator;
	
	public PatternValidatorTest() {
		this.validator = new PatternValidator();
	}
	
	@Test
	public void testValidateWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("no");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to match [\\d\\w]{3,5} regular expression.", constraint.getMessage());
		Assert.assertEquals(Pattern.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		CustomInputConfig configuration = new CustomInputConfig().withConfigurationId("_ok");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
		
		configuration = new CustomInputConfig().addExceptionHandler(new DefaultExceptionHandler());
		method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		annotation = method.getAnnotation(Pattern.class);
		
		constraint = this.validator.validate(configuration.withConfigurationId("yeah_"), method, annotation);
		Assert.assertNull(constraint);
	}
}