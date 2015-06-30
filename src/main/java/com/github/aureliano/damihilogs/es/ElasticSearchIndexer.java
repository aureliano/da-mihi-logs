package com.github.aureliano.damihilogs.es;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.data.ObjectMapperSingleton;
import com.github.aureliano.damihilogs.es.cache.CacheType;
import com.github.aureliano.damihilogs.es.cache.MappingCache;
import com.github.aureliano.damihilogs.es.processor.MappingProcessor;
import com.github.aureliano.damihilogs.es.processor.ObjectProcessor;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ElasticSearchHelper;
import com.github.aureliano.damihilogs.helper.UrlHelper;
import com.github.aureliano.damihilogs.http.HttpActionData;
import com.github.aureliano.damihilogs.http.HttpActionMetadata;

public class ElasticSearchIndexer {

	private static final Logger logger = Logger.getLogger(ElasticSearchIndexer.class);

	private MappingCache cache;
	private ObjectProcessor objectProcessor;
	private IElasticSearchConfiguration configuration;

	public ElasticSearchIndexer(IElasticSearchConfiguration configuration) {
		this.configuration = configuration;
		this.cache = MappingCache.getInstance();
		this.objectProcessor = new ObjectProcessor();
	}
	
	public boolean createMapping(Class<?> clazz) {
		String mapping = MappingProcessor.getMappingAsJson(clazz);
		return putMapping(clazz, mapping);
	}
	
	public boolean deleteMapping(Class<?> clazz) {
		String typeName = MappingProcessor.getIndexTypeName(clazz);		
		String url = UrlHelper.buildGetIndexTypeUrl(this.configuration, typeName);
		
		HttpActionMetadata res = ElasticSearchHelper.doRequest(new HttpActionData().withUrl(url).withMethod("DELETE"));
		return (res.getResponseStatus() / 100 == 2);
	}

	public boolean putMapping(Class<?> clazz, String mapping) {
		String typeName = MappingProcessor.getIndexTypeName(clazz);
		String url = UrlHelper.buildGetIndexTypePutUrl(this.configuration, typeName);
		
		HttpActionData actionData = new HttpActionData()
			.withUrl(url)
			.withMethod("PUT")
			.withData(mapping.getBytes())
			.putRequestProperty("Content-Type", "application/json");
		
		HttpActionMetadata res = ElasticSearchHelper.doRequest(actionData);
		return (res.getResponseStatus() / 100 == 2);
	}

	public String getMapping(Class<?> clazz) {
		String typeName = MappingProcessor.getIndexTypeName(clazz);
		String url = UrlHelper.buildGetIndexTypeUrl(this.configuration, typeName) + "/_mapping";
		
		HttpActionMetadata metadata = ElasticSearchHelper.doRequest(new HttpActionData().withUrl(url).withMethod("GET"));
		return ElasticSearchHelper.formatMappingType(this.configuration.getIndex(), metadata.getResponseContent());
	}

	public HttpActionMetadata index(Object object) {
		return this.indexObject(object);
	}
	
	public HttpActionMetadata index(Map<?, ?> source) {
		try {
			String hash = ObjectMapperSingleton.instance().getObjectMapper().writeValueAsString(source);
			return this.index(hash);
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public HttpActionMetadata index(String source) {
		return this.indexSource(source);
	}
	
	public HttpActionMetadata delete(Object object) {
		return this.deleteObject(object);
	}
	
	public HttpActionMetadata delete(Map<?, ?> source) {
		try {
			String hash = ObjectMapperSingleton.instance().getObjectMapper().writeValueAsString(source);
			return this.deleteSource(hash);
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public HttpActionMetadata delete(String source) {
		return this.deleteSource(source);
	}

	public boolean indexExist() {
		String url = UrlHelper.buildGetIndexUrl(this.configuration) + "/_mapping";
		return ElasticSearchHelper.checkStatusOk(new HttpActionData().withUrl(url).withMethod("GET"));
	}

	public boolean createIndex() {
		if (!indexExist()) {
			String url = UrlHelper.buildGetIndexUrl(this.configuration);
			
			HttpActionMetadata metadata = ElasticSearchHelper.doRequest(new HttpActionData().withUrl(url).withMethod("PUT"));
			return (metadata.getResponseStatus() / 100 == 2);
		}
		
		logger.debug("Index " + this.configuration.getIndex() + " already exist, cannot create");
		return false;
	}
	
	public boolean deleteIndex() {
		if (indexExist()) {
			String url = UrlHelper.buildGetIndexUrl(this.configuration);
			
			HttpActionMetadata metadata = ElasticSearchHelper.doRequest(new HttpActionData().withUrl(url).withMethod("DELETE"));
			return (metadata.getResponseStatus() / 100 == 2);
		}
		
		logger.debug("Index " + this.configuration.getIndex() + " does not exist, cannot delete");
		return false;
	}

	private HttpActionMetadata indexObject(Object object) {
		Class<?> objectClass = object.getClass();
		String typeName = MappingProcessor.getIndexTypeName(objectClass);
		Object objectId = objectProcessor.getIdValue(object);
		
		if (objectId == null) {
			throw new DaMihiLogsException("Unable to find object id");
		}

		String objectJson = objectProcessor.toJsonString(object);
		
		if (!cache.isExist(CacheType.MAPPING, objectClass)) {  // check mapping exist in cache or not
			if (this.getMapping(objectClass) == null) {  // mapping not exist on server
				this.createMapping(objectClass);  // create mapping first
			}
		}
		
		String url = UrlHelper.buildPutDocumentUrl(this.configuration, typeName, objectId.toString());
		
		return ElasticSearchHelper.doRequest(
			new HttpActionData()
				.withUrl(url)
				.withMethod("PUT")
				.withData(objectJson.getBytes())
				.putRequestProperty("Content-Type", "application/json"));
	}
	
	private HttpActionMetadata indexSource(String source) {
		String id = ElasticSearchHelper.getIdFromHash(source);
		String url = UrlHelper.buildPutDocumentUrl(this.configuration, this.configuration.getMappingType(), id);
		
		return ElasticSearchHelper.doRequest(
				new HttpActionData()
					.withUrl(url)
					.withMethod("PUT")
					.withData(source.getBytes())
					.putRequestProperty("Content-Type", "application/json"));
	}
	
	private HttpActionMetadata deleteObject(Object object) {
		Class<?> objectClass = object.getClass();
		String typeName = MappingProcessor.getIndexTypeName(objectClass);
		Object objectId = objectProcessor.getIdValue(object);
		
		if (objectId == null) {
			throw new DaMihiLogsException("Unable to find object id");
		}

		String url = UrlHelper.buildDeleteDocumentUrl(this.configuration, typeName, objectId.toString());
		
		return ElasticSearchHelper.doRequest(
			new HttpActionData()
				.withUrl(url)
				.withMethod("DELETE"));
	}
	
	private HttpActionMetadata deleteSource(String source) {
		String id = ElasticSearchHelper.getIdFromHash(source);
		String url = UrlHelper.buildDeleteDocumentUrl(this.configuration, this.configuration.getMappingType(), id);
		
		return ElasticSearchHelper.doRequest(
				new HttpActionData()
					.withUrl(url)
					.withMethod("DELETE"));
	}
}