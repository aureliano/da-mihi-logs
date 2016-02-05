package com.github.aureliano.almamater.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.almamater.annotation.validation.AssertFalse;
import com.github.aureliano.almamater.core.helper.ReflectionHelper;
import com.github.aureliano.almamater.core.helper.StringHelper;

public class AssertFalseValidator implements IValidator {

	public AssertFalseValidator() {
		super();
	}
	
	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		String message = ((AssertFalse) annotation).message();
		
		if (!Boolean.FALSE.equals(returnedValue)) {
			violations.add(new ConstraintViolation()
				.withValidator(AssertFalse.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", StringHelper.toString(returnedValue))));
		}
		
		return violations;
	}
}