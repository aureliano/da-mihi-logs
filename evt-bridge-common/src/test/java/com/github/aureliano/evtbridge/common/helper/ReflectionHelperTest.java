package com.github.aureliano.evtbridge.common.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import com.github.aureliano.evtbridge.common.model.CommonModel;

public class ReflectionHelperTest {

	@Test
	public void testGetField() {
		Field field = ReflectionHelper.getField(CommonModel.class, "id");
		
		assertEquals("id", field.getName());
		assertEquals(Integer.class, field.getType());
	}
	
	@Test
	public void testGetFieldValueByString() {
		CommonModel model = new CommonModel().withId(1).withName("test");
		
		Object expected = 1;
		Object actual = ReflectionHelper.getFieldValue(model, "id");
		
		assertEquals(expected, actual);
		
		expected = "test";
		actual = ReflectionHelper.getFieldValue(model, "name");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetFieldValueByField() {
		CommonModel model = new CommonModel().withId(1).withName("test");
		
		Field field = ReflectionHelper.getField(CommonModel.class, "id");
		Object expected = 1;
		Object actual = ReflectionHelper.getFieldValue(model, field);
		
		assertEquals(expected, actual);
		
		field = ReflectionHelper.getField(CommonModel.class, "name");
		expected = "test";
		actual = ReflectionHelper.getFieldValue(model, field);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMethod() {
		String methodName = "getValues";
		Method method = ReflectionHelper.getMethod(CommonModel.class, methodName, new Class<?>[0]);
		
		assertEquals(methodName, method.getName());
		assertEquals(List.class, method.getReturnType());
	}
	
	@Test
	public void testGetGenericTypeByField() {
		Field field = ReflectionHelper.getField(CommonModel.class, "values");
		Class<?> expected = String.class;
		Class<?> actual = ReflectionHelper.getGenericType(field);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetGenericTypeByMethod() {
		Method method = ReflectionHelper.getMethod(CommonModel.class, "getValues", new Class<?>[0]);
		Class<?> expected = String.class;
		Class<?> actual = ReflectionHelper.getGenericType(method);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCallMethod() {
		CommonModel model = new CommonModel().withId(1).withName("test");
		Object expected = 1;
		Object actual = ReflectionHelper.callMethod(model, "getId", new Class<?>[0], new Object[0]);
		
		assertEquals(expected, actual);
		
		expected = "test";
		actual = ReflectionHelper.callMethod(model, "getName", new Class<?>[0], new Object[0]);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testNewInstanceByClass() {
		Object model = ReflectionHelper.newInstance(CommonModel.class);
		
		assertNotNull(model);
		assertTrue(model instanceof CommonModel);
	}
	
	@Test
	public void testNewInstanceByString() {
		Object model = ReflectionHelper.newInstance(CommonModel.class.getName());
		
		assertNotNull(model);
		assertTrue(model instanceof CommonModel);
	}
	
	@Test
	public void testGetSimpleAccessMethodName() {
		Method method = ReflectionHelper.getMethod(CommonModel.class, "getId", new Class<?>[0]);
		String expected = "id";
		String actual = ReflectionHelper.getSimpleAccessMethodName(method);
		
		assertEquals(expected, actual);
		
		method = ReflectionHelper.getMethod(CommonModel.class, "setId", new Class<?>[] { Integer.class });
		expected = "id";
		actual = ReflectionHelper.getSimpleAccessMethodName(method);
		
		assertEquals(expected, actual);
		
		method = ReflectionHelper.getMethod(CommonModel.class, "withId", new Class<?>[] { Integer.class });
		expected = "id";
		actual = ReflectionHelper.getSimpleAccessMethodName(method);
		
		assertEquals(expected, actual);
	}
}