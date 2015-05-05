package com.github.aureliano.damihilogs.config.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.matcher.IMatcher;
import com.github.aureliano.damihilogs.matcher.SingleLineMatcher;

public class InputFileConfig implements IConfigInput {

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

	public InputFileConfig() {
		this.encoding = "UTF-8";
		this.tailFile = false;
		this.tailDelay = 1000L;
		this.tailInterval = 0L;
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		this.exceptionHandlers = new ArrayList<IExceptionHandler>();
		this.matcher = new SingleLineMatcher();
	}
	
	@Override
	public String getConfigurationId() {
		return id;
	}
	
	@Override
	public InputFileConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public InputFileConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	public File getFile() {
		return file;
	}

	public InputFileConfig withFile(File file) {
		this.file = file;
		return this;
	}

	public InputFileConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}
	
	public Integer getStartPosition() {
		return startPosition;
	}
	
	public InputFileConfig withStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
		return this;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public InputFileConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public Boolean isTailFile() {
		return this.tailFile;
	}
	
	public InputFileConfig withTailFile(Boolean tailFile) {
		this.tailFile = tailFile;
		return this;
	}
	
	public Long getTailDelay() {
		return tailDelay;
	}
	
	public InputFileConfig withTailDelay(Long tailDelay) {
		this.tailDelay = tailDelay;
		return this;
	}
	
	public Long getTailInterval() {
		return tailInterval;
	}
	
	public InputFileConfig withTailInterval(Long tailInterval) {
		this.tailInterval = tailInterval;
		return this;
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public InputFileConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}
	
	@Override
	public InputFileConfig clone() {
		return new InputFileConfig()
			.withEncoding(this.encoding)
			.withFile(this.file)
			.withStartPosition(this.startPosition)
			.withTailDelay(this.tailDelay)
			.withTailFile(this.tailFile)
			.withTailInterval(this.tailInterval)
			.withConfigurationId(this.id)
			.withMatcher(this.matcher)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withExceptionHandlers(this.exceptionHandlers);
	}
	
	@Override
	public InputFileConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected InputFileConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected InputFileConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public InputFileConfig addExceptionHandler(IExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
		return this;
	}

	@Override
	public List<IExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}
}