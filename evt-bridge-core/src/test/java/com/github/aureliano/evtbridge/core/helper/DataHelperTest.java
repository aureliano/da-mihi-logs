package com.github.aureliano.evtbridge.core.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

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
}