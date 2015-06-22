package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.damihilogs.annotation.validation.Max;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class MaxValidator implements IValidator {

	public MaxValidator() {
		super();
	}

	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		if (returnedValue == null) {
			returnedValue = "";
		}
		
		String message = ((Max) annotation).message();
		int maxSize = ((Max) annotation).value();
		int objectSize;
		
		if (Collection.class.isAssignableFrom(returnedValue.getClass())) {
			objectSize = ((Collection<?>) returnedValue).size();
		} else if (!(returnedValue instanceof String) && StringHelper.isNumeric(returnedValue.toString())) {
			Double d = Double.parseDouble(returnedValue.toString());
			objectSize = d.intValue();
		} else {
			objectSize = returnedValue.toString().length();
		}
		
		if (maxSize < objectSize) {
			violations.add(new ConstraintViolation()
				.withValidator(Max.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", String.valueOf(maxSize))
					.replaceFirst("#\\{1\\}", property)
					.replaceFirst("#\\{2\\}", String.valueOf(objectSize))));
		}
		
		return violations;
	}
}