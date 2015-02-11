package com.github.aureliano.defero.writer;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.defero.config.output.FileOutputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.exception.DeferoException;

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
		});
	}
	
	@Test
	public void testCreateDataWriter() {
		Assert.assertTrue(DataWriterFactory.createDataWriter(new StandardOutputConfig()) instanceof StandardDataWriter);
		Assert.assertTrue(DataWriterFactory.createDataWriter(new FileOutputConfig()) instanceof FileDataWriter);
		Assert.assertTrue(DataWriterFactory.createDataWriter(new ElasticSearchOutputConfig()) instanceof ElasticSearchDataWriter);
	}
}