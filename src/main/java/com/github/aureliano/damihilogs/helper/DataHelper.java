package com.github.aureliano.damihilogs.helper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class DataHelper {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private DataHelper() {
		super();
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
			return OBJECT_MAPPER.writeValueAsString(map);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public static <T> T jsonStringToObject(String jsonString, Class<T> valueType) {
		try {
			return (T) OBJECT_MAPPER.readValue(jsonString, valueType);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}