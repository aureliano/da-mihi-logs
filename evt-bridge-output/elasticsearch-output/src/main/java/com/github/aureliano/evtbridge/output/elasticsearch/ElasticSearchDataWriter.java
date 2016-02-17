package com.github.aureliano.evtbridge.output.elasticsearch;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.core.agent.AbstractDataWriter;

public class ElasticSearchDataWriter extends AbstractDataWriter {
	
	private ElasticSearchClient elasticSearchClient;
	
	private static final Logger logger = Logger.getLogger(ElasticSearchDataWriter.class);
	
	public ElasticSearchDataWriter() {
		super();
	}
	
	@Override
	public void initializeResources() {
		this.initialize();
	}

	@Override
	public void write(Object data) {
		this.elasticSearchClient.index(data);
	}

	@Override
	public void finalizeResources() {
		if (this.elasticSearchClient == null) {
			return;
		}
		
		logger.debug(" >>> Shutting ElasticSearch client instance down.");
	}
	
	private void initialize() {
		logger.debug(" >>> Starting new ElasticSearch client instance.");
		this.elasticSearchClient = new ElasticSearchClient()
				.withConfiguration((IElasticSearchConfiguration) this.outputConfiguration);
		this.elasticSearchClient.startUp();
	}
}