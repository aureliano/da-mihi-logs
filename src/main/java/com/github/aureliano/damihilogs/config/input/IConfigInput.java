package com.github.aureliano.damihilogs.config.input;

import java.util.List;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public interface IConfigInput extends IConfiguration {

	public abstract String getConfigurationId();
	
	public abstract IConfigInput withConfigurationId(String id);
	
	public abstract IMatcher getMatcher();
	
	public abstract IConfigInput withMatcher(IMatcher matcher);
	
	public abstract Boolean isUseLastExecutionRecords();
	
	public abstract IConfigInput withUseLastExecutionRecords(Boolean useLastExecutionRecords);
	
	public abstract IConfigInput addExceptionHandler(IExceptionHandler handler);
	
	public abstract List<IExceptionHandler> getExceptionHandlers();
	
	public List<DataReadingListener> getDataReadingListeners();
	
	public IConfigInput withDataReadingListeners(List<DataReadingListener> dataReadingListeners);
	
	public IConfigInput addDataReadingListeners(DataReadingListener listener);
}