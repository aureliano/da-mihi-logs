package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.validation.Size;
import com.github.aureliano.evtbridge.core.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.core.helper.StringHelper;

public class SizeValidator implements IValidator {

	public SizeValidator() {
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
		Size sizeAnnotation = (Size) annotation;
		String message = sizeAnnotation.message();
		int minSize = sizeAnnotation.min();
		int maxSize = sizeAnnotation.max();
		int objectSize;
		
		if (Collection.class.isAssignableFrom(returnedValue.getClass())) {
			objectSize = ((Collection<?>) returnedValue).size();
		} else if (!(returnedValue instanceof String) && StringHelper.isNumeric(returnedValue.toString())) {
			Double d = Double.parseDouble(returnedValue.toString());
			objectSize = d.intValue();
		} else {
			objectSize = returnedValue.toString().length();
		}
		
		if ((minSize > objectSize) || (maxSize < objectSize)) {
			violations.add(new ConstraintViolation()
				.withValidator(Size.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", String.valueOf(minSize))
					.replaceFirst("#\\{2\\}", String.valueOf(maxSize))
					.replaceFirst("#\\{3\\}", String.valueOf(objectSize))));
		}
		
		return violations;
	}
}