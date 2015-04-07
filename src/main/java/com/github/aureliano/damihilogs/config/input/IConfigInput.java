package com.github.aureliano.damihilogs.config.input;

import java.util.List;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public interface IConfigInput extends IConfiguration {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
	
	public abstract Boolean isUseLastExecutionRecords();
	
	public abstract IConfigInput withUseLastExecutionRecords(Boolean useLastExecutionRecords);
	
	public abstract IConfigInput addExceptionHandler(IExceptionHandler handler);
	
	public abstract List<IExceptionHandler> getExceptionHandlers();
}