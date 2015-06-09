package com.github.aureliano.damihilogs.executor.writer;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.es.ElasticSearchClient;
import com.github.aureliano.damihilogs.es.IElasticSearchConfiguration;

public class ElasticSearchDataWriter extends AbstractDataWriter {
	
	private ElasticSearchClient elasticSearchClient;
	
	private static final Logger logger = Logger.getLogger(FileDataWriter.class);
	
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
		this.elasticSearchClient = new ElasticSearchClient().withConfiguration((IElasticSearchConfiguration) this.outputConfiguration);
		this.elasticSearchClient.startUp();
	}
}