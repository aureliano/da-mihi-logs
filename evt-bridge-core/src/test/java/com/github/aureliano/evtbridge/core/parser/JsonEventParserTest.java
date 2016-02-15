package com.github.aureliano.evtbridge.core.parser;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.helper.FileHelper;

public class JsonEventParserTest {

	private IParser<Map<String, ?>> parser;
	
	public JsonEventParserTest() {
		this.parser = new JsonEventParser();
	}
	
	@Test
	public void testParseSimplest() {
		long id = System.currentTimeMillis();
		String name = "guest";
		
		String json = new StringBuilder()
			.append("{\"id\": ")
			.append(id)
			.append(", \"name\": \"")
			.append(name + "\"}")
			.toString();
		
		Map<String, ?> map = this.parser.parse(json);
		assertEquals(id, map.get("id"));
		assertEquals(name, map.get("name"));
	}
	
	@Test
	public void testParseBasic() {
		String json = FileHelper.readResource("simple.json");
		
		Map<String, ?> map = this.parser.parse(json);
		
		@SuppressWarnings("unchecked")
		Map<String, ?> id = (Map<String, ?>) map.get("id");
		
		assertEquals("integer", id.get("type"));
		assertEquals(Boolean.TRUE, id.get("required"));
		
		@SuppressWarnings("unchecked")
		Map<String, ?> name = (Map<String, ?>) map.get("name");
		
		assertEquals("string", name.get("type"));
		assertEquals(Boolean.FALSE, name.get("required"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testParseDraft() {
		String json = FileHelper.readResource("draft-04.json");
		
		Map<String, ?> map = this.parser.parse(json);
		Map<String, ?> properties = (Map<String, ?>) map.get("properties");
		
		Map<String, ?> id = (Map<String, ?>) properties.get("id");
		
		assertEquals("integer", id.get("type"));
		assertEquals("User identification.", id.get("description"));
		assertEquals(12345, id.get("default"));
		
		Map<String, ?> name = (Map<String, ?>) properties.get("name");
		
		assertEquals("string", name.get("type"));
		assertEquals("User name.", name.get("description"));
		assertEquals("AVRELIANVS", name.get("default"));
	}
}