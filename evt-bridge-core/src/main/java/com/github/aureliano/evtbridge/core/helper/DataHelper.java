package com.github.aureliano.evtbridge.core.helper;

import java.util.Properties;

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
}