package com.github.aureliano.evtbridge.app.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.helper.FileHelper;

public final class ConfigurationSchemaHelper {

	private static final String CONFIGURATION_SCHEMA_DIR = "configuration-schema";
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
	
	public static String fetchSchema(String type, String name) {
		Map<String, Object> rootSchema = loadRootSchema();
		String schemaPath = null;
		
		if ("root".equals(type)) {
			schemaPath = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, rootSchema.get("id").toString());
		} else {
			schemaPath = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, findSchemaId(rootSchema, type, name));
		}
		
		try {
			return FileHelper.readResource(schemaPath);
		} catch (Exception ex) {
			throw new EventBridgeException(ex, "Could not load schema file " + schemaPath);
		}
	}
	
	private static String findSchemaId(Map<String, Object> rootSchema, String type, String name) {
		Map<String, Object> map = DataHelper.getAsHash(rootSchema, type);
		if (map == null) {
			throw new EventBridgeException("Could not load schema of type " + type);
		}
		
		if (StringHelper.isEmpty(name)) {
			return map.get("id").toString();
		}
		
		map = DataHelper.getAsHash(map, name);
		if (map == null) {
			throw new EventBridgeException("Could not load schema of type '" + type + "' and name '" + name + "'");
		}
		
		return map.get("id").toString();
	}
}