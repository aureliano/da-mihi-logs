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
	private FileOutputConfig fileOutputConfiguration;
	
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
			String text = this.fileOutputConfiguration.getOutputFormatter().format(data);
			this.writer.println(text);
			if (!this.fileOutputConfiguration.isUseBuffer()) {
				this.writer.flush();
			}
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
		
		this.fileOutputConfiguration = (FileOutputConfig) super.outputConfiguration;
		
		logger.info("Outputing data to " + this.fileOutputConfiguration.getFile().getPath());
		logger.debug("Data encondig: " + this.fileOutputConfiguration.getEncoding());
		logger.debug("Append data to file? " + this.fileOutputConfiguration.isAppend());
		
		if (super.getConfiguration().getOutputFormatter() == null) {
			super.getConfiguration().withOutputFormatter(new PlainTextFormatter());
		}
		
		try {
			FileOutputStream stream = new FileOutputStream(this.fileOutputConfiguration.getFile(), this.fileOutputConfiguration.isAppend());
			BufferedOutputStream buffer = new BufferedOutputStream(stream);
			OutputStreamWriter osw = new OutputStreamWriter(buffer, this.fileOutputConfiguration.getEncoding());
			this.writer = new PrintWriter(osw);
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}