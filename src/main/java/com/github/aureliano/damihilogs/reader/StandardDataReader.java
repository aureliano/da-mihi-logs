package com.github.aureliano.damihilogs.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class StandardDataReader extends AbstractDataReader {

	private BufferedReader bufferedReader;
	
	private static final Logger logger = Logger.getLogger(FileDataReader.class);
		
	public StandardDataReader() {
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
	
	protected String readNextLine() {
		try {
			String line = null;
			if (super.unprocessedLine != null) {
				line = super.unprocessedLine;
				super.unprocessedLine = null;
			} else {
				line = this.bufferedReader.readLine();
				if (line != null) {
					line = new String(line.getBytes(), ((StandardInputConfig) super.inputConfiguration).getEncoding());
					super.lineCounter++;
				}
			}
			
			return line;
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void initialize() {
		if (this.bufferedReader != null) {
			return;
		}
		
		logger.info("Reading data from Standard Input.");
		logger.info("Data encondig: " + ((StandardInputConfig) super.inputConfiguration).getEncoding());
		
		System.out.println("Listening standard input. Type text and then press Enter to process event or Ctrl + C to quit.");
		
		this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void endResources() {
		logger.info(" >>> Flushing and closing stream reader.");
		if (this.bufferedReader == null) {
			return;
		}
		
		try {
			this.bufferedReader.close();
			this.bufferedReader = null;
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	@Override
	public Map<String, Object> executionLog() {
		super.readingProperties.put("input.config." + super.inputConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return super.readingProperties;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) { }
}