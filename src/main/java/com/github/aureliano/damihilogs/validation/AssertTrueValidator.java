package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.aureliano.damihilogs.annotation.validation.AssertTrue;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class AssertTrueValidator implements IValidator {

	public AssertTrueValidator() {
		super();
	}
	
	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		String message = ((AssertTrue) annotation).message();
		
		
		if (!Boolean.TRUE.equals(returnedValue)) {
			return new ConstraintViolation()
				.withValidator(AssertTrue.class)
				.withMessage(message
					.replaceFirst("\\?", property)
					.replaceFirst("\\?", StringHelper.toString(returnedValue)));
		}
		
		return null;
	}
}