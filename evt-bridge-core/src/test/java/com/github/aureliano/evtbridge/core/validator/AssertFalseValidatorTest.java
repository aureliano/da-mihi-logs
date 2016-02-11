package com.github.aureliano.evtbridge.core.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.AssertFalse;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.model.CustomInputConfig;

public class AssertFalseValidatorTest {

	private AssertFalseValidator validator;
	
	public AssertFalseValidatorTest() {
		this.validator = new AssertFalseValidator();
	}
	
	@Test
	public void testValidateWithNullError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withNotOk(null);
		Method method = configuration.getClass().getMethod("isNotOk", new Class[] {});
		Annotation annotation = method.getAnnotation(AssertFalse.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		assertNotNull(constraint);
		
		assertEquals("Expected field notOk to be false but got null.", constraint.getMessage());
		assertEquals(AssertFalse.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithFalseError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withNotOk(true);
		Method method = configuration.getClass().getMethod("isNotOk", new Class[] {});
		Annotation annotation = method.getAnnotation(AssertFalse.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field notOk to be false but got true.", constraint.getMessage());
		Assert.assertEquals(AssertFalse.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withNotOk(false);
		Method method = configuration.getClass().getMethod("isNotOk", new Class[] {});
		Annotation annotation = method.getAnnotation(AssertFalse.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}