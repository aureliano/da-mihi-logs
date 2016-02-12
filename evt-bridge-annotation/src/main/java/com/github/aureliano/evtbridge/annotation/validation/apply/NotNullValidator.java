package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;

public class NotNullValidator implements IValidator {

	public NotNullValidator() {
		super();
	}

	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		if (returnedValue == null) {
			String message = ((NotNull) annotation).message();
			violations.add(new ConstraintViolation()
				.withValidator(NotNull.class)
				.withMessage(message.replaceFirst("#\\{0\\}", property)));
		}
		
		return violations;
	}
}