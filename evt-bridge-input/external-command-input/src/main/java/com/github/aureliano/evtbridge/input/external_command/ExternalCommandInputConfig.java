package com.github.aureliano.evtbridge.input.external_command;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;
import com.github.aureliano.evtbridge.core.listener.ExecutionListener;
import com.github.aureliano.evtbridge.core.matcher.IMatcher;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.register.ServiceRegistration;

public class ExternalCommandInputConfig implements IConfigInput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(InputConfigTypes.EXTERNAL_COMMAND.name())
			.withAgent(ExternalCommandDataReader.class)
			.withConfiguration(ExternalCommandInputConfig.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private String id;
	private String command;
	private List<String> parameters;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	public ExternalCommandInputConfig() {
		this.useLastExecutionRecords = false;
		this.parameters = new ArrayList<String>();
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<>();
		
		this.dataReadingListeners = new ArrayList<>();
		this.inputExecutionListeners = new ArrayList<>();
	}
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public ExternalCommandInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public ExternalCommandInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	public List<DataReadingListener> getDataReadingListeners() {
		return dataReadingListeners;
	}

	public ExternalCommandInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public ExternalCommandInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}

	@Override
	public ExternalCommandInputConfig clone() {
		return new ExternalCommandInputConfig()
			.withConfigurationId(this.id)
			.withCommand(this.command)
			.withMatcher(this.matcher)
			.withParameters(this.parameters)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers)
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners);
	}

	@Override
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public ExternalCommandInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	@NotEmpty
	public String getCommand() {
		return command;
	}
	
	public ExternalCommandInputConfig withCommand(String command) {
		this.command = command;
		return this;
	}
	
	public List<String> getParameters() {
		return parameters;
	}
	
	public ExternalCommandInputConfig withParameters(List<String> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	public ExternalCommandInputConfig addParameter(String parameter) {
		this.parameters.add(parameter);
		return this;
	}
	
	@Override
	public ExternalCommandInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected ExternalCommandInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected ExternalCommandInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public ExternalCommandInputConfig addExceptionHandler(IExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
		return this;
	}

	@Override
	public List<IExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}

	@Override
	public List<ExecutionListener> getExecutionListeners() {
		return this.inputExecutionListeners;
	}

	@Override
	public ExternalCommandInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public ExternalCommandInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}

	@Override
	public String id() {
		return InputConfigTypes.EXTERNAL_COMMAND.name();
	}
}