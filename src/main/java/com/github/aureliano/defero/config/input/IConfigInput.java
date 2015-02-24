package com.github.aureliano.defero.config.input;

import com.github.aureliano.defero.config.IConfiguration;

public interface IConfigInput extends IConfiguration {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
}