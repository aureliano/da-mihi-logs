package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.model.AnnotationModel;
import com.github.aureliano.evtbridge.annotation.validation.Pattern;

public class PatternValidatorTest {

	private PatternValidator validator;
	
	public PatternValidatorTest() {
		this.validator = new PatternValidator();
	}
	
	@Test
	public void testValidateWithError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("no");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to match [\\d\\w]{3,5} regular expression.", constraint.getMessage());
		Assert.assertEquals(Pattern.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("_ok");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		configuration = new AnnotationModel().withConfigurationId("yeah_");
		method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		annotation = method.getAnnotation(Pattern.class);
		
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}