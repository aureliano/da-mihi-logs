package com.github.aureliano.defero.writer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.github.aureliano.defero.config.output.FileOutputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.event.AfterWritingEvent;
import com.github.aureliano.defero.event.BeforeWritingEvent;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.listener.DataWritingListener;

public class FileDataWriter implements IDataWriter {

	private FileOutputConfig outputConfiguration;
	private List<DataWritingListener> listeners;
	private OutputStreamWriter writer;
	
	private int lines;
	
	public FileDataWriter() {
		this.lines = 0;
	}

	@Override
	public IConfigOutput getOutputConfiguration() {
		return this.outputConfiguration;
	}

	@Override
	public IDataWriter withOutputConfiguration(IConfigOutput config) {
		this.outputConfiguration = (FileOutputConfig) config;
		return this;
	}

	@Override
	public List<DataWritingListener> getListeners() {
		return this.listeners;
	}

	@Override
	public IDataWriter withListeners(List<DataWritingListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	@Override
	public void write(Object data) {
		this.initialize();
		if (data == null) {
			return;
		}
		
		this.executeBeforeWritingMethodListeners(data);
		try {
			String text = (this.lines == 0) ? data.toString() : (LINE_SEPARATOR + data);
			this.writer.write(text);
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
		this.executeAfterWritingMethodListeners(data);
		this.lines++;
	}
	
	@Override
	public void endResources() {
		try {
			this.writer.flush();
			this.writer.close();
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}

	private void executeBeforeWritingMethodListeners(Object data) {
		for (DataWritingListener listener : this.listeners) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}

	private void executeAfterWritingMethodListeners(Object data) {
		for (DataWritingListener listener : this.listeners) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, data));
		}
	}
	
	private void initialize() {
		if (this.writer != null) {
			return;
		}
		
		try {
			FileOutputStream stream = new FileOutputStream(this.outputConfiguration.getFile(), this.outputConfiguration.isAppend());
			BufferedOutputStream buffer = new BufferedOutputStream(stream);
			this.writer = new OutputStreamWriter(buffer, this.outputConfiguration.getEncoding());
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
}