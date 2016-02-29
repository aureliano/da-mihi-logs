package com.github.aureliano.evtbridge.annotation.helper;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.aureliano.evtbridge.common.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;

public final class AssertionHelper {

	private static final List<String> IGNORABLE_METHODS = new ArrayList<>(
		Arrays.asList("equals", "toString", "hashCode", "annotationType")
	);
	
	private AssertionHelper() {}
	
	public static void checkAttributes(Class<? extends Annotation> annotation, Class<?> schema) {
		Method[] methods = annotation.getMethods();
		for (Method m : methods) {
			if (IGNORABLE_METHODS.contains(m.getName())) {
				continue;
			}
			checkAttribute(m);
		}
	}
	
	private static  void checkAttribute(Method method) {
		Field field = ReflectionHelper.getField(SchemaConfiguration.class, method.getName());
		
		assertEquals(method.getName(), field.getName());
		assertEquals(method.getReturnType(), field.getType());
	}
}