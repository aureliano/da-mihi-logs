package com.github.aureliano.evtbridge.output.standard;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;

public class StandardOutputConfigTest {

	@Test
	public void testOutputType() {
		Assert.assertEquals(OutputConfigTypes.STANDARD.name(), new StandardOutputConfig().id());
	}
}