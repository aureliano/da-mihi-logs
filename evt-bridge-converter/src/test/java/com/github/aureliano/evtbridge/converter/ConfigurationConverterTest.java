package com.github.aureliano.evtbridge.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.helper.DataHelper;

public class ConfigurationConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> map = this.createConfigurationMap();
		EventCollectorConfiguration configuration = new ConfigurationConverter().convert(map);
		
		assertEquals("xpto-collector", configuration.getCollectorId());
		assertFalse(configuration.isPersistExecutionLog());
		assertTrue(configuration.isMultiThreadingEnabled());
		
		assertEquals(DataHelper.mapToProperties(this.createMetadata()), configuration.getMetadata());
	}
	
	private Map<String, Object> createConfigurationMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("collectorId", "xpto-collector");
		map.put("persistExecutionLog", false);
		map.put("multiThreadingEnabled", "true");
		map.put("metadata", this.createMetadata());
		
		return map;
	}
	
	private Map<String, Object> createMetadata() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("test", true);
		map.put("host", "127.0.0.1");
		
		return map;
	}
}