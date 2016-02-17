package com.github.aureliano.evtbridge.output.elasticsearch;

import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.core.http.HttpActionMetadata;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.Indexable;

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
				throw new ElasticSearchOutputException(
					"Don't know how to index object. Expected [java.util.Map, java.lang.String, " +
					Indexable.class.getName() + "] but found " + data.getClass().getName()
				);
			}
			this.indexIndexable(data);
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(Object data) {
		if (data instanceof Map) {
			this.deleteSource((Map<String, Object>) data);
		} else if (data instanceof String) {
			this.deleteSource(data.toString());
		} else {
			Indexable indexable = data.getClass().getAnnotation(Indexable.class);
			if (indexable == null) {
				throw new ElasticSearchOutputException(
						"Don't know how to index object. Expected [java.util.Map, java.lang.String, " +
						Indexable.class.getName() + "] but found " + data.getClass().getName()
					);
			}
			this.deleteIndexable(data);
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
	
	private void deleteIndexable(Object indexable) {
		HttpActionMetadata response = this.indexer.delete(indexable);
		this.printIndexDocumentResponseLog(response);
	}
	
	private void deleteSource(Map<String, Object> data) {
		HttpActionMetadata response = this.indexer.delete(data);
		this.printIndexDocumentResponseLog(response);
	}
	
	private void deleteSource(String json) {
		HttpActionMetadata response = this.indexer.delete(json);
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