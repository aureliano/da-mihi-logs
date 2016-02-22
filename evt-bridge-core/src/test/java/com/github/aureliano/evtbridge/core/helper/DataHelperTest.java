package com.github.aureliano.evtbridge.core.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

public class DataHelperTest {

	@Test
	public void testCopyProperties() {
		Properties p1 = new Properties();
		p1.setProperty("a", "something");
		p1.setProperty("b", "another thing");
		
		Properties p2 = DataHelper.copyProperties(p1);
		assertNotSame(p1, p2);
		assertEquals(p1, p2);
	}
	
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
			assertEquals(names[i], data[i]);
		}
	}
	
	@Test
	public void testPropertiesToJson() {
		Properties p = new Properties();
		
		p.setProperty("Monteiro Lobato", "Brazil");
		p.setProperty("Liev Tolstói", "Russia");
		p.setProperty("Jules Verne", "France");
		
		String json = DataHelper.propertiesToJson(p);
		
		assertTrue(json.contains("\"Monteiro Lobato\":\"Brazil\""));
		assertTrue(json.contains("\"Liev Tolstói\":\"Russia\""));
		assertTrue(json.contains("\"Jules Verne\":\"France\""));
	}
	
	@Test
	public void testMapToProperties() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", true);
		map.put("host", "127.0.0.1");
		
		Properties properties = DataHelper.mapToProperties(map);
		
		assertEquals("true", properties.getProperty("test"));
		assertEquals("127.0.0.1", properties.getProperty("host"));
	}
	
	@Test
	public void testEncapsulateIntoList() {
		List<String> l = DataHelper.encapsulateIntoList("test");
		assertTrue(l instanceof ArrayList);
		assertTrue(l.size() == 1);
		assertEquals("test", l.get(0));
		
		List<String> l2 = DataHelper.encapsulateIntoList(l);
		assertSame(l, l2);
		assertTrue(l2.size() == 1);
		assertEquals("test", l2.get(0));
		
		assertNull(DataHelper.encapsulateIntoList(null));
	}
}