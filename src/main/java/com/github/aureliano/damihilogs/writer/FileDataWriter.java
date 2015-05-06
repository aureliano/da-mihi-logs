package com.github.aureliano.damihilogs.writer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class FileDataWriter extends AbstractDataWriter {
	
	private PrintWriter writer;
	
	private static final Logger logger = Logger.getLogger(FileDataWriter.class);
	
	public FileDataWriter() {
		super();
	}

	@Override
	public void write(String content) {
		this.initialize();
		
		Object data = super.getParser().parse(content);
		super.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}

		boolean accept = super.getFilter().accept(data);
		if (accept) {
			String text = super.outputConfiguration.getOutputFormatter().format(data);
			this.writer.println(text);
		}
		
		super.executeAfterWritingMethodListeners(data, accept);
	}
	
	@Override
	public void endResources() {
		if (this.writer == null) {
			return;
		}
		
		logger.debug(" >>> Flushing and closing stream writer.");
		
		this.writer.flush();
		this.writer.close();		
	}
	
	private void initialize() {
		if (this.writer != null) {
			return;
		}
		
		FileOutputConfig fileOutputConfiguration = (FileOutputConfig) super.outputConfiguration;
		
		logger.info("Outputing data to " + fileOutputConfiguration.getFile().getPath());
		logger.debug("Data encondig: " + fileOutputConfiguration.getEncoding());
		logger.debug("Append data to file? " + fileOutputConfiguration.isAppend());
		
		if (super.getOutputConfiguration().getOutputFormatter() == null) {
			super.getOutputConfiguration().withOutputFormatter(new PlainTextFormatter());
		}
		
		try {
			FileOutputStream stream = new FileOutputStream(fileOutputConfiguration.getFile(), fileOutputConfiguration.isAppend());
			BufferedOutputStream buffer = new BufferedOutputStream(stream);
			OutputStreamWriter osw = new OutputStreamWriter(buffer, fileOutputConfiguration.getEncoding());
			this.writer = new PrintWriter(osw);
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}