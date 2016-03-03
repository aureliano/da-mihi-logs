package com.github.aureliano.evtbridge.input.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.Valid;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;
import com.github.aureliano.evtbridge.core.listener.ExecutionListener;
import com.github.aureliano.evtbridge.core.matcher.IMatcher;
import com.github.aureliano.evtbridge.core.matcher.SingleLineMatcher;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.register.ServiceRegistration;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Input configuration to consume from Java Database Connectivity.",
	type = "object"
)
public class JdbcInputConfig implements IConfigInput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(InputConfigTypes.JDBC.name())
			.withAgent(JdbcDataReader.class)
			.withConfiguration(JdbcInputConfig.class)
			.withConverter(JdbcInputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private String id;
	private Boolean useLastExecutionRecords;
	private JdbcConnectionModel connection;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	public JdbcInputConfig() {
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		
		this.exceptionHandlers = new ArrayList<>();
		this.dataReadingListeners = new ArrayList<>();
		this.inputExecutionListeners = new ArrayList<>();
		this.matcher = new SingleLineMatcher();
	}

	@Override
	public JdbcInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}

	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
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
	public String id() {
		return InputConfigTypes.JDBC.name();
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
	public JdbcInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	@Override
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

	@Override
	public JdbcInputConfig withMatcher(IMatcher matcher) {
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
	public JdbcInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@Override
	public JdbcInputConfig addExceptionHandler(IExceptionHandler handler) {
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
		return exceptionHandlers;
	}

	@Override
	@SchemaProperty(
		property = "dataReadingListeners",
		types = "array",
		description = "Fully qualified name of the class that will listen and fire an event before and after a log event is read.",
		required = false
	)
	public List<DataReadingListener> getDataReadingListeners() {
		return this.dataReadingListeners;
	}

	@Override
	public JdbcInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}

	@Override
	public JdbcInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
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
	public JdbcInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public JdbcInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}
	
	@NotNull
	@Valid
	@SchemaProperty(
		property = "connection",
		types = "object",
		description = "JDBC attributes.",
		required = true,
		reference = JdbcConnectionModel.class
	)
	public JdbcConnectionModel getConnection() {
		return connection;
	}
	
	public JdbcInputConfig withConnection(JdbcConnectionModel connection) {
		this.connection = connection;
		return this;
	}

	@Override
	public JdbcInputConfig clone() {
		return new JdbcInputConfig()
			.withConfigurationId(this.id)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers)
			.withMatcher(this.matcher)
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners)
			.withUseLastExecutionRecords(this.useLastExecutionRecords)
			.withConnection((this.connection == null) ? null : this.connection.clone());
	}
	
	protected JdbcInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected JdbcInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}
}