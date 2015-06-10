package com.github.aureliano.damihilogs.clean;

import org.junit.Assert;
import org.junit.Test;

public class LogCleanerTest {

	@Test
	public void testId() {
		Assert.assertEquals(CleanerTypes.LOG.name(), new LogCleaner().id());
	}
}