package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import com.github.aureliano.damihilogs.annotation.validation.Size;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class SizeValidator implements IValidator {

	public SizeValidator() {
		super();
	}
	
	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		if (returnedValue == null) {
			returnedValue = "";
		}
		
		Size sizeAnnotation = (Size) annotation;
		String message = sizeAnnotation.message();
		int minSize = sizeAnnotation.min();
		int maxSize = sizeAnnotation.max();
		int objectSize;
		
		if (Collection.class.isAssignableFrom(returnedValue.getClass())) {
			objectSize = ((Collection<?>) returnedValue).size();
		} else if (StringHelper.isNumeric(returnedValue.toString())) {
			Double d = Double.parseDouble(returnedValue.toString());
			objectSize = d.intValue();
		} else {
			objectSize = returnedValue.toString().length();
		}
		
		if ((minSize > objectSize) || (maxSize < objectSize)) {
			return new ConstraintViolation()
				.withValidator(Size.class)
				.withMessage(message
					.replaceFirst("#\\{0\\}", property)
					.replaceFirst("#\\{1\\}", String.valueOf(minSize))
					.replaceFirst("#\\{2\\}", String.valueOf(maxSize))
					.replaceFirst("#\\{3\\}", String.valueOf(objectSize)));
		}
		
		return null;
	}
}