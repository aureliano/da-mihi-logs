package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.Max;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.DefaultExceptionHandler;

public class MaxValidatorTest {

	private MaxValidator validator;
	
	public MaxValidatorTest() {
		this.validator = new MaxValidator();
	}
	
	@Test
	public void testValidateStringWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("123456");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a maximum value of 5 for field configurationId but got 6.", constraint.getMessage());
		Assert.assertEquals(Max.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateCollectionWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig()
			.addExceptionHandler(new DefaultExceptionHandler())
			.addExceptionHandler(new DefaultExceptionHandler());
		Method method = configuration.getClass().getMethod("getExceptionHandlers", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a maximum value of 1 for field exceptionHandlers but got 2.", constraint.getMessage());
		Assert.assertEquals(Max.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("12345")
				.addExceptionHandler(new DefaultExceptionHandler());
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		method = configuration.getClass().getMethod("getExceptionHandlers", new Class[] {});
		annotation = method.getAnnotation(Max.class);
		 
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}