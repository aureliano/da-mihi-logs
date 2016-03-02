package com.github.aureliano.evtbridge.input.file_tailer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.annotation.validation.Min;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;
import com.github.aureliano.evtbridge.core.listener.ExecutionListener;
import com.github.aureliano.evtbridge.core.matcher.IMatcher;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.register.ServiceRegistration;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Input configuration to watch (like Linux tail application) and consume from plain text file.",
	type = "object"
)
public class FileTailerInputConfig implements IConfigInput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(InputConfigTypes.FILE_TAILER.name())
			.withAgent(FileTailerDataReader.class)
			.withConfiguration(FileTailerInputConfig.class)
			.withConverter(FileTailerInputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private File file;
	private String encoding;
	private Long tailDelay;
	private Long tailInterval;
	private TimeUnit timeUnit;
	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	public FileTailerInputConfig() {
		this.encoding = "UTF-8";
		this.tailDelay = 1000L;
		this.timeUnit = TimeUnit.MILLISECONDS;
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<>();
		
		this.dataReadingListeners = new ArrayList<>();
		this.inputExecutionListeners = new ArrayList<>();
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
	public FileTailerInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	@SchemaProperty(
		property = "matcher",
		types = "string",
		description = "Fully qualified name of class matcher used to get text from input.",
		required = false
	)
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public FileTailerInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	@NotNull
	@SchemaProperty(
		property = "file",
		types = "string",
		description = "The input file path where data will be read from.",
		required = true
	)
	public File getFile() {
		return file;
	}

	public FileTailerInputConfig withFile(File file) {
		this.file = file;
		return this;
	}

	public FileTailerInputConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}

	@SchemaProperty(
		property = "encoding",
		types = "string",
		description = "Force encoding for reading.",
		required = false,
		defaultValue = "UTF-8"
	)
	public String getEncoding() {
		return encoding;
	}
	
	public FileTailerInputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	@NotNull
	@Min(value = 0)
	@SchemaProperty(
		property = "tailDelay",
		types = "integer",
		description = "Time in milliseconds to wait for the next file changing verification.",
		required = true,
		defaultValue = "1000"
	)
	public Long getTailDelay() {
		return tailDelay;
	}
	
	public FileTailerInputConfig withTailDelay(Long tailDelay) {
		this.tailDelay = tailDelay;
		return this;
	}
	
	@Min(value = 0)
	@SchemaProperty(
		property = "tailInterval",
		types = "integer",
		description = "Time in milliseconds to tail (watch for changes) a file. Null means forever.",
		required = false
	)
	public Long getTailInterval() {
		return tailInterval;
	}
	
	public FileTailerInputConfig withTailInterval(Long tailInterval) {
		this.tailInterval = tailInterval;
		return this;
	}
	
	@SchemaProperty(
		property = "timeUnit",
		types = { "days", "hours", "minutes", "seconds", "milliseconds" },
		description = "The time unit of the tailDelay and tailInterval parameters.",
		required = false,
		defaultValue = "milliseconds"
	)
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	
	public FileTailerInputConfig withTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
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
	public FileTailerInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
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

	public FileTailerInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public FileTailerInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}
	
	@Override
	public FileTailerInputConfig clone() {
		return new FileTailerInputConfig()
			.withEncoding(this.encoding)
			.withFile(this.file)
			.withTailDelay(this.tailDelay)
			.withTailInterval(this.tailInterval)
			.withTimeUnit(this.timeUnit)
			.withConfigurationId(this.id)
			.withMatcher(this.matcher)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers)
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners);
	}
	
	@Override
	public FileTailerInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected FileTailerInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected FileTailerInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
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
	public FileTailerInputConfig addExceptionHandler(IExceptionHandler handler) {
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
	public FileTailerInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public FileTailerInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}

	@Override
	public String id() {
		return InputConfigTypes.FILE_TAILER.name();
	}
}