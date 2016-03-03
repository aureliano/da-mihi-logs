package com.github.aureliano.evtbridge.app.helper;

import org.reflections.Reflections;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.config.IConfiguration;

public final class AppHelper {

	private AppHelper() {}
	
	public static void initializeResources() {
		Reflections reflections = new Reflections("com.github.aureliano.evtbridge");
		Class<?>[] classes = reflections.getSubTypesOf(IConfiguration.class).toArray(new Class<?>[0]);
		
		try {
			for (Class<?> clazz : classes) {
				Class.forName(clazz.getName());
			}
		} catch (ClassNotFoundException ex) {
			throw new EventBridgeException(ex);
		}
	}
}