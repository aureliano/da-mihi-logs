package com.github.aureliano.damihilogs.es.processor;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.aureliano.damihilogs.es.annotations.Indexable;
import com.github.aureliano.damihilogs.es.cache.CacheType;
import com.github.aureliano.damihilogs.es.cache.MappingCache;
import com.github.aureliano.damihilogs.es.jackson.JacksonElasticSearchModule;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;

/**
 * Serialize/Deserialize object using Jackson
 */
public class ObjectProcessor {

	private ObjectMapper serializeMapper;
	private ObjectMapper deSerializeMapper;
	private MappingCache cache;

	public ObjectProcessor() {
		cache = MappingCache.getInstance();
		initSerializeMapper();
		initDeSerializeMapper();
	}

	private void initSerializeMapper() {
		serializeMapper = new ObjectMapper();
		serializeMapper.setVisibilityChecker(serializeMapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.ANY)
				.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE));
		serializeMapper.registerModule(new JacksonElasticSearchModule());
		serializeMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		serializeMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	private void initDeSerializeMapper() {
		deSerializeMapper = new ObjectMapper();
		deSerializeMapper.setVisibilityChecker(deSerializeMapper.getDeserializationConfig().getDefaultVisibilityChecker()
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE));
		deSerializeMapper.registerModule(new JacksonElasticSearchModule());
		deSerializeMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * Serialize object to json string
	 *
	 * @param object object to serialize
	 * @return json string of the object
	 */
	public String toJsonString(Object object) {
		try {
			return serializeMapper.writeValueAsString(object);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex, "Failed to convert object to json string");
		}

	}

	/**
	 * Deserialize object to json string
	 *
	 * @param string json string to deserialize
	 * @param clazz  Class to deserialize to
	 * @return object
	 */
	public <T> T fromJsonString(String string, Class<T> clazz) {
		try {
			return deSerializeMapper.readValue(string, clazz);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex, "Failed to convert object from json string");
		}
	}

	/**
	 * Get the id of the object
	 *
	 * @param object object to get id
	 * @return id value
	 */
	public Object getIdValue(Object object) {
		Field idField = (Field) cache.getCache(CacheType.ID_FIELD, object.getClass());  // try cache first
		if (idField == null) {
			idField = ReflectionHelper.getIdField(object.getClass());
			if (idField == null) {
				throw new DaMihiLogsException("Can't find id field for class: " + object.getClass().getSimpleName());
			}
			cache.putCache(CacheType.ID_FIELD, object.getClass(), idField);
		}
		return ReflectionHelper.getFieldValue(object, idField);
	}

	/**
	 * Get the routing id of the object
	 *
	 * @param object object to get routing id
	 * @return routing id
	 */
	public String getRoutingId(Object object) {
		Class<?> clazz = object.getClass();
		if (cache.isExist(CacheType.ROUTING_PATH, clazz)) {
			String path = (String) cache.getCache(CacheType.ROUTING_PATH, clazz);
			if (path == null || path.isEmpty()) return null;
			return getValueByPath(object, path);
		}
		Indexable indexable = (Indexable) clazz.getAnnotation(Indexable.class);
		if (indexable == null) {
			throw new DaMihiLogsException("Class " + object.getClass().getSimpleName() + " is no Indexable");
		}
		String path = indexable.routingFieldPath();
		cache.putCache(CacheType.ROUTING_PATH, clazz, path);
		if (!path.isEmpty()) {
			return getValueByPath(object, path);
		}
		return null;
	}

	/**
	 * Get the parent id of the object
	 *
	 * @param object object to get parent id
	 * @return parent id
	 */
	public String getParentId(Object object) {
		Class<?> clazz = object.getClass();
		if (cache.isExist(CacheType.PARENT_PATH, clazz)) {
			String path = (String) cache.getCache(CacheType.PARENT_PATH, clazz);
			if (path == null || path.isEmpty()) return null;
			return getValueByPath(object, path);
		}
		Indexable indexable = (Indexable) clazz.getAnnotation(Indexable.class);
		if (indexable == null) {
			throw new DaMihiLogsException("Class " + object.getClass().getSimpleName() + " is no Indexable");
		}
		String path = indexable.parentPath();
		cache.putCache(CacheType.PARENT_PATH, clazz, path);
		if (!path.isEmpty()) {
			return getValueByPath(object, path);
		}
		return null;
	}

	private String getValueByPath(Object object, String path) {
		String[] fieldNames = path.split("\\.");
		Object parentObject = object;
		for (String fieldName : fieldNames) {
			parentObject = ReflectionHelper.getFieldValue(parentObject, fieldName);
			if (parentObject == null) return null;
		}
		return parentObject.toString();
	}
}