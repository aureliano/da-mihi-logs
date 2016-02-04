package com.github.aureliano.damihilogs.executor;

import com.github.aureliano.damihilogs.config.IConfiguration;

public interface IAgent {

	public abstract IConfiguration getConfiguration();
	
	public abstract IAgent withConfiguration(IConfiguration configuration);
}