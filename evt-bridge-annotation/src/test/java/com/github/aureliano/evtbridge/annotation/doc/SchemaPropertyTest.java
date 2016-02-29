package com.github.aureliano.evtbridge.annotation.doc;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.aureliano.evtbridge.common.doc.SchemaProperty;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;

public class SchemaPropertyTest {

	private final List<String> IGNORABLE_METHODS = new ArrayList<>(
		Arrays.asList("equals", "toString", "hashCode", "annotationType")
	);
	
	@Test
	public void testCheckAttributes() {
		Method[] methods = com.github.aureliano.evtbridge.annotation.doc.SchemaProperty.class.getMethods();
		for (Method m : methods) {
			if (IGNORABLE_METHODS.contains(m.getName())) {
				continue;
			}
			this.checkAttribute(m);
		}
	}
	
	private void checkAttribute(Method method) {
		Field field = ReflectionHelper.getField(SchemaProperty.class, method.getName());
		
		assertEquals(method.getName(), field.getName());
		assertEquals(method.getReturnType(), field.getType());
	}
}