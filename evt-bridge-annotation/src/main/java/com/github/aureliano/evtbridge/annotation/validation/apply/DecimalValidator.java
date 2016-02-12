package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.validation.Decimal;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;

public class DecimalValidator implements IValidator {

	public DecimalValidator() {
		super();
	}

	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(object, method.getName(), null, null);
		
		if (returnedValue == null) {
			return null;
		}
		
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		Decimal decimalAnnotation = (Decimal) annotation;
		String message = decimalAnnotation.message();
		double minSize = decimalAnnotation.min();
		double maxSize = decimalAnnotation.max();
		
		if (!((returnedValue instanceof Double) || (returnedValue instanceof Float))) {
			throw new EventBridgeException("Decimal validator supports only Double and Float values but got " +
					returnedValue.getClass().getName());
		}
		
		Double objectSize = Double.parseDouble(returnedValue.toString());
		
		if ((minSize > objectSize) || (maxSize < objectSize)) {
			violations.add(new ConstraintViolation()
				.withValidator(Decimal.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", String.valueOf(minSize))
					.replaceFirst("#\\{2\\}", String.valueOf(maxSize))
					.replaceFirst("#\\{3\\}", String.valueOf(objectSize))));
		}
		
		return violations;
	}
}