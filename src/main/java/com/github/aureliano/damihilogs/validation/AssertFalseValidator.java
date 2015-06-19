package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.aureliano.damihilogs.annotation.validation.AssertFalse;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class AssertFalseValidator implements IValidator {

	public AssertFalseValidator() {
		super();
	}
	
	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		String message = ((AssertFalse) annotation).message();
		
		if (!Boolean.FALSE.equals(returnedValue)) {
			return new ConstraintViolation()
				.withValidator(AssertFalse.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", StringHelper.toString(returnedValue)));
		}
		
		return null;
	}
}