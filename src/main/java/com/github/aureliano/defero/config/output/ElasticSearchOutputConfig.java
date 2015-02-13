package com.github.aureliano.defero.config.output;

import java.io.File;

import com.github.aureliano.defero.es.IElasticSearchConfiguration;

public class ElasticSearchOutputConfig implements IConfigOutput, IElasticSearchConfiguration {

	private String host;
	private int port;
	private int transportClientPort;
	private boolean printElasticSearchLog;
	private String index;
	private String type;
	private File configProperties;
	
	public ElasticSearchOutputConfig() {
		this.host = DEFAULT_ELASTIC_SEARCH_HOST;
		this.port = DEFAULT_ELASTIC_SEARCH_PORT;
		this.transportClientPort = DEFAULT_TRANSPORT_CLIENT_PORT;
		this.printElasticSearchLog = DEFAULT_PRINT_ELASTIC_SEARCH_LOG;
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
	public int getTransportClientPort() {
		return transportClientPort;
	}

	@Override
	public ElasticSearchOutputConfig withTransportClientPort(int transportClientPort) {
		this.transportClientPort = transportClientPort;
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
	public File getConfigProperties() {
		return this.configProperties;
	}

	@Override
	public IElasticSearchConfiguration withConfigProperties(File configProperties) {
		this.configProperties = configProperties;
		return this;
	}

	@Override
	public String outputType() {
		return "ELASTIC_SEARCH";
	}
}