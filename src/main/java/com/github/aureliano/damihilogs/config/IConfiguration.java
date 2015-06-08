package com.github.aureliano.damihilogs.config;

import java.util.Properties;

public interface IConfiguration {

	public abstract IConfiguration clone();
	
	public abstract IConfiguration putMetadata(String key, String value);
	
	public abstract String getMetadata(String key);
	
	public abstract Properties getMetadata();
	
	public abstract String type();
}