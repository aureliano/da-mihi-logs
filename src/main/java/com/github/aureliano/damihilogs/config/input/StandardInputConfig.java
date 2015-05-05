package com.github.aureliano.damihilogs.config.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class StandardInputConfig implements IConfigInput {

	private String encoding;
	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	
	public StandardInputConfig() {
		this.encoding = "UTF-8";
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<IExceptionHandler>();
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public StandardInputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	@Override
	public String getConfigurationId() {
		return id;
	}

	@Override
	public StandardInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public StandardInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public StandardInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	public List<DataReadingListener> getDataReadingListeners() {
		return dataReadingListeners;
	}

	public StandardInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public StandardInputConfig addDataReadingListeners(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}
	
	@Override
	public StandardInputConfig clone() {
		return new StandardInputConfig()
			.withEncoding(this.encoding)
			.withConfigurationId(this.id)
			.withMatcher(this.matcher)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers)
			.withDataReadingListeners(this.dataReadingListeners);
	}
	
	@Override
	public StandardInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected StandardInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected StandardInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public StandardInputConfig addExceptionHandler(IExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
		return this;
	}

	@Override
	public List<IExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}
}