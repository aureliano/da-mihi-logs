package com.github.aureliano.almamater.core.config;

import java.util.Properties;

public interface IConfiguration {

	public abstract IConfiguration clone();
	
	public abstract IConfiguration putMetadata(String key, String value);
	
	public abstract String getMetadata(String key);
	
	public abstract Properties getMetadata();
	
	public abstract String id();
}