package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.github.aureliano.damihilogs.helper.ReflectionHelper;

public class ValidValidator implements IValidator {

	public ValidValidator() {
		super();
	}

	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		
		if (returnedValue == null) {
			return null;
		}
		
		return ObjectValidator.instance().validate(returnedValue);
	}
}