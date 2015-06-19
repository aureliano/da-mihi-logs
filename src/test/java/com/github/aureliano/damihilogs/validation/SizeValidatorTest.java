package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.Size;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.DefaultExceptionHandler;

public class SizeValidatorTest {

	private SizeValidator validator;
	
	public SizeValidatorTest() {
		this.validator = new SizeValidator();
	}
	
	@Test
	public void testValidateWithStringMinError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("no");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to have size between 3 and 5 but got 2.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithCollectionMinError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig();
		Method method = configuration.getClass().getMethod("getExceptionHandlers", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field exceptionHandlers to have size between 1 and 1 but got 0.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithStringMaxError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("Is it ok?");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to have size between 3 and 5 but got 9.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithCollectionMaxError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig()
			.addExceptionHandler(new DefaultExceptionHandler())
			.addExceptionHandler(new DefaultExceptionHandler());
		Method method = configuration.getClass().getMethod("getExceptionHandlers", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field exceptionHandlers to have size between 1 and 1 but got 2.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig()
			.withConfigurationId("yes!")
			.addExceptionHandler(new DefaultExceptionHandler());
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
		
		method = configuration.getClass().getMethod("getExceptionHandlers", new Class[] {});
		annotation = method.getAnnotation(Size.class);
		 
		constraint = this.validator.validate(configuration, method, annotation);
		Assert.assertNull(constraint);
	}
}