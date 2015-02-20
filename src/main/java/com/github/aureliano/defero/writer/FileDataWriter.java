package com.github.aureliano.defero.writer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.output.FileOutputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.event.AfterWritingEvent;
import com.github.aureliano.defero.event.BeforeWritingEvent;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.formatter.IOutputFormatter;
import com.github.aureliano.defero.formatter.PlainTextFormatter;
import com.github.aureliano.defero.listener.DataWritingListener;

public class FileDataWriter implements IDataWriter {

	private FileOutputConfig outputConfiguration;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> listeners;
	private PrintWriter writer;
	
	private static final Logger logger = Logger.getLogger(FileDataWriter.class.getName());
	
	public FileDataWriter() {
		super();
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
	public IOutputFormatter getOutputFormatter() {
		return this.outputFormatter;
	}
	
	@Override
	public IDataWriter withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}

	@Override
	public void write(Object data) {
		this.initialize();
		if (data == null) {
			return;
		}
		
		this.executeBeforeWritingMethodListeners(data);
		
		String text = this.outputFormatter.format(data);
		this.writer.println(text);
		
		this.executeAfterWritingMethodListeners(data);
	}
	
	@Override
	public void endResources() {
		if (this.writer == null) {
			return;
		}
		
		logger.info(" >>> Flushing and closing stream writer.");
		
		this.writer.flush();
		this.writer.close();		
	}

	private void executeBeforeWritingMethodListeners(Object data) {
		logger.fine("Execute beforeDataWriting listeners.");
		for (DataWritingListener listener : this.listeners) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}

	private void executeAfterWritingMethodListeners(Object data) {
		logger.fine("Execute afterDataWriting listeners.");
		for (DataWritingListener listener : this.listeners) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, data));
		}
	}
	
	private void initialize() {
		if (this.writer != null) {
			return;
		}
		
		if (this.outputFormatter == null) {
			this.outputFormatter = new PlainTextFormatter();
		}
		
		logger.info("Outputing data to " + this.outputConfiguration.getFile().getPath());
		logger.info("Data encondig: " + this.outputConfiguration.getEncoding());
		logger.info("Append data to file? " + this.outputConfiguration.isAppend());
		
		try {
			FileOutputStream stream = new FileOutputStream(this.outputConfiguration.getFile(), this.outputConfiguration.isAppend());
			BufferedOutputStream buffer = new BufferedOutputStream(stream);
			OutputStreamWriter osw = new OutputStreamWriter(buffer, this.outputConfiguration.getEncoding());
			this.writer = new PrintWriter(osw);
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
}