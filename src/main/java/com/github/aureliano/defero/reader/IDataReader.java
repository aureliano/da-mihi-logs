package com.github.aureliano.defero.reader;

import java.util.List;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.filter.IEventFielter;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.parser.IParser;

public interface IDataReader {

	public abstract IConfigInput getInputConfiguration();
	
	public abstract IDataReader withInputConfiguration(IConfigInput config);
	
	public abstract IParser<?> getParser();
	
	public abstract IDataReader withParser(IParser<?> parser);
	
	public abstract List<DataReadingListener> getListeners();
	
	public abstract IDataReader withListeners(List<DataReadingListener> listeners);
	
	public abstract Object nextData();
	
	public abstract long lastLine();
	
	public abstract IEventFielter getFilter();
	
	public abstract IDataReader withFilter(IEventFielter filter);
	
	public static final int MAX_PARSE_ATTEMPTS = 10000;
}