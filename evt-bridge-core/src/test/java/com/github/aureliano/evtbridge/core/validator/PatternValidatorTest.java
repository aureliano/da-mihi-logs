package com.github.aureliano.evtbridge.core.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.Pattern;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.model.CustomInputConfig;

public class PatternValidatorTest {

	private PatternValidator validator;
	
	public PatternValidatorTest() {
		this.validator = new PatternValidator();
	}
	
	@Test
	public void testValidateWithError() throws SecurityException, NoSuchMethodException {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId("no");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		assertNotNull(constraint);
		
		assertEquals("Expected field configurationId to match [\\d\\w]{3,5} regular expression.", constraint.getMessage());
		assertEquals(Pattern.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		CustomInputConfig configuration = new CustomInputConfig().withConfigurationId("_ok");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		assertTrue(res.isEmpty());
		
		configuration = new CustomInputConfig().withConfigurationId("yeah_");
		method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		annotation = method.getAnnotation(Pattern.class);
		
		res = this.validator.validate(configuration, method, annotation);
		assertTrue(res.isEmpty());
	}
}