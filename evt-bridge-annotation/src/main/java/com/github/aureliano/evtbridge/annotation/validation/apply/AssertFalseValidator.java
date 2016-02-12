package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.helper.ValidationHelper;
import com.github.aureliano.evtbridge.annotation.validation.AssertFalse;

public class AssertFalseValidator implements IValidator {

	public AssertFalseValidator() {
		super();
	}
	
	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ValidationHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ValidationHelper.callMethod(object, method.getName(), null, null);
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		String message = ((AssertFalse) annotation).message();
		
		if (!Boolean.FALSE.equals(returnedValue)) {
			violations.add(new ConstraintViolation()
				.withValidator(AssertFalse.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", ValidationHelper.objectToString(returnedValue))));
		}
		
		return violations;
	}
}