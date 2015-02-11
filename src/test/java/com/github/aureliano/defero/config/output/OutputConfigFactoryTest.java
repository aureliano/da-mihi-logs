package com.github.aureliano.defero.config.output;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.exception.DeferoException;

public class OutputConfigFactoryTest {

	@Test(expected = DeferoException.class)
	public void testCreateInputConfigNullType() {
		OutputConfigFactory.createOutputConfig(null);
	}

	@Test(expected = DeferoException.class)
	public void testCreateOutputConfigUnsupportedType() {
		OutputConfigFactory.createOutputConfig(this.getClass());
	}
	
	@Test
	public void testCreateOutputConfig() {
		IConfigOutput output = OutputConfigFactory.createOutputConfig(StandardOutputConfig.class);
		Assert.assertTrue(output instanceof StandardOutputConfig);
		
		output = OutputConfigFactory.createOutputConfig(FileOutputConfig.class);
		Assert.assertTrue(output instanceof FileOutputConfig);
		
		output = OutputConfigFactory.createOutputConfig(ElasticSearchOutputConfig.class);
		Assert.assertTrue(output instanceof ElasticSearchOutputConfig);
	}
}