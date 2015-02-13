package com.github.aureliano.defero.es;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.github.aureliano.defero.exception.DeferoException;
import com.github.kzwang.osem.annotations.Indexable;
import com.github.kzwang.osem.impl.ElasticSearchIndexerImpl;

public class ElasticSearchClient {

	private Client client;
	private IElasticSearchConfiguration configuration;
	private ElasticSearchIndexerImpl indexer;
	
	private static final Logger logger = Logger.getLogger(ElasticSearchClient.class.getName());
	
	public ElasticSearchClient() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public void index(Object data) {
		if (data instanceof Map) {
			this.indexSource((Map<String, Object>) data);
		} else if (data instanceof String) {
			this.indexSource(data.toString());
		} else {
			Indexable indexable = data.getClass().getAnnotation(Indexable.class);
			if (indexable == null) {
				throw new DeferoException("Don't know how to index object. Expected [java.util.Map, java.lang.String, com.github.kzwang.osem.annotations.@Indexable] but found " + data.getClass().getName());
			}
			this.indexIndexable(data);
		}
	}
	
	private void indexIndexable(Object indexable) {
		IndexResponse response = this.indexer.index(indexable);
		this.printIndexDocumentResponseLog(response);
	}
	
	private void indexSource(Map<String, Object> data) {
		IndexRequestBuilder indexRequestBuilder = this.client.prepareIndex(this.configuration.getIndex(), this.configuration.getMappingType());
        indexRequestBuilder.setSource(data);
        
        IndexResponse response = indexRequestBuilder.get();
		this.printIndexDocumentResponseLog(response);
	}
	
	private void indexSource(String json) {
		IndexRequestBuilder indexRequestBuilder = this.client.prepareIndex(this.configuration.getIndex(), this.configuration.getMappingType());
        indexRequestBuilder.setSource(json);
        
        IndexResponse response = indexRequestBuilder.get();
		this.printIndexDocumentResponseLog(response);
	}
	
	private void printIndexDocumentResponseLog(IndexResponse response) {
		if (this.configuration.isPrintElasticSearchLog()) {
			logger.info(String.format(
					"Document with id %s %s index %s and mapping type %s", response.getId(),
					(response.isCreated() ? "added to" : "put on"), response.getIndex(), response.getType()));
			logger.info("Document version: " + response.getVersion());
		}
	}
	
	public void start() {
		if (this.client != null) {
			return;
		}
		
		this.client = this.configureNewClient();
		this.indexer = new ElasticSearchIndexerImpl(this.client, this.configuration.getIndex());
		this.indexer.createIndex();
	}
	
	public void shutdown() {
		this.client.close();
		this.client = null;
	}
	
	public IElasticSearchConfiguration getConfiguration() {
		return configuration;
	}
	
	public ElasticSearchClient withConfiguration(IElasticSearchConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
	
	private Client configureNewClient() {
		if (this.configuration == null) {
			throw new DeferoException("ElasticSearch configuration is null.");
		}
		
		logger.info("Configure Inet Socket Transport Address.");
		logger.info("Host: " + this.configuration.getHost());
		logger.info("Transport client port: " + this.configuration.getTransportClientPort());
		
		Settings settings = null;
		if (this.configuration.getConfigProperties() != null) {
			Properties properties = this.getConfigProperties();
			settings = ImmutableSettings.settingsBuilder().put(properties).build();			
		}
		
		TransportClient transportClient = (settings == null) ? new TransportClient() : new TransportClient(settings);
		return transportClient.addTransportAddress(
			new InetSocketTransportAddress(
				this.configuration.getHost(),
				this.configuration.getTransportClientPort()
			)
		);
	}
	
	private Properties getConfigProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(this.configuration.getConfigProperties()));
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
		
		return properties;
	}
}