package com.github.aureliano.damihilogs.writer;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.filter.IEventFielter;
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
			
			@SuppressWarnings("unchecked")
			public Object clone() {
				return null;
			}

			@Override
			public IParser<?> getParser() {
				return null;
			}

			@Override
			public IConfigOutput withParser(IParser<?> parser) {
				return null;
			}

			@Override
			public IEventFielter getFilter() {
				return null;
			}

			@Override
			public IConfigOutput withFilter(IEventFielter filter) {
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