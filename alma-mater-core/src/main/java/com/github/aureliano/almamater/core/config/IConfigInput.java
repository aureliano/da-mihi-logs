package com.github.aureliano.almamater.core.config;

public interface IConfigInput extends IConfiguration {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
	
	public abstract Boolean isUseLastExecutionRecords();
	
	public abstract IConfigInput withUseLastExecutionRecords(Boolean useLastExecutionRecords);
}