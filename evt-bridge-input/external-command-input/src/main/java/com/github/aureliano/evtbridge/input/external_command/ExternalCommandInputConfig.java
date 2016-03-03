package com.github.aureliano.evtbridge.input.external_command;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;
import com.github.aureliano.evtbridge.core.listener.ExecutionListener;
import com.github.aureliano.evtbridge.core.matcher.IMatcher;
import com.github.aureliano.evtbridge.core.matcher.SingleLineMatcher;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.register.ServiceRegistration;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Input configuration to consume from external command output.",
	type = "object"
)
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
		this.matcher = new SingleLineMatcher();
	}

	@SchemaProperty(
		property = "matcher",
		types = "string",
		description = "Fully qualified name of class matcher used to get text from input.",
		required = false,
		defaultValue = "SingleLineMatcher"
	)
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public ExternalCommandInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}
	
	@Override
	@SchemaProperty(
		property = "useLastExecutionLog",
		types = "boolean",
		description = "Whether to use data from earlier execution or not.",
		defaultValue = "false"
	)
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public ExternalCommandInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "dataReadingListeners",
		types = "array",
		description = "Fully qualified name of the class that will listen and fire an event before and after a log event is read.",
		required = false
	)
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
	@SchemaProperty(
		property = "configurationId",
		types = "string",
		description = "Input configuration id.",
		defaultValue = "Auto-generated id.",
		required = false
	)
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public ExternalCommandInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	@NotEmpty
	@SchemaProperty(
		property = "command",
		types = "string",
		description = "The external command which output will be read.",
		required = true
	)
	public String getCommand() {
		return command;
	}
	
	public ExternalCommandInputConfig withCommand(String command) {
		this.command = command;
		return this;
	}

	@SchemaProperty(
		property = "parameters",
		types = "array",
		description = "External command's parameters.",
		required = false
	)
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
	@SchemaProperty(
		property = "metadata",
		types = "object",
		description = "A key-value pair (hash <string, string>) which provides a mechanism to exchange metadata between configurations (main, inputs and outputs).",
		required = false
	)
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public ExternalCommandInputConfig addExceptionHandler(IExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
		return this;
	}

	@Override
	@SchemaProperty(
		property = "exceptionHandlers",
		types = "array",
		description = "Fully qualified name of the class that will handle exceptions.",
		required = false
	)
	public List<IExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}

	@Override
	@SchemaProperty(
		property = "executionListeners",
		types = "array",
		description = "Fully qualified name of the class that will listen and fire an event before and after data reading execution.",
		required = false
	)
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