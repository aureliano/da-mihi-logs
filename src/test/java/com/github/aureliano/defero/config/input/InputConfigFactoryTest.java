package com.github.aureliano.defero.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.defero.exception.DeferoException;

public class InputConfigFactoryTest {

	@Test(expected = DeferoException.class)
	public void testCreateInputConfigNullType() {
		InputConfigFactory.createInputConfig(null);
	}

	@Test(expected = DeferoException.class)
	public void testCreateInputConfigUnsupportedType() {
		InputConfigFactory.createInputConfig(this.getClass());
	}
	
	@Test
	public void testCreateInputConfig() {
		IConfigInput input = InputConfigFactory.createInputConfig(InputFileConfig.class);
		Assert.assertTrue(input instanceof InputFileConfig);
	}
}