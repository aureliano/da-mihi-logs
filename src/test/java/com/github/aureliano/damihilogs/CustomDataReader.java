package com.github.aureliano.damihilogs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.reader.AbstractDataReader;

public class CustomDataReader extends AbstractDataReader {

	public CustomDataReader() {
		super();
	}

	@Override
	public String nextData() {
		super.markedToStop = true;
		return null;
	}

	@Override
	public Map<String, Object> executionLog() {
		return new HashMap<String, Object>();
	}

	@Override
	public void endResources() {
		
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		
	}

	@Override
	protected String readNextLine() {
		return null;
	}
}