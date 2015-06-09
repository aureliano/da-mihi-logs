package com.github.aureliano.damihilogs.config.output;

import org.junit.Assert;
import org.junit.Test;

public class StandardOutputConfigTest {

	@Test
	public void testOutputType() {
		Assert.assertEquals(OutputConfigTypes.STANDARD_OUTPUT.name(), new StandardOutputConfig().id());
	}
}