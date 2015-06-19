package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import com.github.aureliano.damihilogs.annotation.validation.Min;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class MinValidator implements IValidator {

	public MinValidator() {
		super();
	}

	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		if (returnedValue == null) {
			returnedValue = "";
		}
		
		String message = ((Min) annotation).message();
		int minSize = ((Min) annotation).value();
		int objectSize;
		
		if (Collection.class.isAssignableFrom(returnedValue.getClass())) {
			objectSize = ((Collection<?>) returnedValue).size();
		} else if (!(returnedValue instanceof String) && StringHelper.isNumeric(returnedValue.toString())) {
			Double d = Double.parseDouble(returnedValue.toString());
			objectSize = d.intValue();
		} else {
			objectSize = returnedValue.toString().length();
		}
		
		if (minSize > objectSize) {
			return new ConstraintViolation()
				.withValidator(Min.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", String.valueOf(minSize))
					.replaceFirst("#\\{1\\}", property)
					.replaceFirst("#\\{2\\}", String.valueOf(objectSize)));
		}
		
		return null;
	}
}