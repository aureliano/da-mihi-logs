package com.github.aureliano.damihilogs.config.output;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public class FileOutputConfig implements IConfigOutput {

	private File file;
	private boolean append;
	private String encoding;
	private boolean useBuffer;
	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	public FileOutputConfig() {
		this.append = false;
		this.useBuffer = true;
		this.encoding = "UTF-8";
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
	}

	@Override
	public String outputType() {
		return "FILE";
	}

	public File getFile() {
		return file;
	}

	public FileOutputConfig withFile(File file) {
		this.file = file;
		return this;
	}
	
	public FileOutputConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}

	public boolean isAppend() {
		return append;
	}

	public FileOutputConfig withAppend(boolean append) {
		this.append = append;
		return this;
	}

	public String getEncoding() {
		return encoding;
	}

	public FileOutputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	public boolean isUseBuffer() {
		return useBuffer;
	}
	
	public FileOutputConfig withUseBuffer(boolean useBuffer) {
		this.useBuffer = useBuffer;
		return this;
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public FileOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public FileOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}

	@Override
	public FileOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}
	
	public List<DataWritingListener> getDataWritingListeners() {
		return dataWritingListeners;
	}
	
	public FileOutputConfig withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}
	
	public FileOutputConfig addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}
	
	public FileOutputConfig clone() {
		return new FileOutputConfig()
			.withAppend(this.append)
			.withEncoding(this.encoding)
			.withUseBuffer(this.useBuffer)
			.withFile(this.file)
			.withParser(this.parser)
			.withFilter(this.filter)
			.withOutputFormatter(this.outputFormatter)
			.withDataWritingListeners(this.dataWritingListeners)
			.withMetadata(DataHelper.copyProperties(this.metadata));
	}
	
	@Override
	public FileOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected FileOutputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}