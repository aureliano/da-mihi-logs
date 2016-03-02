package com.github.aureliano.evtbridge.input.url;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.core.config.ConnectionSchema;
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
	title = "Input configuration to consume from downloaded plain text file.",
	type = "object"
)
public class UrlInputConfig implements IConfigInput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(InputConfigTypes.URL.name())
			.withAgent(UrlDataReader.class)
			.withConfiguration(UrlInputConfig.class)
			.withConverter(UrlInputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	public static final long DEFAULT_READ_TIMEOUT = 30 * 1000;
	
	private ConnectionSchema connectionSchema;
	private String host;
	private Integer port;
	private String path;
	private Map<String, String> parameters;
	private Long readTimeout;
	private Integer byteOffSet;
	
	private File outputFile;
	private Boolean appendIfOutputFileExist;
	private Integer fileStartPosition;
	
	private String user;
	private String password;
	private Boolean noCheckCertificate;

	private String id;

	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;

	private IMatcher matcher;

	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	public UrlInputConfig() {
		this.connectionSchema = ConnectionSchema.HTTP;
		this.port = -1;
		this.parameters = new LinkedHashMap<>();
		this.readTimeout = DEFAULT_READ_TIMEOUT;
		this.byteOffSet = 0;
		this.appendIfOutputFileExist = false;
		this.noCheckCertificate = false;
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<>();
		
		this.dataReadingListeners = new ArrayList<>();
		this.inputExecutionListeners = new ArrayList<>();
	}

	@NotNull
	@SchemaProperty(
		property = "connectionSchema",
		types = { "http", "https" },
		description = "The HTTP connection schema.",
		required = true,
		defaultValue = "http"
	)
	public ConnectionSchema getConnectionSchema() {
		return connectionSchema;
	}

	public UrlInputConfig withConnectionSchema(ConnectionSchema connectionSchema) {
		this.connectionSchema = connectionSchema;
		return this;
	}

	@NotEmpty
	@SchemaProperty(
		property = "host",
		types = "string",
		description = "The server host name to establish connection.",
		required = true
	)
	public String getHost() {
		return host;
	}

	public UrlInputConfig withHost(String host) {
		this.host = host;
		return this;
	}

	@SchemaProperty(
		property = "port",
		types = "integer",
		description = "The port number.",
		required = false
	)
	public int getPort() {
		return port;
	}
	
	public UrlInputConfig withPort(Integer port) {
		this.port = port;
		return this;
	}
	
	@NotEmpty
	@SchemaProperty(
		property = "path",
		types = "string",
		description = "The resource path . Example: foo/bar/download.log",
		required = true
	)
	public String getPath() {
		return path;
	}

	public UrlInputConfig withPath(String path) {
		this.path = path;
		return this;
	}

	@SchemaProperty(
		property = "parameters",
		types = "object",
		description = "URL parameters. A hash map with URL parameters where key is the parameter name and value is the value itself.",
		required = false
	)
	public Map<String, String> getParameters() {
		return parameters;
	}

	public UrlInputConfig withParameters(Map<String, String> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	public UrlInputConfig addParameter(String key, String value) {
		this.parameters.put(key, value);
		return this;
	}

	@SchemaProperty(
		property = "readTimeout",
		types = "integer",
		description = "The time in milliseconds to wait for connection stablishment before quiting.",
		required = false,
		defaultValue = "30000"
	)
	public Long getReadTimeout() {
		return readTimeout;
	}

	public UrlInputConfig withReadTimeout(Long readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	@SchemaProperty(
		property = "byteOffSet",
		types = "integer",
		description = "The byte off set to download. That means, download partial file.",
		required = false,
		defaultValue = "0"
	)
	public Integer getByteOffSet() {
		return byteOffSet;
	}
	
	public UrlInputConfig withByteOffSet(Integer byteOffSet) {
		this.byteOffSet = byteOffSet;
		return this;
	}
	
	@NotNull
	@SchemaProperty(
		property = "outputFile",
		types = "string",
		description = "The path where download data will be saved and read afterwards.",
		required = true
	)
	public File getOutputFile() {
		return outputFile;
	}
	
	public UrlInputConfig withOutputFile(File outputFile) {
		this.outputFile = outputFile;
		return this;
	}
	
	public UrlInputConfig withOutputFile(String outputFilePath) {
		this.outputFile = new File(outputFilePath);
		return this;
	}

	@SchemaProperty(
		property = "appendIfOutputFileExist",
		types = "boolean",
		description = "Append downloaded bytes to file if already exists.",
		required = false,
		defaultValue = "false"
	)
	public Boolean isAppendIfOutputFileExist() {
		return appendIfOutputFileExist;
	}
	
	public UrlInputConfig withAppendIfOutputFileExist(Boolean appendIfOutputFileExist) {
		this.appendIfOutputFileExist = appendIfOutputFileExist;
		return this;
	}

	@SchemaProperty(
		property = "fileStartPosition",
		types = "integer",
		description = "The line number of the file where reading will start from.",
		required = false
	)
	public Integer getFileStartPosition() {
		return fileStartPosition;
	}
	
	public UrlInputConfig withFileStartPosition(Integer fileStartPosition) {
		this.fileStartPosition = fileStartPosition;
		return this;
	}

	@SchemaProperty(
		property = "user",
		types = "string",
		description = "The user name in order to authenticate on server.",
		required = false
	)
	public String getUser() {
		return user;
	}

	public UrlInputConfig withUser(String user) {
		this.user = user;
		return this;
	}

	@SchemaProperty(
		property = "password",
		types = "string",
		description = "The password in order to authenticate on server.",
		required = false
	)
	public String getPassword() {
		return password;
	}

	public UrlInputConfig withPassword(String password) {
		this.password = password;
		return this;
	}

	public Boolean isNoCheckCertificate() {
		return noCheckCertificate;
	}

	public UrlInputConfig withNoCheckCertificate(Boolean noCheckCertificate) {
		this.noCheckCertificate = noCheckCertificate;
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
	public UrlInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "matcher",
		types = "string",
		description = "Fully qualified name of class matcher used to get text from input.",
		required = false
	)
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public UrlInputConfig withMatcher(IMatcher matcher) {
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
	public UrlInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
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

	public UrlInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public UrlInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}
	
	@Override
	public UrlInputConfig clone() {
		return new UrlInputConfig()
			.withByteOffSet(this.byteOffSet)
			.withConnectionSchema(this.connectionSchema)
			.withFileStartPosition(this.fileStartPosition)
			.withHost(this.host)
			.withNoCheckCertificate(this.noCheckCertificate)
			.withOutputFile(this.outputFile)
			.withParameters(this.parameters)
			.withPassword(this.password)
			.withPath(this.path)
			.withPort(this.port)
			.withReadTimeout(this.readTimeout)
			.withUser(this.user)
			.withAppendIfOutputFileExist(this.appendIfOutputFileExist)
			.withConfigurationId(this.id)
			.withMatcher(this.matcher)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withDataReadingListeners(this.dataReadingListeners)
			.withExceptionHandlers(this.exceptionHandlers)
			.withExecutionListeners(this.inputExecutionListeners);
	}
	
	@Override
	public UrlInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected UrlInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected UrlInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
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
	public UrlInputConfig addExceptionHandler(IExceptionHandler handler) {
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
	public UrlInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public UrlInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}

	@Override
	public String id() {
		return InputConfigTypes.URL.name();
	}
}