package com.github.aureliano.damihilogs.config.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.inout.CompressMetadata;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class FileInputConfig implements IConfigInput {

	private File file;
	private Integer startPosition;
	private String encoding;
	private Boolean tailFile;
	private Long tailDelay;
	private Long tailInterval;
	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private List<IExceptionHandler> exceptionHandlers;
	private IMatcher matcher;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	private CompressMetadata decompressFileConfiguration;

	public FileInputConfig() {
		this.encoding = "UTF-8";
		this.tailFile = false;
		this.tailDelay = 1000L;
		this.tailInterval = 0L;
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<IExceptionHandler>();
		
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.inputExecutionListeners = new ArrayList<ExecutionListener>();
	}
	
	@Override
	public String getConfigurationId() {
		return id;
	}
	
	@Override
	public FileInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public FileInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	public File getFile() {
		return file;
	}

	public FileInputConfig withFile(File file) {
		this.file = file;
		return this;
	}

	public FileInputConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}
	
	public Integer getStartPosition() {
		return startPosition;
	}
	
	public FileInputConfig withStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
		return this;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public FileInputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public Boolean isTailFile() {
		return this.tailFile;
	}
	
	public FileInputConfig withTailFile(Boolean tailFile) {
		this.tailFile = tailFile;
		return this;
	}
	
	public Long getTailDelay() {
		return tailDelay;
	}
	
	public FileInputConfig withTailDelay(Long tailDelay) {
		this.tailDelay = tailDelay;
		return this;
	}
	
	public Long getTailInterval() {
		return tailInterval;
	}
	
	public FileInputConfig withTailInterval(Long tailInterval) {
		this.tailInterval = tailInterval;
		return this;
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public FileInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	public List<DataReadingListener> getDataReadingListeners() {
		return dataReadingListeners;
	}

	public FileInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public FileInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}
	
	public CompressMetadata getDecompressFileConfiguration() {
		return decompressFileConfiguration;
	}
	
	public FileInputConfig withDecompressFileConfiguration(CompressMetadata decompressFileConfiguration) {
		this.decompressFileConfiguration = decompressFileConfiguration;
		return this;
	}
	
	@Override
	public FileInputConfig clone() {
		return new FileInputConfig()
			.withEncoding(this.encoding)
			.withFile(this.file)
			.withStartPosition(this.startPosition)
			.withTailDelay(this.tailDelay)
			.withTailFile(this.tailFile)
			.withTailInterval(this.tailInterval)
			.withConfigurationId(this.id)
			.withMatcher(this.matcher)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers)
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners)
			.withDecompressFileConfiguration(this.decompressFileConfiguration);
	}
	
	@Override
	public FileInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected FileInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected FileInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public FileInputConfig addExceptionHandler(IExceptionHandler handler) {
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
	public FileInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public FileInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}
}