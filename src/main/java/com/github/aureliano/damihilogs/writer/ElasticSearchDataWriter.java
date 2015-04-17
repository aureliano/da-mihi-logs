package com.github.aureliano.damihilogs.writer;

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
	public void write(String content) {
		this.initialize();

		Object data = super.getParser().parse(content);
		this.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}
		
		if (this.outputFormatter != null) {
			data = this.outputFormatter.format(data);
		}
		
		boolean accept = super.getFilter().accept(data);
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
	}
	
	private void initialize() {
		if (this.elasticSearchClient != null) {
			return;
		}
		
		logger.info(" >>> Starting new ElasticSearch client instance.");
		this.elasticSearchClient = new ElasticSearchClient().withConfiguration((IElasticSearchConfiguration) this.outputConfiguration);
		this.elasticSearchClient.startUp();
	}
}