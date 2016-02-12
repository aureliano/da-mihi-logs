package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.model.AnnotationModel;
import com.github.aureliano.evtbridge.annotation.validation.Size;

public class SizeValidatorTest {

	private SizeValidator validator;
	
	public SizeValidatorTest() {
		this.validator = new SizeValidator();
	}
	
	@Test
	public void testValidateWithStringMinError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("no");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to have size between 3 and 5 but got 2.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithCollectionMinError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel();
		Method method = configuration.getClass().getMethod("getData", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field data to have size between 1 and 1 but got 0.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithStringMaxError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId("Is it ok?");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to have size between 3 and 5 but got 9.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithCollectionMaxError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel()
			.withData(Arrays.asList(new Object(), new Object()));
		Method method = configuration.getClass().getMethod("getData", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field data to have size between 1 and 1 but got 2.", constraint.getMessage());
		Assert.assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel()
			.withConfigurationId("yes!")
			.withData(Arrays.asList(new Object()));
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		method = configuration.getClass().getMethod("getData", new Class[] {});
		annotation = method.getAnnotation(Size.class);
		
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}