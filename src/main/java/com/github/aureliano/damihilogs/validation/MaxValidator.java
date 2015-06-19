package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

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
		int objectSize;
		
		if (Collection.class.isAssignableFrom(returnedValue.getClass())) {
			objectSize = ((Collection<?>) returnedValue).size();
		} else {
			objectSize = returnedValue.toString().length();
		}
		
		if (maxSize < objectSize) {
			return new ConstraintViolation()
				.withValidator(Max.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", String.valueOf(maxSize))
					.replaceFirst("#\\{1\\}", property)
					.replaceFirst("#\\{2\\}", String.valueOf(objectSize)));
		}
		
		return null;
	}
}