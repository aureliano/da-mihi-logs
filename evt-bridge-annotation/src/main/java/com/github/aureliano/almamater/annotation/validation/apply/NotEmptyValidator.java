package com.github.aureliano.almamater.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.almamater.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.core.exception.AlmaMaterException;
import com.github.aureliano.evtbridge.core.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.core.helper.StringHelper;

public class NotEmptyValidator implements IValidator {

	public NotEmptyValidator() {
		super();
	}

	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		if ((returnedValue != null) && !(returnedValue instanceof String)) {
			throw new AlmaMaterException("Expected to find a String but got " + returnedValue.getClass().getName() +
					". You might want to use NotNull annotation instead of NotEmpty.");
		}
		
		if (StringHelper.isEmpty(StringHelper.parse(returnedValue))) {
			String message = ((NotEmpty) annotation).message();
			violations.add(new ConstraintViolation()
				.withValidator(NotEmpty.class)
				.withMessage(message.replaceFirst("#\\{0\\}", property)));
		}
		
		return violations;
	}
}