package com.github.aureliano.damihilogs.config.input;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.inout.CompressMetadata;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class UrlInputConfig implements IConfigInput {

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
	private Boolean alwaysDownloadCompleteFile;
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
	
	private CompressMetadata decompressFileConfiguration;
	
	public UrlInputConfig() {
		this.connectionSchema = ConnectionSchema.HTTP;
		this.port = -1;
		this.parameters = new LinkedHashMap<String, String>();
		this.readTimeout = DEFAULT_READ_TIMEOUT;
		this.byteOffSet = 0;
		this.appendIfOutputFileExist = false;
		this.alwaysDownloadCompleteFile = false;
		this.noCheckCertificate = false;
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<IExceptionHandler>();
		
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.inputExecutionListeners = new ArrayList<ExecutionListener>();
	}

	public ConnectionSchema getConnectionSchema() {
		return connectionSchema;
	}

	public UrlInputConfig withConnectionSchema(ConnectionSchema connectionSchema) {
		this.connectionSchema = connectionSchema;
		return this;
	}

	public String getHost() {
		return host;
	}

	public UrlInputConfig withHost(String host) {
		this.host = host;
		return this;
	}
	
	public int getPort() {
		return port;
	}
	
	public UrlInputConfig withPort(Integer port) {
		this.port = port;
		return this;
	}

	public String getPath() {
		return path;
	}

	public UrlInputConfig withPath(String path) {
		this.path = path;
		return this;
	}

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

	public Long getReadTimeout() {
		return readTimeout;
	}

	public UrlInputConfig withReadTimeout(Long readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}
	
	public Integer getByteOffSet() {
		return byteOffSet;
	}
	
	public UrlInputConfig withByteOffSet(Integer byteOffSet) {
		this.byteOffSet = byteOffSet;
		return this;
	}
	
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
	
	public Boolean isAppendIfOutputFileExist() {
		return appendIfOutputFileExist;
	}
	
	public UrlInputConfig withAppendIfOutputFileExist(Boolean appendIfOutputFileExist) {
		this.appendIfOutputFileExist = appendIfOutputFileExist;
		return this;
	}
	
	public UrlInputConfig withAlwaysDownloadCompleteFile(Boolean alwaysDownloadCompleteFile) {
		this.alwaysDownloadCompleteFile = alwaysDownloadCompleteFile;
		return this;
	}
	
	public Boolean isAlwaysDownloadCompleteFile() {
		return alwaysDownloadCompleteFile;
	}
	
	public Integer getFileStartPosition() {
		return fileStartPosition;
	}
	
	public UrlInputConfig withFileStartPosition(Integer fileStartPosition) {
		this.fileStartPosition = fileStartPosition;
		return this;
	}

	public String getUser() {
		return user;
	}

	public UrlInputConfig withUser(String user) {
		this.user = user;
		return this;
	}

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
	public String getConfigurationId() {
		return id;
	}
	
	@Override
	public UrlInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public UrlInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public UrlInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

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
	
	public CompressMetadata getDecompressFileConfiguration() {
		return decompressFileConfiguration;
	}
	
	public UrlInputConfig withDecompressFileConfiguration(CompressMetadata decompressFileConfiguration) {
		this.decompressFileConfiguration = decompressFileConfiguration;
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
			.withExecutionListeners(this.inputExecutionListeners)
			.withDecompressFileConfiguration(this.decompressFileConfiguration);
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
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public UrlInputConfig addExceptionHandler(IExceptionHandler handler) {
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
	public UrlInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public UrlInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}
}