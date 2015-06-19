package com.github.aureliano.damihilogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.annotation.validation.AssertFalse;
import com.github.aureliano.damihilogs.annotation.validation.AssertTrue;
import com.github.aureliano.damihilogs.annotation.validation.Max;
import com.github.aureliano.damihilogs.annotation.validation.Min;
import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.annotation.validation.Size;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class CustomInputConfig implements IConfigInput {

	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	private Integer myNewProperty;
	private Boolean ok;
	private Boolean notOk;
	
	public CustomInputConfig() {
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<IExceptionHandler>();
		
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.inputExecutionListeners = new ArrayList<ExecutionListener>();
	}
	
	@Override
	public CustomInputConfig clone() {
		return new CustomInputConfig()
			.withConfigurationId(this.id)
			.withMatcher(this.matcher)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers)
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners)
			.withMyNewProperty(this.myNewProperty);
	}
	
	@Override
	public IConfiguration putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}

	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	@NotNull
	@Min(value = 3)
	@Max(value = 5)
	@Size(min = 3, max = 5)
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public CustomInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public IMatcher getMatcher() {
		return this.matcher;
	}

	@Override
	public CustomInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public CustomInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@AssertTrue
	public Boolean isOk() {
		return ok;
	}
	
	public CustomInputConfig withOk(Boolean value) {
		this.ok = value;
		return this;
	}

	@AssertFalse
	public Boolean isNotOk() {
		return notOk;
	}
	
	public CustomInputConfig withNotOk(Boolean value) {
		this.notOk = value;
		return this;
	}

	@Override
	public CustomInputConfig addExceptionHandler(IExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
		return this;
	}

	@Override
	@Min(value = 1)
	@Max(value = 1)
	public List<IExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}

	@Override
	public List<DataReadingListener> getDataReadingListeners() {
		return this.dataReadingListeners;
	}

	@Override
	public CustomInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}

	@Override
	public CustomInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}

	@Override
	public List<ExecutionListener> getExecutionListeners() {
		return this.inputExecutionListeners;
	}

	@Override
	public CustomInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public CustomInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}
	
	private CustomInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	private CustomInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}

	public Integer getMyNewProperty() {
		return this.myNewProperty;
	}

	public CustomInputConfig withMyNewProperty(Integer myNewProperty) {
		this.myNewProperty = myNewProperty;
		return this;
	}

	@Override
	public String id() {
		return "CUSTOM_INPUT_CONFIG";
	}
}