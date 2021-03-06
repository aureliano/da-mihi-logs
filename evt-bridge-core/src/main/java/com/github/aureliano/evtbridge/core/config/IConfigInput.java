package com.github.aureliano.evtbridge.core.config;

import java.util.List;

import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;
import com.github.aureliano.evtbridge.core.listener.ExecutionListener;
import com.github.aureliano.evtbridge.core.matcher.IMatcher;

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
	
	public IConfigInput addDataReadingListener(DataReadingListener listener);
	
	public List<ExecutionListener> getExecutionListeners();
	
	public IConfigInput withExecutionListeners(List<ExecutionListener> inputExecutionListeners);
	
	public IConfigInput addExecutionListener(ExecutionListener listener);
}