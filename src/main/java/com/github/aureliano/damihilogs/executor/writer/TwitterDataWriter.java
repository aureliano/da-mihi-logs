package com.github.aureliano.damihilogs.executor.writer;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class TwitterDataWriter extends AbstractDataWriter {

	private static final Logger logger = Logger.getLogger(TwitterDataWriter.class);

	public TwitterDataWriter() {
		super();
	}

	@Override
	public void initializeResources() {
		logger.info("Open Twitter API Stream writer.");
		this.initialize();
	}

	@Override
	public void finalizeResources() {
		logger.debug(" >>> Flushing and closing Twitter API Stream writer.");
	}

	@Override
	public void write(Object data) {
		
	}

	private void initialize() {
		if (super.getConfiguration().getOutputFormatter() == null) {
			super.getConfiguration().withOutputFormatter(new PlainTextFormatter());
		}
	}
}