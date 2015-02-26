package com.github.aureliano.damihilogs.writer;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DeferoException;

public class DataWriterFactoryTest {

	@Test(expected = DeferoException.class)
	public void testCreateDataWriterNull() {
		DataWriterFactory.createDataWriter(null);
	}
	
	@Test(expected = DeferoException.class)
	public void testCreateDataWriterUnsupported() {
		DataWriterFactory.createDataWriter(new IConfigOutput() {
			public String outputType() {
				return null;
			}
			
			@SuppressWarnings("unchecked")
			public Object clone() {
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