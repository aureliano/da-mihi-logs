package com.github.aureliano.almamater.annotation.validation.apply;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.almamater.annotation.model.CustomInputConfig;
import com.github.aureliano.evtbridge.core.config.IConfiguration;

public class ObjectValidatorTest {

	private ObjectValidator validator;
	
	public ObjectValidatorTest() {
		this.validator = ObjectValidator.instance();;
	}
	
	@Test
	public void testValidate() {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId(null);
		Set<ConstraintViolation> violations = this.validator.validate(configuration);
		
		Assert.assertEquals(8, violations.size());
	}
}