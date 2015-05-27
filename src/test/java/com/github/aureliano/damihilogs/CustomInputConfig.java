package com.github.aureliano.damihilogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class CustomInputConfig implements IConfigInput {

	private String id;
	
	public CustomInputConfig() {
		super();
	}
	
	@Override
	public CustomInputConfig clone() {
		return new CustomInputConfig();
	}
	
	@Override
	public IConfiguration putMetadata(String key, String value) {
		return this;
	}

	@Override
	public String getMetadata(String key) {
		return null;
	}

	@Override
	public Properties getMetadata() {
		return new Properties();
	}

	@Override
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public IConfigInput withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public IMatcher getMatcher() {
		return null;
	}

	@Override
	public IConfigInput withMatcher(IMatcher matcher) {
		return this;
	}

	@Override
	public Boolean isUseLastExecutionRecords() {
		return false;
	}

	@Override
	public IConfigInput withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		return this;
	}

	@Override
	public IConfigInput addExceptionHandler(IExceptionHandler handler) {
		return this;
	}

	@Override
	public List<IExceptionHandler> getExceptionHandlers() {
		return new ArrayList<IExceptionHandler>();
	}

	@Override
	public List<DataReadingListener> getDataReadingListeners() {
		return new ArrayList<DataReadingListener>();
	}

	@Override
	public IConfigInput withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		return this;
	}

	@Override
	public IConfigInput addDataReadingListener(DataReadingListener listener) {
		return this;
	}

	@Override
	public List<ExecutionListener> getExecutionListeners() {
		return new ArrayList<ExecutionListener>();
	}

	@Override
	public IConfigInput withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		return this;
	}

	@Override
	public IConfigInput addExecutionListener(ExecutionListener listener) {
		return this;
	}
}