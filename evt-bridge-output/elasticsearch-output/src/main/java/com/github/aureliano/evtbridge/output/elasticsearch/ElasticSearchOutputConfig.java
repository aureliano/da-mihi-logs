package com.github.aureliano.evtbridge.output.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
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
	title = "Output configuration to save data onto ElasticSearch.",
	type = "object"
)
public class ElasticSearchOutputConfig implements IConfigOutput, IElasticSearchConfiguration {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(OutputConfigTypes.ELASTIC_SEARCH.name())
			.withAgent(ElasticSearchDataWriter.class)
			.withConfiguration(ElasticSearchOutputConfig.class)
			.withConverter(ElasticSearchOutputConverter.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private String host;
	private int port;
	private boolean printElasticSearchLog;
	private String index;
	private String type;
	
	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	public ElasticSearchOutputConfig() {
		this.host = DEFAULT_ELASTIC_SEARCH_HOST;
		this.port = DEFAULT_ELASTIC_SEARCH_PORT;
		this.printElasticSearchLog = DEFAULT_PRINT_ELASTIC_SEARCH_LOG;
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
		
		this.parser = new PlainTextParser();
		this.filter = new EmptyFilter();
		this.outputFormatter = new PlainTextFormatter();
	}

	@Override
	@SchemaProperty(
		property = "host",
		types = "string",
		description = "ElasticSearch server host.",
		required = false,
		defaultValue = "localhost"
	)
	public String getHost() {
		return host;
	}

	@Override
	public ElasticSearchOutputConfig withHost(String host) {
		this.host = host;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "port",
		types = "integer",
		description = "ElasticSearch connection port.",
		required = false,
		defaultValue = "9200"
	)
	public int getPort() {
		return port;
	}

	@Override
	public ElasticSearchOutputConfig withPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	@NotEmpty
	@SchemaProperty(
		property = "index",
		types = "string",
		description = "The ElasticSearch index name.",
		required = true
	)
	public String getIndex() {
		return index;
	}

	@Override
	public ElasticSearchOutputConfig withIndex(String index) {
		this.index = index;
		return this;
	}

	@Override
	@NotEmpty
	@SchemaProperty(
		property = "mappingType",
		types = "string",
		description = "The index's mapping type.",
		required = true
	)
	public String getMappingType() {
		return type;
	}

	@Override
	public ElasticSearchOutputConfig withMappingType(String type) {
		this.type = type;
		return this;
	}

	@Override
	@SchemaProperty(
		property = "printElasticSearchLog",
		types = "boolean",
		description = "Whether to print ElasticSearch log or not.",
		required = false,
		defaultValue = "false"
	)
	public boolean isPrintElasticSearchLog() {
		return this.printElasticSearchLog;
	}

	@Override
	public ElasticSearchOutputConfig withPrintElasticSearchLog(boolean printElasticSearchLog) {
		this.printElasticSearchLog = printElasticSearchLog;
		return this;
	}

	@Override
	public String id() {
		return OutputConfigTypes.ELASTIC_SEARCH.name();
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
	public ElasticSearchOutputConfig withParser(IParser<?> parser) {
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
	public ElasticSearchOutputConfig withFilter(IEventFielter filter) {
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
	public ElasticSearchOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
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
	
	public ElasticSearchOutputConfig withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}
	
	public ElasticSearchOutputConfig addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}
	
	@Override
	public ElasticSearchOutputConfig clone() {
		return (ElasticSearchOutputConfig) new ElasticSearchOutputConfig()
			.withHost(this.host)
			.withIndex(this.index)
			.withMappingType(this.type)
			.withPort(this.port)
			.withPrintElasticSearchLog(this.printElasticSearchLog)
			.withParser(this.parser)
			.withFilter(this.filter)
			.withOutputFormatter(this.outputFormatter)
			.withDataWritingListeners(this.dataWritingListeners)
			.withMetadata(DataHelper.copyProperties(this.metadata));
	}
	
	@Override
	public ElasticSearchOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected ElasticSearchOutputConfig withMetadata(Properties properties) {
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