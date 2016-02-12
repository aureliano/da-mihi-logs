package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.model.AnnotationModel;
import com.github.aureliano.evtbridge.annotation.validation.Min;

public class MinValidatorTest {

	private MinValidator validator;
	
	public MinValidatorTest() {
		this.validator = new MinValidator();
	}
	
	@Test
	public void testValidateStringWithError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Min.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a minimum value of 3 for field configurationId but got 0.", constraint.getMessage());
		Assert.assertEquals(Min.class, constraint.getValidator());
		
		configuration = new AnnotationModel().withConfigurationId("ok");
		
		constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a minimum value of 3 for field configurationId but got 2.", constraint.getMessage());
		Assert.assertEquals(Min.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateCollectionWithError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("ok!");
		Method method = configuration.getClass().getMethod("getData", new Class[] {});
		Annotation annotation = method.getAnnotation(Min.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected a minimum value of 1 for field data but got 0.", constraint.getMessage());
		Assert.assertEquals(Min.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("ok!");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Min.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		configuration = new AnnotationModel()
			.withData(Arrays.asList(new Object()));
		method = configuration.getClass().getMethod("getData", new Class[] {});
		annotation = method.getAnnotation(Min.class);
		
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}