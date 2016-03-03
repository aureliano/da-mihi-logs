package com.github.aureliano.evtbridge.input.external_command;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.InputConfigTypes;

public class ExternalCommandInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("configurationId", "test-123");
		data.put("command", "ls");
		data.put("parameters", Arrays.asList("-la"));
		
		data.put("type", InputConfigTypes.EXTERNAL_COMMAND);
		
		ExternalCommandInputConfig conf = new ExternalCommandInputConverter().convert(data);
		assertEquals("test-123", conf.getConfigurationId());
		assertEquals("ls", conf.getCommand());
		assertEquals(Arrays.asList("-la"), conf.getParameters());
	}
	
	@Test
	public void testId() {
		assertEquals(InputConfigTypes.EXTERNAL_COMMAND.name(), new ExternalCommandInputConverter().id());
	}
}