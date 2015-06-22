package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.github.aureliano.damihilogs.annotation.validation.Valid;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;

public class ValidValidator implements IValidator {

	public ValidValidator() {
		super();
	}

	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		if (returnedValue == null) {
			return null;
		}
		
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation((IConfiguration) returnedValue);
		if (violations.isEmpty()) {
			return null;
		}
		
		throw new DaMihiLogsException("Not implemented yet");
	}
}