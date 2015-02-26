package com.github.aureliano.damihilogs.writer;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.es.ElasticSearchClient;
import com.github.aureliano.damihilogs.es.IElasticSearchConfiguration;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;

public class ElasticSearchDataWriter extends AbstractDataWriter {
	
	private ElasticSearchClient elasticSearchClient;
	
	private static final Logger logger = Logger.getLogger(FileDataWriter.class);
	
	public ElasticSearchDataWriter() {
		super();
	}

	@Override
	public void write(Object data) {
		this.initialize();
		
		this.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}
		
		if (this.outputFormatter != null) {
			data = this.outputFormatter.format(data);
		}
		
		boolean accept = super.filter.accept(data);
		if (accept) {
			this.elasticSearchClient.index(data);
		}
		
		this.executeAfterWritingMethodListeners(data, accept);
	}

	@Override
	public void endResources() {
		if (this.elasticSearchClient == null) {
			return;
		}
		
		logger.info(" >>> Shutting ElasticSearch client instance down.");
		this.elasticSearchClient.shutdown();
	}
	
	private void initialize() {
		if (this.elasticSearchClient != null) {
			return;
		}
		
		if (super.filter == null) {
			super.filter = new DefaultEmptyFilter();
		}
		
		logger.info(" >>> Starting new ElasticSearch client instance.");
		this.elasticSearchClient = new ElasticSearchClient().withConfiguration((IElasticSearchConfiguration) this.outputConfiguration);
		this.elasticSearchClient.start();
	}
}