package com.github.aureliano.almamater.core.agent;

import com.github.aureliano.almamater.core.config.IConfiguration;

public interface IAgent {

	public abstract IConfiguration getConfiguration();
	
	public abstract IAgent withConfiguration(IConfiguration configuration);
}