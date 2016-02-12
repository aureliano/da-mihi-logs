package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.model.AnnotationModel;
import com.github.aureliano.evtbridge.annotation.validation.Max;

public class MaxValidatorTest {

	private MaxValidator validator;
	
	public MaxValidatorTest() {
		this.validator = new MaxValidator();
	}
	
	@Test
	public void testValidateStringWithError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("123456");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a maximum value of 5 for field configurationId but got 6.", constraint.getMessage());
		Assert.assertEquals(Max.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateCollectionWithError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel()
			.withData(Arrays.asList(new Object(), new Object()));
		Method method = configuration.getClass().getMethod("getData", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a maximum value of 1 for field data but got 2.", constraint.getMessage());
		Assert.assertEquals(Max.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("12345")
				.withData(Arrays.asList(new Object()));
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Max.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		method = configuration.getClass().getMethod("getData", new Class[] {});
		annotation = method.getAnnotation(Max.class);
		 
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}