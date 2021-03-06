package com.github.aureliano.evtbridge.core.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import org.yaml.snakeyaml.Yaml;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.data.ObjectMapperSingleton;

public final class DataHelper {

	private DataHelper() {
		super();
	}
	
	public static Properties copyProperties(Properties properties) {
		Properties copy = new Properties();
		for (Object key : properties.keySet()) {
			copy.setProperty(key.toString(), properties.getProperty(key.toString()));
		}
		
		return copy;
	}
	
	public static Properties sortProperties(Properties p) {
		Properties properties = new Properties() {
			private static final long serialVersionUID = 1L;

			@Override
			public synchronized Enumeration<Object> keys() {
				return Collections.enumeration(new TreeSet<Object>(super.keySet()));
			}
		};
		
		for (Object key : p.keySet()) {
			properties.put(key, p.get(key));
		}
		
		return properties;
	}
	
	public static String propertiesToJson(Properties properties) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		for (Object key : properties.keySet()) {
			map.put(key.toString(), properties.get(key));
		}
		
		try {
			return ObjectMapperSingleton.instance().getObjectMapper().writeValueAsString(map);
		} catch (Exception ex) {
			throw new EventBridgeException(ex);
		}
	}
	
	public static Properties mapToProperties(Map<String, Object> map) {
		Properties properties = new Properties();
		for (String key : map.keySet()) {
			properties.setProperty(key, map.get(key).toString());
		}
		
		return properties;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAsHash(Map<String, Object> data, String key) {
		Object map = data.get(key);
		if (map == null) {
			return null;
		}
		
		return (Map<String, Object>) map;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T encapsulateIntoList(Object data) {
		if (data == null) {
			return null;
		}
		
		Class<?> clazz = data.getClass();
		if (List.class.isAssignableFrom(clazz)) {
			return (T) data;
		} else {
			List<Object> list = new ArrayList<>();
			list.add(data);
			return (T) list;
		}
	}
	
	public static <T> T jsonStringToObject(String jsonString, Class<T> valueType) {
		try {
			return (T) ObjectMapperSingleton.instance().getObjectMapper().readValue(jsonString, valueType);
		} catch (Exception ex) {
			throw new EventBridgeException(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T yamlStringToObject(String yamlString, Class<T> valueType) {
		try {
			return (T) new Yaml().load(yamlString);
		} catch (Exception ex) {
			throw new EventBridgeException(ex);
		}
	}
}