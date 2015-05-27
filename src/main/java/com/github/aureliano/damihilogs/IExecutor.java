package com.github.aureliano.damihilogs;

import com.github.aureliano.damihilogs.config.IConfiguration;

public interface IExecutor {

	public abstract IConfiguration getConfiguration();
	
	public abstract IExecutor withConfiguration(IConfiguration configuration);
}