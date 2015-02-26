package com.github.aureliano.damihilogs.writer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.exception.DeferoException;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class FileDataWriter extends AbstractDataWriter {
	
	private PrintWriter writer;
	
	private static final Logger logger = Logger.getLogger(FileDataWriter.class);
	
	public FileDataWriter() {
		super();
	}

	@Override
	public void write(Object data) {
		this.initialize();

		super.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}

		boolean accept = super.filter.accept(data);
		if (accept) {
			String text = super.outputFormatter.format(data);
			this.writer.println(text);
		}
		
		super.executeAfterWritingMethodListeners(data, accept);
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
	
	private void initialize() {
		if (this.writer != null) {
			return;
		}
		
		if (super.outputFormatter == null) {
			super.outputFormatter = new PlainTextFormatter();
		}
		
		if (super.filter == null) {
			super.filter = new DefaultEmptyFilter();
		}
		
		FileOutputConfig fileOutputConfiguration = (FileOutputConfig) super.outputConfiguration;
		
		logger.info("Outputing data to " + fileOutputConfiguration.getFile().getPath());
		logger.info("Data encondig: " + fileOutputConfiguration.getEncoding());
		logger.info("Append data to file? " + fileOutputConfiguration.isAppend());
		
		try {
			FileOutputStream stream = new FileOutputStream(fileOutputConfiguration.getFile(), fileOutputConfiguration.isAppend());
			BufferedOutputStream buffer = new BufferedOutputStream(stream);
			OutputStreamWriter osw = new OutputStreamWriter(buffer, fileOutputConfiguration.getEncoding());
			this.writer = new PrintWriter(osw);
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
}