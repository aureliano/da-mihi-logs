package com.github.aureliano.damihilogs.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class ConfigurationSchemaHelper {

	private static final String CONFIGURATION_SCHEMA_DIR = "configuration-schema";
	public static final String SCHEMA_NAME_SEPARATOR = "-";
	
	private ConfigurationSchemaHelper() {
		super();
	}
	
	public static Map<String, Object> loadRootSchema() {
		String path = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, "root-schema.json");
		String jsonString = FileHelper.readResource(path);
		return DataHelper.jsonStringToObject(jsonString, Map.class);
	}
	
	public static List<String> fetchSchemaNames(Map<String, Object> map) {
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
	
	public static String fetchSchema(String path) {
		Map<String, Object> rootSchema = loadRootSchema();
		String schemaPath = null;
		
		if ("root".equals(path)) {
			schemaPath = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, rootSchema.get("id").toString());
		} else {
			schemaPath = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, findSchemaId(rootSchema, path));
		}
		
		try {
			return FileHelper.readResource(schemaPath);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex, "Could not load schema file " + schemaPath);
		}
	}
	
	private static String findSchemaId(Map<String, Object> rootSchema, String path) {
		Map<String, Object> map = null;
		
		for (String key : path.split(SCHEMA_NAME_SEPARATOR)) {
			if (map == null) {
				map = DataHelper.getAsHash(rootSchema, key);
			} else {
				map = DataHelper.getAsHash(map, key);
			}
			
			if (map == null) {
				throw new DaMihiLogsException("Could not load schema from path " + path);
			}
		}
		
		String schemaId = map.get("id").toString();
		return schemaId;
	}
}