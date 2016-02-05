package com.github.aureliano.evtbridge.input.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.core.agent.AbstractDataReader;
import com.github.aureliano.evtbridge.core.exception.EventBridgeException;

public class StandardDataReader extends AbstractDataReader {

	private BufferedReader bufferedReader;
	
	private static final Logger logger = Logger.getLogger(StandardDataReader.class);
		
	public StandardDataReader() {
		super();
	}
	
	@Override
	public void initializeResources() {
		this.initialize();
	}
	
	@Override
	public String readLine() {
		try {
			return this.bufferedReader.readLine();
		} catch (IOException ex) {
			throw new EventBridgeException(ex);
		}
	}

	@Override
	public void finalizeResources() {
		logger.debug(" >>> Flushing and closing stream reader.");
		if (this.bufferedReader == null) {
			return;
		}
		
		try {
			this.bufferedReader.close();
			this.bufferedReader = null;
		} catch (IOException ex) {
			throw new EventBridgeException(ex);
		}
	}

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> map = new HashMap<>();
		map.put("input.config." + super.inputConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return map;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) { }
	
	private void initialize() {
		StandardInputConfig configuration = (StandardInputConfig) super.inputConfiguration;
		
		logger.info("Reading data from Standard Input.");
		logger.debug("Data encondig: " + configuration.getEncoding());
		
		System.out.println("Listening standard input. Type text and then press Enter to process event or Ctrl + C to quit.");
		
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(System.in, configuration.getEncoding()));
		} catch (UnsupportedEncodingException ex) {
			throw new EventBridgeException(ex);
		}
	}
}