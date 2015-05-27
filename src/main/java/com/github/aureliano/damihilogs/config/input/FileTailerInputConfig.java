package com.github.aureliano.damihilogs.config.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class FileTailerInputConfig implements IConfigInput {

	private File file;
	private String encoding;
	private Long tailDelay;
	private Long tailInterval;
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
	public FileTailerInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public FileTailerInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

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
	
	public String getEncoding() {
		return encoding;
	}
	
	public FileTailerInputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	public Long getTailDelay() {
		return tailDelay;
	}
	
	public FileTailerInputConfig withTailDelay(Long tailDelay) {
		this.tailDelay = tailDelay;
		return this;
	}
	
	public Long getTailInterval() {
		return tailInterval;
	}
	
	public FileTailerInputConfig withTailInterval(Long tailInterval) {
		this.tailInterval = tailInterval;
		return this;
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public FileTailerInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

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
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public FileTailerInputConfig addExceptionHandler(IExceptionHandler handler) {
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
	public FileTailerInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public FileTailerInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}
}