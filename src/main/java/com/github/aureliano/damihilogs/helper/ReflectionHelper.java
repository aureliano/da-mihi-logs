package com.github.aureliano.damihilogs.helper;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withName;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.ReflectionUtils;

import com.github.aureliano.damihilogs.es.annotations.IndexableId;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class ReflectionHelper {

    private static final Logger logger = Logger.getLogger(ReflectionHelper.class);

    @SuppressWarnings("unchecked")
	public static Field getField(Class<?> clazz, String fieldName) {
        Set<Field> fields = getAllFields(clazz, withName(fieldName));
        if (fields.size() != 1) {
        	throw new DaMihiLogsException(String.format("Unable to find field %s for class %s", fieldName, clazz.getSimpleName()));
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
            logger.error("Failed to get value from field", ex);
            throw new DaMihiLogsException(ex);
        }
    }

    @SuppressWarnings("unchecked")
	public static Field getIdField(Class<?> clazz) {
        Set<Field> fields = getAllFields(clazz, withAnnotation(IndexableId.class));
        if (fields.size() != 1) {
        	throw new DaMihiLogsException(String.format("Unable to find id field for class %s", clazz.getSimpleName()));
        }
        return fields.iterator().next();
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
    		throw new DaMihiLogsException(ex);
    	}
    }
    
    public static Object newInstance(Class<?> clazz) {
    	try {
    		return clazz.newInstance();
    	} catch (Exception ex) {
    		throw new DaMihiLogsException(ex);
    	}
    }
    
    public static Object newInstance(String fullClassName) {
    	Class<?> clazz = ReflectionUtils.forName(fullClassName);
    	return newInstance(clazz);
    }
    
    public static String getSimpleAccessMethodName(Method method) {
    	String name = method.getName().replaceFirst("^(get|set|with)", "");
    	return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}