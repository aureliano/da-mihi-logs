package com.github.aureliano.damihilogs.es;

import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.es.annotations.Indexable;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.http.HttpActionMetadata;

public class ElasticSearchClient {

	private IElasticSearchConfiguration configuration;
	private ElasticSearchIndexer indexer;
	
	private static final Logger logger = Logger.getLogger(ElasticSearchClient.class);
	
	public ElasticSearchClient() {
		super();
	}
	
	public void startUp() {
		this.indexer = new ElasticSearchIndexer(this.configuration);
		this.indexer.createIndex();
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
				throw new DaMihiLogsException("Don't know how to index object. Expected [java.util.Map, java.lang.String, com.github.kzwang.osem.annotations.@Indexable] but found " + data.getClass().getName());
			}
			this.indexIndexable(data);
		}
	}
	
	private void indexIndexable(Object indexable) {
		HttpActionMetadata response = this.indexer.index(indexable);
		this.printIndexDocumentResponseLog(response);
	}
	
	private void indexSource(Map<String, Object> data) {
		HttpActionMetadata response = this.indexer.index(data);
		this.printIndexDocumentResponseLog(response);
	}
	
	private void indexSource(String json) {
		HttpActionMetadata response = this.indexer.index(json);
		this.printIndexDocumentResponseLog(response);
	}
	
	private void printIndexDocumentResponseLog(HttpActionMetadata metadata) {
		if (this.configuration.isPrintElasticSearchLog()) {
			logger.info(new StringBuilder()
				.append(metadata.getRequestMethod())
				.append(" ")
				.append(metadata.getRequestUrl())
				.append(" [status:")
				.append(metadata.getResponseStatus())
				.append(", request:")
				.append(metadata.getRequestTime())
				.append("]")
				.toString());
			
			logger.info(">>> " + metadata.getRequestData());
			logger.info("<<< " + metadata.getResponseContent());
		}
	}
	
	public IElasticSearchConfiguration getConfiguration() {
		return configuration;
	}
	
	public ElasticSearchClient withConfiguration(IElasticSearchConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
}