package com.github.aureliano.damihilogs.config.input;

import com.github.aureliano.damihilogs.config.IConfiguration;

public interface IConfigInput extends IConfiguration {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
	
	public abstract boolean isUseLastExecutionRecords();
	
	public abstract IConfigInput withUseLastExecutionRecords(boolean useLastExecutionRecords);
}