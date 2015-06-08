package com.github.aureliano.damihilogs.config.output;

import java.util.List;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public interface IConfigOutput extends IConfiguration {

	public abstract IParser<?> getParser();
	
	public abstract IConfigOutput withParser(IParser<?> parser);
	
	public abstract IEventFielter getFilter();
	
	public abstract IConfigOutput withFilter(IEventFielter filter);
	
	public abstract IOutputFormatter getOutputFormatter();
	
	public abstract List<DataWritingListener> getDataWritingListeners();
	
	public abstract IConfigOutput withDataWritingListeners(List<DataWritingListener> dataWritingListeners);
	
	public abstract IConfigOutput addDataWritingListener(DataWritingListener listener);
	
	public abstract IConfigOutput withOutputFormatter(IOutputFormatter outputFormatter);
}