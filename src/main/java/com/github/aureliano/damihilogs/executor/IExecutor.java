package com.github.aureliano.damihilogs.executor;

import com.github.aureliano.damihilogs.config.IConfiguration;

public interface IExecutor {

	public abstract IConfiguration getConfiguration();
	
	public abstract IExecutor withConfiguration(IConfiguration configuration);
}