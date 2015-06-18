package com.github.aureliano.damihilogs.validation;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.config.IConfiguration;

public class ConfigurationValidationTest {

	@Test
	public void testApplyValidation() {
		IConfiguration configuration = new CustomInputConfig().withConfigurationId(null);
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation(configuration);
		
		Assert.assertEquals(2, violations.size());
	}
}