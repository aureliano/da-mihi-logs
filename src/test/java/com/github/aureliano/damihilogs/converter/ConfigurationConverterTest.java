package com.github.aureliano.damihilogs.converter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.helper.DataHelper;

public class ConfigurationConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> map = this.createConfigurationMap();
		EventCollectorConfiguration configuration = new ConfigurationConverter().convert(map);
		
		Assert.assertEquals("xpto-collector", configuration.getCollectorId());
		Assert.assertFalse(configuration.isPersistExecutionLog());
		Assert.assertTrue(configuration.isMultiThreadingEnabled());
		
		Assert.assertEquals(DataHelper.mapToProperties(this.createMetadata()), configuration.getMetadata());
	}
	
	private Map<String, Object> createConfigurationMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", "xpto-collector");
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