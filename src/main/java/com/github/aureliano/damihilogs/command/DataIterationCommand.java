package com.github.aureliano.damihilogs.command;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.reader.IDataReader;
import com.github.aureliano.damihilogs.writer.IDataWriter;

public class DataIterationCommand implements Runnable {

	private IDataReader dataReader;
	private List<IDataWriter> dataWriters;
	
	public DataIterationCommand(IDataReader dataReader, List<IDataWriter> dataWriters) {
		this.dataReader = dataReader;
		this.dataWriters = dataWriters;
	}

	@Override
	public void run() {
		this.execute();
	}
	
	public void execute() {		
		Map<String, Object> logExecution = this.dataIteration();
		CollectEventsCommand.addLogExecution(logExecution);
	}
	
	private Map<String, Object> dataIteration() {		
		while (this.dataReader.keepReading()) {
			Object data = this.dataReader.nextData();
			if (data != null) {
				this.write(this.dataWriters, data);
			}
		}
		
		this.dataReader.endResources();
		this.endResources(this.dataWriters);
		
		return this.dataReader.executionLog();
	}
	
	private void write(List<IDataWriter> dataWriters, Object data) {
		for (IDataWriter dataWriter : dataWriters) {
			dataWriter.write(data);
		}
	}
	
	private void endResources(List<IDataWriter> dataWriters) {
		for (IDataWriter dataWriter : dataWriters) {
			dataWriter.endResources();
		}
	}
}