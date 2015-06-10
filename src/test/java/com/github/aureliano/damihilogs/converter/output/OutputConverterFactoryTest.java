package com.github.aureliano.damihilogs.converter.output;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class OutputConverterFactoryTest {

	@Test
	public void testCreateConverterNonexistent() {
		String type = "test";
		String errorMessage = "Output config type '" + type + "' not supported. Expected one of: " + Arrays.toString(OutputConfigTypes.values());
		
		try {
			OutputConverterFactory.createConverter(type);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(errorMessage, ex.getMessage());
		}
	}

	@Test
	public void testCreateConverter() {
		Assert.assertTrue(OutputConverterFactory.createConverter(OutputConfigTypes.ELASTIC_SEARCH.name()) instanceof ElasticSearchOutputConverter);
		Assert.assertTrue(OutputConverterFactory.createConverter(OutputConfigTypes.ELASTIC_SEARCH.name().toLowerCase()) instanceof ElasticSearchOutputConverter);
		
		Assert.assertTrue(OutputConverterFactory.createConverter(OutputConfigTypes.FILE_OUTPUT.name()) instanceof FileOutputConverter);
		Assert.assertTrue(OutputConverterFactory.createConverter(OutputConfigTypes.FILE_OUTPUT.name().toLowerCase()) instanceof FileOutputConverter);
		
		Assert.assertTrue(OutputConverterFactory.createConverter(OutputConfigTypes.STANDARD_OUTPUT.name()) instanceof StandardOutputConverter);
		Assert.assertTrue(OutputConverterFactory.createConverter(OutputConfigTypes.STANDARD_OUTPUT.name().toLowerCase()) instanceof StandardOutputConverter);
	}
}