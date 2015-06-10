package com.github.aureliano.damihilogs.converter.input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.InputConfigTypes;

public class ExternalCommandInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("id", "test-123");
		data.put("command", "ls");
		data.put("parameters", Arrays.asList("-la"));
		
		data.put("type", InputConfigTypes.EXTERNAL_COMMAND);
		
		ExternalCommandInput conf = new ExternalCommandInputConverter().convert(data);
		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals("ls", conf.getCommand());
		Assert.assertEquals(Arrays.asList("-la"), conf.getParameters());
	}
}