package com.github.aureliano.evtbridge.core.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.validation.AssertTrue;
import com.github.aureliano.evtbridge.core.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.core.helper.StringHelper;

public class AssertTrueValidator implements IValidator {

	public AssertTrueValidator() {
		super();
	}
	
	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		String message = ((AssertTrue) annotation).message();
		
		if (!Boolean.TRUE.equals(returnedValue)) {
			violations.add(new ConstraintViolation()
				.withValidator(AssertTrue.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", StringHelper.toString(returnedValue))));
		}
		
		return violations;
	}
}