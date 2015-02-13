package com.github.aureliano.defero.es;

public interface IElasticSearchConfiguration {
	
	public static final String DEFAULT_ELASTIC_SEARCH_HOST = "127.0.0.1";
	public static final int DEFAULT_ELASTIC_SEARCH_PORT = 9200;
	public static final int DEFAULT_TRANSPORT_CLIENT_PORT = 9300;
	public static final boolean DEFAULT_PRINT_ELASTIC_SEARCH_LOG = false;

	public abstract String getHost();

	public abstract IElasticSearchConfiguration withHost(String host);

	public abstract int getPort();

	public abstract IElasticSearchConfiguration withPort(int port);
	
	public abstract int getTransportClientPort();
	
	public abstract IElasticSearchConfiguration withTransportClientPort(int transportClientPort);
	
	public abstract String getIndex();

	public abstract IElasticSearchConfiguration withIndex(String index);

	public abstract String getMappingType();

	public abstract IElasticSearchConfiguration withMappingType(String type);
	
	public abstract boolean isPrintElasticSearchLog();
	
	public abstract IElasticSearchConfiguration withPrintElasticSearchLog(boolean printElasticSearchLog);
}