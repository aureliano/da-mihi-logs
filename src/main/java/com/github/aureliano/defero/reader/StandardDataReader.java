package com.github.aureliano.defero.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.input.StandardInputConfig;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.filter.DefaultEmptyFilter;

public class StandardDataReader extends AbstractDataReader {

	private BufferedReader bufferedReader;
	
	private static final Logger logger = Logger.getLogger(FileDataReader.class.getName());
		
	public StandardDataReader() {
		super();
	}

	@Override
	public Object nextData() {
		this.initialize();
		String line = this.readNextLine();
		
		if (line == null) {
			super.markedToStop = true;
			return null;
		}
		
		Object data = null;
		boolean accepted = false;
		
		do {
			super.executeBeforeReadingMethodListeners();
			
			data = super.parser.parse(super.prepareLogEvent(line));
			if (data == null) {
				continue;
			}
			accepted = super.filter.accept(data);
			
			super.executeAfterReadingMethodListeners(data, accepted);
		} while (!accepted && (line = this.readNextLine()) != null);
		
		return (accepted) ? data : null;
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
			throw new DeferoException(ex);
		}
	}
	
	private void initialize() {
		if (this.bufferedReader != null) {
			return;
		}
		
		if (super.filter == null) {
			super.filter = new DefaultEmptyFilter();
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
			throw new DeferoException(ex);
		}
	}

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> log = new HashMap<String, Object>();
		log.put("file.data.reader.last.line", super.lineCounter);
		
		return log;
	}
}