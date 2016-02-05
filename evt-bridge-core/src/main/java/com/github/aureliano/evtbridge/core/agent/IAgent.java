package com.github.aureliano.evtbridge.core.agent;

import com.github.aureliano.evtbridge.core.config.IConfiguration;

public interface IAgent {

	public abstract IConfiguration getConfiguration();
	
	public abstract IAgent withConfiguration(IConfiguration configuration);
}