package com.github.aureliano.damihilogs.command;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.event.AfterInputEvent;
import com.github.aureliano.damihilogs.event.BeforeInputEvent;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.reader.IDataReader;
import com.github.aureliano.damihilogs.writer.IDataWriter;

public class DataIterationCommand implements Runnable {

	private IDataReader dataReader;
	private List<IDataWriter> dataWriters;
	private String id;
	private boolean writersInitialized;
	
	private Map<String, Object> logExecution;
	
	public DataIterationCommand(IDataReader dataReader, List<IDataWriter> dataWriters) {
		this(dataReader, dataWriters, null);
	}
	
	public DataIterationCommand(IDataReader dataReader, List<IDataWriter> dataWriters, String id) {
		this.dataReader = dataReader;
		this.dataWriters = dataWriters;
		this.id = id;
		this.writersInitialized = false;
	}

	@Override
	public void run() {
		this.execute();
	}
	
	public void execute() {
		for (IDataWriter dw : this.dataWriters) {
			IConfiguration c = dw.getConfiguration();
			ConfigHelper.copyMetadata(this.dataReader.getConfiguration(), c);
		}
		
		this.executeBeforeInputExecutionListener();
		this.logExecution = this.dataIteration();
		this.executeAfterInputExecutionListener();
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Map<String, Object> getLogExecution() {
		return this.logExecution;
	}
	
	public void setLogExecution(Map<String, Object> logExecution) {
		this.logExecution = logExecution;
	}
	
	private Map<String, Object> dataIteration() {
		this.dataReader.initializeResources();
		
		while (this.dataReader.keepReading()) {
			this.dataReader.executeBeforeReadingListeners();
			String data = this.dataReader.nextData();
			this.dataReader.executeAfterReadingListeners(data);
			
			if (data != null) {
				if (!this.writersInitialized) {
					this.initializeDataWriters();
				}
				
				this.write(data);
			}
		}
		
		this.dataReader.finalizeResources();
		this.finalizeResources(this.dataWriters);
		
		return this.dataReader.executionLog();
	}
	
	private void write(String event) {
		for (IDataWriter dataWriter : this.dataWriters) {
			Object data = dataWriter.parseEvent(event);
			if (data == null) {
				continue;
			}
			
			dataWriter.executeBeforeWritingListeners(data);
			boolean shouldWrite = dataWriter.applyFilter(data);
			
			if (shouldWrite) {
				data = dataWriter.formatData(data);
				dataWriter.write(data);
			}
			
			dataWriter.executeAfterWritingListeners(data, shouldWrite);
		}
	}
	
	private void initializeDataWriters() {
		for (IDataWriter dataWriter : this.dataWriters) {
			dataWriter.initializeResources();
		}
		
		this.writersInitialized = true;
	}
	
	private void executeBeforeInputExecutionListener() {
		IConfigInput inputConfiguration = (IConfigInput) this.dataReader.getConfiguration();
		List<ExecutionListener> listeners = inputConfiguration.getExecutionListeners();
		
		for (ExecutionListener listener : listeners) {
			listener.beforeExecution(new BeforeInputEvent(inputConfiguration));
		}
	}

	private void executeAfterInputExecutionListener() {
		IConfigInput inputConfiguration = (IConfigInput) this.dataReader.getConfiguration();
		List<ExecutionListener> listeners = inputConfiguration.getExecutionListeners();
		
		for (ExecutionListener listener : listeners) {
			listener.afterExecution(new AfterInputEvent(inputConfiguration, this.logExecution));
		}
	}
	
	private void finalizeResources(List<IDataWriter> dataWriters) {
		for (IDataWriter dataWriter : dataWriters) {
			dataWriter.finalizeResources();
		}
	}
	
	public IDataReader getDataReader() {
		return this.dataReader;
	}
	
	public List<IDataWriter> getDataWriters() {
		return this.dataWriters;
	}
}