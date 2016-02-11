package com.github.aureliano.evtbridge.core.helper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

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
}