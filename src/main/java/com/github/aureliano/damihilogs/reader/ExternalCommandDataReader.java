package com.github.aureliano.damihilogs.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.exception.DeferoException;

public class ExternalCommandDataReader extends AbstractDataReader {

	private Process process;
	private BufferedReader bufferedReader;
	private ExternalCommandInput externalCommandInput;
	
	private static final Logger logger = Logger.getLogger(ExternalCommandInput.class);
	
	public ExternalCommandDataReader() {
		super();
	}

	@Override
	public String nextData() {
		this.initialize();
		String line = this.readNextLine();
		
		if (line == null) {
			super.markedToStop = true;
			return null;
		}
		
		String data = null;
		super.executeBeforeReadingMethodListeners();
		
		data = super.prepareLogEvent(line);
		super.executeAfterReadingMethodListeners(data);
		
		return data;
	}

	@Override
	public Map<String, Object> executionLog() {
		return new HashMap<String, Object>();
	}

	@Override
	public void endResources() {
		logger.info(" >>> Flushing and closing stream reader.");
		if (this.bufferedReader == null) {
			return;
		}

		this.process.destroy();
		if (this.process.exitValue() != 0) {
			logger.warn("External command exited with no normal exit status.");
		}
		
		try {
			this.bufferedReader.close();
			this.bufferedReader = null;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}

	@Override
	protected String readNextLine() {
		try {
			String line = null;
			if (super.unprocessedLine != null) {
				line = super.unprocessedLine;
				super.unprocessedLine = null;
			} else {
				line = this.bufferedReader.readLine();
				if (line != null) {
					super.lineCounter++;
				}
			}
			
			return line;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
	
	private void initialize() {
		if (this.bufferedReader != null) {
			return;
		}
		
		this.externalCommandInput = (ExternalCommandInput) super.inputConfiguration;
		
		logger.info("Execute command " + this.externalCommandInput.getCommand());
		logger.info("With parameters: " + this.externalCommandInput.getParameters());
		
		List<String> command = this.buildCommand();
		
		try {
			this.process = new ProcessBuilder(command).start();
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
		
		InputStreamReader inputStreamReader = new InputStreamReader(this.process.getInputStream());
		this.bufferedReader = new BufferedReader(inputStreamReader);
	}
	
	private List<String> buildCommand() {
		List<String> command = new ArrayList<String>();
		command.add(this.externalCommandInput.getCommand());
		
		for (String parameter : this.externalCommandInput.getParameters()) {
			command.add(parameter);
		}
		
		return command;
	}
}