package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.model.AnnotationModel;

public class ObjectValidatorTest {

	private ObjectValidator validator;
	
	public ObjectValidatorTest() {
		this.validator = ObjectValidator.instance();;
	}
	
	@Test
	public void testValidate() {
		AnnotationModel configuration = new AnnotationModel().withConfigurationId(null);
		Set<ConstraintViolation> violations = this.validator.validate(configuration);
		
		Assert.assertEquals(8, violations.size());
	}
}