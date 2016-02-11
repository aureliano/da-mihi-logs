package com.github.aureliano.evtbridge.core.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.Size;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.model.CustomInputConfig;

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
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		assertNotNull(constraint);
		
		assertEquals("Expected field configurationId to have size between 3 and 5 but got 2.", constraint.getMessage());
		assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithCollectionMinError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig();
		Method method = configuration.getClass().getMethod("getData", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		assertNotNull(constraint);
		
		assertEquals("Expected field data to have size between 1 and 1 but got 0.", constraint.getMessage());
		assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithStringMaxError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("Is it ok?");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		assertNotNull(constraint);
		
		assertEquals("Expected field configurationId to have size between 3 and 5 but got 9.", constraint.getMessage());
		assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithCollectionMaxError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig()
			.withData(Arrays.asList(new Object(), new Object()));
		Method method = configuration.getClass().getMethod("getData", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		assertNotNull(constraint);
		
		assertEquals("Expected field data to have size between 1 and 1 but got 2.", constraint.getMessage());
		assertEquals(Size.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig()
			.withConfigurationId("yes!")
			.withData(Arrays.asList(new Object()));
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Size.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		assertTrue(res.isEmpty());
		
		method = configuration.getClass().getMethod("getData", new Class[] {});
		annotation = method.getAnnotation(Size.class);
		
		res = this.validator.validate(configuration, method, annotation);
		assertTrue(res.isEmpty());
	}
}