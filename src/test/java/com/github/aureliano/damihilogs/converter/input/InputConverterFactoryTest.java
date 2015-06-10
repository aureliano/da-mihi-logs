package com.github.aureliano.damihilogs.converter.input;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class InputConverterFactoryTest {

	@Test
	public void testCreateConverterNonexistent() {
		String type = "test";
		String errorMessage = "Input config type '" + type + "' not supported. Expected one of: " + Arrays.toString(InputConfigTypes.values());
		
		try {
			InputConverterFactory.createConverter(type);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(errorMessage, ex.getMessage());
		}
	}

	@Test
	public void testCreateConverter() {
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.FILE_INPUT.name()) instanceof FileInputConverter);
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.FILE_INPUT.name().toLowerCase()) instanceof FileInputConverter);
		
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.FILE_TAILER.name()) instanceof FileTailerInputConverter);
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.FILE_TAILER.name().toLowerCase()) instanceof FileTailerInputConverter);
		
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.EXTERNAL_COMMAND.name()) instanceof ExternalCommandInputConverter);
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.EXTERNAL_COMMAND.name().toLowerCase()) instanceof ExternalCommandInputConverter);
		
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.STANDARD_INPUT.name()) instanceof StandardInputConverter);
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.STANDARD_INPUT.name().toLowerCase()) instanceof StandardInputConverter);
		
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.URL.name()) instanceof UrlInputConverter);
		Assert.assertTrue(InputConverterFactory.createConverter(InputConfigTypes.URL.name().toLowerCase()) instanceof UrlInputConverter);
	}
}