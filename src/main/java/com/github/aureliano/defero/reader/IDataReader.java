package com.github.aureliano.defero.reader;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.parser.IParser;

public interface IDataReader {

	public abstract IConfigInput getInputConfiguration();
	
	public abstract IDataReader withInputConfiguration(IConfigInput config);
	
	public abstract IParser<?> getParser();
	
	public abstract IDataReader withParser(IParser<?> parser);
	
	public abstract Object nextData();
	
	public abstract long lastLine();
}