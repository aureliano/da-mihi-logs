package com.github.aureliano.damihilogs.config.output;

import java.util.Properties;

import com.github.aureliano.damihilogs.es.IElasticSearchConfiguration;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.parser.IParser;

public class ElasticSearchOutputConfig implements IConfigOutput, IElasticSearchConfiguration {

	private String host;
	private int port;
	private boolean printElasticSearchLog;
	private String index;
	private String type;
	
	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	private IOutputFormatter outputFormatter;
	
	public ElasticSearchOutputConfig() {
		this.host = DEFAULT_ELASTIC_SEARCH_HOST;
		this.port = DEFAULT_ELASTIC_SEARCH_PORT;
		this.printElasticSearchLog = DEFAULT_PRINT_ELASTIC_SEARCH_LOG;
		this.metadata = new Properties();
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public ElasticSearchOutputConfig withHost(String host) {
		this.host = host;
		return this;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public ElasticSearchOutputConfig withPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	public String getIndex() {
		return index;
	}

	@Override
	public ElasticSearchOutputConfig withIndex(String index) {
		this.index = index;
		return this;
	}

	@Override
	public String getMappingType() {
		return type;
	}

	@Override
	public ElasticSearchOutputConfig withMappingType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public boolean isPrintElasticSearchLog() {
		return this.printElasticSearchLog;
	}

	@Override
	public ElasticSearchOutputConfig withPrintElasticSearchLog(boolean printElasticSearchLog) {
		this.printElasticSearchLog = printElasticSearchLog;
		return this;
	}

	@Override
	public String outputType() {
		return "ELASTIC_SEARCH";
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public ElasticSearchOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public ElasticSearchOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}

	@Override
	public ElasticSearchOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
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
	public Properties getMetadata() {
		return this.metadata;
	}
}