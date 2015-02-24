package com.github.aureliano.defero.config.input;

public interface IConfigInput {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
}