package com.github.aureliano.evtbridge.output.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
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
	title = "Output configuration to print events to standard output (stdout).",
	type = "object"
)
public class StandardOutputConfig implements IConfigOutput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(OutputConfigTypes.STANDARD.name())
			.withAgent(StandardDataWriter.class)
			.withConfiguration(StandardOutputConfig.class)
			.withConverter(StandardOutputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	public StandardOutputConfig() {
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<>();
		
		this.parser = new PlainTextParser();
		this.filter = new EmptyFilter();
		this.outputFormatter = new PlainTextFormatter();
	}

	@Override
	public String id() {
		return OutputConfigTypes.STANDARD.name();
	}

	@Override
	@SchemaProperty(
		property = "parser",
		types = "string",
		description = "Fully qualified name of parser class used to convert an event into an business object.",
		required = false,
		defaultValue = "com.github.aureliano.evtbridge.core.parser.PlainTextParser"
	)
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public StandardOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "filter",
		types = "string",
		description = "Fully qualified name of filter class used to filter events before writing.",
		required = false,
		defaultValue = "com.github.aureliano.evtbridge.core.filter.EmptyFilter"
	)
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public StandardOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "outputFormatter",
		types = "string",
		description = "Fully qualified name of formatter class used to format data output.",
		required = false,
		defaultValue = "com.github.aureliano.evtbridge.core.formatter.PlainTextFormatter"
	)
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}

	@Override
	public StandardOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
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
	
	public StandardOutputConfig withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}
	
	public StandardOutputConfig addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}
	
	public StandardOutputConfig clone() {
		return new StandardOutputConfig()
			.withParser(this.parser)
			.withFilter(this.filter)
			.withOutputFormatter(this.outputFormatter)
			.withDataWritingListeners(this.dataWritingListeners)
			.withMetadata(DataHelper.copyProperties(this.metadata));
	}
	
	@Override
	public StandardOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected StandardOutputConfig withMetadata(Properties properties) {
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