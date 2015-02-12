package com.github.aureliano.defero.writer;

import java.util.List;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.es.ElasticSearchClient;
import com.github.aureliano.defero.event.AfterWritingEvent;
import com.github.aureliano.defero.event.BeforeWritingEvent;
import com.github.aureliano.defero.formatter.IOutputFormatter;
import com.github.aureliano.defero.listener.DataWritingListener;

public class ElasticSearchDataWriter implements IDataWriter {

	private ElasticSearchOutputConfig outputConfiguration;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> listeners;
	
	private ElasticSearchClient elasticSearchClient;
	
	private static final Logger logger = Logger.getLogger(FileDataWriter.class.getName());
	
	public ElasticSearchDataWriter() {
		super();
	}

	@Override
	public IConfigOutput getOutputConfiguration() {
		return this.outputConfiguration;
	}

	@Override
	public IDataWriter withOutputConfiguration(IConfigOutput config) {
		this.outputConfiguration = (ElasticSearchOutputConfig) config;
		return this;
	}

	@Override
	public List<DataWritingListener> getListeners() {
		return this.listeners;
	}

	@Override
	public IDataWriter withListeners(List<DataWritingListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	@Override
	public void write(Object data) {
		this.initialize();
		if (data == null) {
			return;
		}
		
		this.executeBeforeWritingMethodListeners(data);
		if (this.outputFormatter != null) {
			data = this.outputFormatter.format(data);
		}
		this.elasticSearchClient.index(data);
		this.executeAfterWritingMethodListeners(data);
	}

	@Override
	public void endResources() {
		if (this.elasticSearchClient == null) {
			return;
		}
		
		logger.info(" >>> Shutting ElasticSearch client instance down.");
		this.elasticSearchClient.shutdown();
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return this.outputFormatter;
	}

	@Override
	public IDataWriter withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}

	private void executeBeforeWritingMethodListeners(Object data) {
		logger.fine("Execute beforeDataWriting listeners.");
		for (DataWritingListener listener : this.listeners) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}

	private void executeAfterWritingMethodListeners(Object data) {
		logger.fine("Execute afterDataWriting listeners.");
		for (DataWritingListener listener : this.listeners) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, data));
		}
	}
	
	private void initialize() {
		if (this.elasticSearchClient != null) {
			return;
		}
		
		logger.info(" >>> Starting new ElasticSearch client instance.");
		this.elasticSearchClient = new ElasticSearchClient().withConfiguration(this.outputConfiguration);
		this.elasticSearchClient.start();
	}
}