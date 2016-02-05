package com.github.aureliano.almamater.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.almamater.annotation.validation.Pattern;
import com.github.aureliano.evtbridge.core.exception.AlmaMaterException;
import com.github.aureliano.evtbridge.core.helper.ReflectionHelper;

public class PatternValidator implements IValidator {

	public PatternValidator() {
		super();
	}

	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		
		if (returnedValue == null) {
			returnedValue = "";
		}
		
		if (!(returnedValue instanceof String)) {
			throw new AlmaMaterException(Pattern.class.getName() + " can be applyed only for " +
					String.class.getName() + " types.");
		}
		
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		String message = ((Pattern) annotation).message();
		String regex = ((Pattern) annotation).value();
		
		if (!returnedValue.toString().matches(regex)) {
			violations.add(new ConstraintViolation()
				.withValidator(Pattern.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", regex.replace("\\", "\\\\"))));
		}
		
		return violations;
	}
}