package com.github.aureliano.evtbridge.core.data;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {

	private static ObjectMapperSingleton instance;
	
	private ObjectMapper objectMapper;
	
	private ObjectMapperSingleton() {
		this.objectMapper = new ObjectMapper();
	}
	
	public static final ObjectMapperSingleton instance() {
		if (instance == null) {
			instance = new ObjectMapperSingleton();
		}
		
		return instance;
	}
	
	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}
}