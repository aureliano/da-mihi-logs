package com.github.aureliano.evtbridge.output.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.core.config.IConfigOutput;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.filter.EmptyFilter;
import com.github.aureliano.evtbridge.core.filter.IEventFielter;
import com.github.aureliano.evtbridge.core.formatter.IOutputFormatter;
import com.github.aureliano.evtbridge.core.formatter.PlainTextFormatter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;
import com.github.aureliano.evtbridge.core.parser.IParser;
import com.github.aureliano.evtbridge.core.parser.PlainTextParser;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.register.ServiceRegistration;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Output configuration where events will be writen as plain text file.",
	type = "object"
)
public class FileOutputConfig implements IConfigOutput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(OutputConfigTypes.FILE.name())
			.withAgent(FileDataWriter.class)
			.withConfiguration(FileOutputConfig.class)
			.withConverter(FileOutputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
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
		this.dataWritingListeners = new ArrayList<>();
		
		this.parser = new PlainTextParser();
		this.filter = new EmptyFilter();
		this.outputFormatter = new PlainTextFormatter();
	}

	@Override
	public String id() {
		return OutputConfigTypes.FILE.name();
	}

	@NotNull
	@SchemaProperty(
		property = "file",
		types = "string",
		description = "The output file path where data will be saved.",
		required = true
	)
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

	@SchemaProperty(
		property = "append",
		types = "boolean",
		description = "Whether to append content to output file or not.",
		required = false,
		defaultValue = "false"
	)
	public boolean isAppend() {
		return append;
	}

	public FileOutputConfig withAppend(boolean append) {
		this.append = append;
		return this;
	}

	@SchemaProperty(
		property = "encoding",
		types = "string",
		description = "Force encoding for writing.",
		required = false,
		defaultValue = "UTF-8"
	)
	public String getEncoding() {
		return encoding;
	}

	public FileOutputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	@SchemaProperty(
		property = "useBuffer",
		types = "boolean",
		description = "Whether to use buffering in order to improve writing process. You might need some times to write data to file instantly though.",
		required = false,
		defaultValue = "true"
	)
	public boolean isUseBuffer() {
		return useBuffer;
	}
	
	public FileOutputConfig withUseBuffer(boolean useBuffer) {
		this.useBuffer = useBuffer;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "parser",
		types = "string",
		description = "Fully qualified name of parser class used to convert an event into an business object.",
		required = false,
		defaultValue = "PlainTextParser"
	)
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public FileOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "filter",
		types = "string",
		description = "Fully qualified name of filter class used to filter events before writing.",
		required = false,
		defaultValue = "EmptyFilter"
	)
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public FileOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "outputFormatter",
		types = "string",
		description = "Fully qualified name of formatter class used to format data output.",
		required = false,
		defaultValue = "PlainTextFormatter"
	)
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}

	@Override
	public FileOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}

	@SchemaProperty(
		property = "dataWritingListeners",
		types = "array",
		description = "Fully qualified name of the class that will listen and fire an event before and after a log event is writen.",
		required = false
	)
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
	@SchemaProperty(
		property = "metadata",
		types = "object",
		description = "A key-value pair (hash <string, string>) which provides a mechanism to exchange metadata between configurations (main, inputs and outputs).",
		required = false
	)
	public Properties getMetadata() {
		return this.metadata;
	}
}