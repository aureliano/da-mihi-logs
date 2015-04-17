package com.github.aureliano.damihilogs.es.cache;

import java.util.HashMap;

/**
 * Cache mapping, fields etc.
 */
public class MappingCache {

	private static MappingCache instance = null;

	private HashMap<CacheType, HashMap<Object, Object>> cache = new HashMap<CacheType, HashMap<Object,Object>>();

	/**
	 * Get Singleton {@link MappingCache} cache instance
	 *
	 * @return instance
	 */
	public static MappingCache getInstance() {
		if (instance == null) {
			instance = new MappingCache();
		}
		return instance;
	}

	public void putCache(CacheType cacheType, Object key, Object value) {
		if (!cache.containsKey(cacheType)) {
			cache.put(cacheType, new HashMap<Object, Object>());
		}
		cache.get(cacheType).put(key, value);
	}

	public boolean isExist(CacheType cacheType, Object key) {
		return cache.containsKey(cacheType) && cache.get(cacheType).containsKey(key);
	}

	public Object getCache(CacheType cacheType, Object key) {
		if (!cache.containsKey(cacheType)) {
			return null;
		}
		return cache.get(cacheType).get(key);
	}

	public void removeCache(CacheType cacheType, Object key) {
		if (cache.containsKey(cacheType)) {
			cache.get(cacheType).remove(key);
		}
	}
}