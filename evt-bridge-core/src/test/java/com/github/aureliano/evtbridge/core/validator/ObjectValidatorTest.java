package com.github.aureliano.evtbridge.core.validator;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.model.CustomInputConfig;

public class ObjectValidatorTest {

	private ObjectValidator validator;
	
	public ObjectValidatorTest() {
		this.validator = ObjectValidator.instance();;
	}
	
	@Test
	public void testValidate() {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId(null);
		Set<ConstraintViolation> violations = this.validator.validate(configuration);
		
		assertEquals(8, violations.size());
	}
}