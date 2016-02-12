package com.github.aureliano.evtbridge.core.helper;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withName;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Set;

import org.reflections.ReflectionUtils;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ReflectionHelper {

    @SuppressWarnings("unchecked")
	public static Field getField(Class<?> clazz, String fieldName) {
        Set<Field> fields = getAllFields(clazz, withName(fieldName));
        if (fields.size() != 1) {
        	throw new EventBridgeException(String.format("Unable to find field %s for class %s", fieldName, clazz.getSimpleName()));
        }
        Field field = fields.iterator().next();
        field.setAccessible(true);
        return field;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getField(object.getClass(), fieldName);
        return getFieldValue(object, field);
    }

    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw new EventBridgeException(ex);
        }
    }

    public static Class<?> getGenericType(Field field) {
        if (!Collection.class.isAssignableFrom(field.getType())) {
            return field.getType();
        }
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    public static Class<?> getGenericType(Method method) {
        if (!Collection.class.isAssignableFrom(method.getReturnType())) {
            return method.getReturnType();
        }
        ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
        return (Class<?>) type.getActualTypeArguments()[0];
    }
    
    public static Object callMethod(Object object, String methodName, Class<?>[] parametersType, Object[] methodParameters) {
    	Class<?> clazz = object.getClass();
    	try {
    		Method method = clazz.getMethod(methodName, parametersType);
    		return method.invoke(object, methodParameters);
    	} catch (Exception ex) {
    		throw new EventBridgeException(ex);
    	}
    }
    
    public static Object newInstance(Class<?> clazz) {
    	try {
    		return clazz.newInstance();
    	} catch (Exception ex) {
    		throw new EventBridgeException(ex);
    	}
    }
    
    public static Object newInstance(String fullClassName) {
    	Class<?> clazz = ReflectionUtils.forName(fullClassName);
    	return newInstance(clazz);
    }
    
    public static String getSimpleAccessMethodName(Method method) {
    	String name = method.getName().replaceFirst("^(get|is|set|with)", "");
    	return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}