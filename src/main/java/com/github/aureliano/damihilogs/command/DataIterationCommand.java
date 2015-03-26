package com.github.aureliano.damihilogs.command;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.reader.IDataReader;
import com.github.aureliano.damihilogs.writer.IDataWriter;

public class DataIterationCommand implements Runnable {

	private IDataReader dataReader;
	private List<IDataWriter> dataWriters;
	private String id;
	
	private Map<String, Object> logExecution;
	
	public DataIterationCommand(IDataReader dataReader, List<IDataWriter> dataWriters) {
		this(dataReader, dataWriters, null);
	}
	
	public DataIterationCommand(IDataReader dataReader, List<IDataWriter> dataWriters, String id) {
		this.dataReader = dataReader;
		this.dataWriters = dataWriters;
		this.id = id;
	}

	@Override
	public void run() {
		this.execute();
	}
	
	public void execute() {
		for (IDataWriter dw : this.dataWriters) {
			IConfigOutput c = dw.getOutputConfiguration();
			ConfigHelper.copyMetadata(this.dataReader.getInputConfiguration(), c);
		}
		
		this.logExecution = this.dataIteration();
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
	
	private Map<String, Object> dataIteration() {		
		while (this.dataReader.keepReading()) {
			String data = this.dataReader.nextData();
			if (data != null) {
				this.write(this.dataWriters, data);
			}
		}
		
		this.dataReader.endResources();
		this.endResources(this.dataWriters);
		
		return this.dataReader.executionLog();
	}
	
	private void write(List<IDataWriter> dataWriters, String data) {
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