package com.github.aureliano.damihilogs.config.input;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class UrlInputConfig implements IConfigInput {

	public static final long DEFAULT_READ_TIMEOUT = 30 * 1000;
	
	private ConnectionSchema connectionSchema;
	private String host;
	private int port;
	private String path;
	private Map<String, String> parameters;
	private long readTimeout;
	private int byteOffSet;
	
	private File outputFile;
	private boolean appendIfOutputFileExist;
	private int fileStartPosition;
	
	private String user;
	private String password;
	private boolean noCheckCertificate;

	private String id;

	private boolean useLastExecutionRecords;
	private Properties metadata;
	
	public UrlInputConfig() {
		this.connectionSchema = ConnectionSchema.HTTP;
		this.port = -1;
		this.parameters = new LinkedHashMap<String, String>();
		this.readTimeout = DEFAULT_READ_TIMEOUT;
		this.byteOffSet = 0;
		this.appendIfOutputFileExist = false;
		this.fileStartPosition = 0;
		this.noCheckCertificate = false;
		this.metadata = new Properties();
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
	
	public UrlInputConfig withPort(int port) {
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

	public long getReadTimeout() {
		return readTimeout;
	}

	public UrlInputConfig withReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}
	
	public int getByteOffSet() {
		return byteOffSet;
	}
	
	public UrlInputConfig withByteOffSet(int byteOffSet) {
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
	
	public boolean isAppendIfOutputFileExist() {
		return appendIfOutputFileExist;
	}
	
	public UrlInputConfig withAppendIfOutputFileExist(boolean appendIfOutputFileExist) {
		this.appendIfOutputFileExist = appendIfOutputFileExist;
		return this;
	}
	
	public int getFileStartPosition() {
		return fileStartPosition;
	}
	
	public UrlInputConfig withFileStartPosition(int fileStartPosition) {
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

	public boolean isNoCheckCertificate() {
		return noCheckCertificate;
	}

	public UrlInputConfig withNoCheckCertificate(boolean noCheckCertificate) {
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
	
	@Override
	public boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public UrlInputConfig withUseLastExecutionRecords(boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
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
			.withMetadata(this.metadata);
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

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}