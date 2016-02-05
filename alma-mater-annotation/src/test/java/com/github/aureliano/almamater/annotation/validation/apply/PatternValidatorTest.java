package com.github.aureliano.almamater.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.almamater.annotation.model.CustomInputConfig;
import com.github.aureliano.almamater.annotation.validation.Pattern;
import com.github.aureliano.almamater.core.config.IConfiguration;

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
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field configurationId to match [\\d\\w]{3,5} regular expression.", constraint.getMessage());
		Assert.assertEquals(Pattern.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		CustomInputConfig configuration = new CustomInputConfig().withConfigurationId("_ok");
		Method method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		Annotation annotation = method.getAnnotation(Pattern.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		configuration = new CustomInputConfig().withConfigurationId("yeah_");
		method = configuration.getClass().getMethod("getConfigurationId", new Class[] {});
		annotation = method.getAnnotation(Pattern.class);
		
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}