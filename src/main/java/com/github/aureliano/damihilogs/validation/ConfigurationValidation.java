package com.github.aureliano.damihilogs.validation;

import java.util.Set;

import com.github.aureliano.damihilogs.config.IConfiguration;

public final class ConfigurationValidation {

	private ConfigurationValidation() {
		super();
	}
	
	public static Set<ConstraintViolation> applyValidation(IConfiguration configuration) {
		return ObjectValidator.instance().validate(configuration);
	}
}