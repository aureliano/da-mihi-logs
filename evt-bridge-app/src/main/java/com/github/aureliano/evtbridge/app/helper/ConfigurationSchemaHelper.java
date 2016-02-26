package com.github.aureliano.evtbridge.app.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.helper.FileHelper;

public final class ConfigurationSchemaHelper {

	private static final String CONFIGURATION_SCHEMA_DIR = "configuration-schema";
	private static final String SCHEMA_NAME_SEPARATOR = "-";
	private static final String ROOT_SCHEMA = "root-schema.json";
	
	private ConfigurationSchemaHelper() {}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadRootSchema() {
		String path = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, ROOT_SCHEMA);
		String jsonString = FileHelper.readResource(path);
		
		return DataHelper.jsonStringToObject(jsonString, Map.class);
	}
	
	public static List<String> fetchSchemaNames() {
		Map<String, Object> map = ConfigurationSchemaHelper.loadRootSchema();
		List<String> names = new ArrayList<String>();
		names.add("root");
		
		for (String key : map.keySet()) {
			if (key.equals("id")) {
				continue;
			}
			
			if (map.get(key) instanceof Map) {
				names.add(key);
			}
		}
		
		return names;
	}
}