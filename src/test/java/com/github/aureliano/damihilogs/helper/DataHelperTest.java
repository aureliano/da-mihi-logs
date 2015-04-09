package com.github.aureliano.damihilogs.helper;

import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class DataHelperTest {

	@Test
	public void testSortProperties() {
		Properties p = new Properties();
		String[] names = new String[] {"Albert Camus", "Fiódor Dostoiévski", "Machado de Assis"};
		
		p.setProperty("Fiódor Dostoiévski", "Russia");
		p.setProperty("Machado de Assis", "Brazil");
		p.setProperty("Albert Camus", "France");
		
		p = DataHelper.sortProperties(p);
		
		Object[] data = p.keySet().toArray();
		for (byte i = 0; i < data.length; i++) {
			Assert.assertEquals(names[i], data[i]);
		}
	}
	
	@Test
	public void testPropertiesToJson() {
		Properties p = new Properties();
		
		p.setProperty("Monteiro Lobato", "Brazil");
		p.setProperty("Liev Tolstói", "Russia");
		p.setProperty("Jules Verne", "France");
		
		String json = DataHelper.propertiesToJson(p);
		
		Assert.assertTrue(json.contains("\"Monteiro Lobato\":\"Brazil\""));
		Assert.assertTrue(json.contains("\"Liev Tolstói\":\"Russia\""));
		Assert.assertTrue(json.contains("\"Jules Verne\":\"France\""));
	}
	
	@Test
	public void testJsonStringToObject() {
		String hash = "{\"Monteiro Lobato\":\"Brazil\",\"Liev Tolstói\":\"Russia\",\"Jules Verne\":\"France\"}";
		Map<String, String> map = DataHelper.jsonStringToObject(hash, Map.class);
		
		Assert.assertEquals(map.get("Monteiro Lobato"), "Brazil");
		Assert.assertEquals(map.get("Liev Tolstói"), "Russia");
		Assert.assertEquals(map.get("Jules Verne"), "France");
	}
}