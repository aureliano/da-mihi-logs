package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.AssertTrue;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class AssertTrueValidatorTest {

	private AssertTrueValidator validator;
	
	public AssertTrueValidatorTest() {
		this.validator = new AssertTrueValidator();
	}
	
	@Test
	public void testValidateWithNullError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withOk(null);
		Method method = configuration.getClass().getMethod("isOk", new Class[] {});
		Annotation annotation = method.getAnnotation(AssertTrue.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field ok to be true but got null.", constraint.getMessage());
		Assert.assertEquals(AssertTrue.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithFalseError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withOk(false);
		Method method = configuration.getClass().getMethod("isOk", new Class[] {});
		Annotation annotation = method.getAnnotation(AssertTrue.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field ok to be true but got false.", constraint.getMessage());
		Assert.assertEquals(AssertTrue.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withOk(true);
		Method method = configuration.getClass().getMethod("isOk", new Class[] {});
		Annotation annotation = method.getAnnotation(AssertTrue.class);
		
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}