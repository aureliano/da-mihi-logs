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
	public void testCreateInputConfigUnsupportedType() {
		OutputConfigFactory.createOutputConfig(this.getClass());
	}
	
	@Test
	public void testCreateInputConfig() {
		IConfigOutput input = OutputConfigFactory.createOutputConfig(StandardOutputConfig.class);
		Assert.assertTrue(input instanceof StandardOutputConfig);
	}
}