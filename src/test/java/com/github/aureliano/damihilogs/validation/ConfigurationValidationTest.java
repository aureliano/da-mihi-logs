package com.github.aureliano.damihilogs.validation;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class ConfigurationValidationTest {

	@Test
	public void testApplyValidation() {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId(null);
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation(configuration);
		
		Assert.assertEquals(1, violations.size());
		ConstraintViolation constraint = violations.iterator().next();
		
		Assert.assertEquals("Expected to find a not null value for field configurationId.", constraint.getMessage());
		Assert.assertEquals(NotNull.class, constraint.getValidator());
	}
}