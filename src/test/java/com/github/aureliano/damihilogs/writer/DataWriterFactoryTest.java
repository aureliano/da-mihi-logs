package com.github.aureliano.damihilogs.writer;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.parser.IParser;

public class DataWriterFactoryTest {

	@Test(expected = DaMihiLogsException.class)
	public void testCreateDataWriterNull() {
		DataWriterFactory.createDataWriter(null);
	}
	
	@Test(expected = DaMihiLogsException.class)
	public void testCreateDataWriterUnsupported() {
		DataWriterFactory.createDataWriter(new IConfigOutput() {
			public String outputType() {
				return null;
			}
			
			public IConfiguration clone() {
				return null;
			}

			public IParser<?> getParser() {
				return null;
			}

			public IConfigOutput withParser(IParser<?> parser) {
				return null;
			}

			public IEventFielter getFilter() {
				return null;
			}

			public IConfigOutput withFilter(IEventFielter filter) {
				return null;
			}

			public IConfiguration putMetadata(String key, String value) {
				return null;
			}

			public String getMetadata(String key) {
				return null;
			}
			
			public Properties getMetadata() {
				return null;
			}

			public IOutputFormatter getOutputFormatter() {
				return null;
			}

			public IConfigOutput withOutputFormatter(IOutputFormatter outputFormatter) {
				return null;
			}
		});
	}
	
	@Test
	public void testCreateDataWriter() {
		Assert.assertTrue(DataWriterFactory.createDataWriter(new StandardOutputConfig()) instanceof StandardDataWriter);
		Assert.assertTrue(DataWriterFactory.createDataWriter(new FileOutputConfig()) instanceof FileDataWriter);
		Assert.assertTrue(DataWriterFactory.createDataWriter(new ElasticSearchOutputConfig()) instanceof ElasticSearchDataWriter);
	}
}