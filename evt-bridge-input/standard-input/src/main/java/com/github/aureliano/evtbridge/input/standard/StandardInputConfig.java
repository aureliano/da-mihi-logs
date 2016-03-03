package com.github.aureliano.evtbridge.input.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
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
	title = "Input configuration to consume from standard input (stdin).",
	type = "object"
)
public class StandardInputConfig implements IConfigInput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(InputConfigTypes.STANDARD.name())
			.withAgent(StandardDataReader.class)
			.withConfiguration(StandardInputConfig.class)
			.withConverter(StandardInputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private String encoding;
	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	public StandardInputConfig() {
		this.encoding = "UTF-8";
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<>();
		
		this.dataReadingListeners = new ArrayList<>();
		this.inputExecutionListeners = new ArrayList<>();
		this.matcher = new SingleLineMatcher();
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public StandardInputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
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
		return id;
	}

	@Override
	public StandardInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
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
	
	public StandardInputConfig withMatcher(IMatcher matcher) {
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
	public StandardInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@SchemaProperty(
		property = "dataReadingListeners",
		types = "array",
		description = "Fully qualified name of the class that will listen and fire an event before and after a log event is read.",
		required = false
	)
	public List<DataReadingListener> getDataReadingListeners() {
		return dataReadingListeners;
	}

	public StandardInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public StandardInputConfig addDataReadingListener(DataReadingListener listener) {
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
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners);
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
	public StandardInputConfig addExceptionHandler(IExceptionHandler handler) {
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
	public StandardInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public StandardInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}

	@Override
	public String id() {
		return InputConfigTypes.STANDARD.name();
	}
}