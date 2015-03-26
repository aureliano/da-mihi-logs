package com.github.aureliano.damihilogs.config.input;

import com.github.aureliano.damihilogs.config.IConfiguration;

public interface IConfigInput extends IConfiguration {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
	
	public abstract Boolean isUseLastExecutionRecords();
	
	public abstract IConfigInput withUseLastExecutionRecords(Boolean useLastExecutionRecords);
}