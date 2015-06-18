package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.aureliano.damihilogs.annotation.validation.Max;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;

public class MaxValidator implements IValidator {

	public MaxValidator() {
		super();
	}

	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		if (returnedValue == null) {
			returnedValue = "";
		}
		
		String message = ((Max) annotation).message();
		int maxSize = ((Max) annotation).value();
		
		if (maxSize < returnedValue.toString().length()) {
			int size = returnedValue.toString().length();
			return new ConstraintViolation()
				.withValidator(Max.class)
				.withMessage(message
					.replaceFirst("\\?", String.valueOf(maxSize))
					.replaceFirst("\\?", property)
					.replaceFirst("\\?", String.valueOf(size)));
		}
		
		return null;
	}
}