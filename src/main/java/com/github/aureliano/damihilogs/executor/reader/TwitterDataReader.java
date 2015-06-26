package com.github.aureliano.damihilogs.executor.reader;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TwitterDataReader extends AbstractDataReader {

	private static final Logger logger = Logger.getLogger(TwitterDataReader.class);
	
	public TwitterDataReader() {
		super();
	}

	@Override
	public void initializeResources() {
		logger.info("Open Twitter API Stream reader.");
		this.initialize();
	}

	@Override
	public void finalizeResources() {
		logger.debug(" >>> Flushing and closing Twitter API Stream reader.");
	}

	@Override
	public String readLine() {
		return null;
	}

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) { }

	private void initialize() {
		
	}
}