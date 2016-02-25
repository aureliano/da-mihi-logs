package com.github.aureliano.evtbridge.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class EventBridgeMetadata {

	private static final String METADATA_RESOURCE = "metadata.properties";
	
	private static EventBridgeMetadata metadata;
	private Properties properties;
	
	private EventBridgeMetadata() {
		this.loadProperties();
	}
	
	public static EventBridgeMetadata instance() {
		if (metadata == null) {
			metadata = new EventBridgeMetadata();
		}
		
		return metadata;
	}
	
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
	
	private void loadProperties() {
		this.properties = new Properties();
		InputStream stream = ClassLoader.getSystemResourceAsStream(METADATA_RESOURCE);
		
		if (stream == null) {
			throw new EventBridgeException("Resource not '" + METADATA_RESOURCE + "' found.");
		}
		
		try {
			this.properties.load(stream);
			stream.close();
		} catch (IOException ex) {
			throw new EventBridgeException(ex);
		}
	}
}