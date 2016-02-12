package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.model.AnnotationModel;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;

public class NotNullValidatorTest {

	private NotNullValidator validator;
	
	public NotNullValidatorTest() {
		this.validator = new NotNullValidator();
	}
	
	@Test
	public void testValidateWithError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId(null);
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(NotNull.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected to find a not null value for field configurationId.", constraint.getMessage());
		Assert.assertEquals(NotNull.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("custom-id");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(NotNull.class);

		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}