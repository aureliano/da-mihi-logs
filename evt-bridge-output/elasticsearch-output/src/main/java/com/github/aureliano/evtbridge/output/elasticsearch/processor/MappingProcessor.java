package com.github.aureliano.evtbridge.output.elasticsearch.processor;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.core.data.ObjectMapperSingleton;
import com.github.aureliano.evtbridge.output.elasticsearch.ElasticSearchOutputException;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.Indexable;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.IndexableComponent;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.IndexableId;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.IndexableProperties;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.IndexableProperty;
import com.github.aureliano.evtbridge.output.elasticsearch.cache.CacheType;
import com.github.aureliano.evtbridge.output.elasticsearch.cache.MappingCache;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.DateDetectionEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.DocValuesFormatEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.DynamicEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.FieldDataFormat;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.FieldDataLoading;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.GeoShapeTreeEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.IncludeInAllEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.IndexEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.IndexOptionsEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.MultiFieldPathEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.NormsEnabledEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.NormsLoadingEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.NumericDetectionEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.ObjectFieldPathEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.PostingsFormatEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.SimilarityEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.TermVectorEnum;
import com.github.aureliano.evtbridge.output.elasticsearch.enumeration.TypeEnum;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Utils for mapping related operations
 */
public class MappingProcessor {

	private static final Logger logger = Logger.getLogger(MappingProcessor.class);

	private static final MappingCache cache = MappingCache.getInstance();

	/**
	 * Get the mapping for class
	 *
	 * @param clazz class to get mapping
	 * @return map of the mapping
	 */
	public static Map<String, Object> getMapping(Class<?> clazz) {
		String indexableName = getIndexTypeName(clazz);

		Map<String, Object> indexableMap = getIndexableMap(clazz);
		indexableMap.put("properties", getPropertiesMap(clazz));


		Map<String, Object> mapping = new HashMap<String, Object>();
		mapping.put(indexableName, indexableMap);
		return mapping;

	}

	/**
	 * Get the mapping for class
	 *
	 * @param clazz class to get mapping
	 * @return mapping string
	 */
	public static String getMappingAsJson(Class<?> clazz) {
		Map<String, Object> mappingMap = getMapping(clazz);
		if (mappingMap != null) {
			try {
            	ObjectMapper mapper = ObjectMapperSingleton.instance().getObjectMapper();
            	return mapper.writeValueAsString(mappingMap);
			} catch (IOException ex) {
				throw new ElasticSearchOutputException(ex, "Failed to convert mapping to JSON string");
			}
		}
		return null;
	}

	/**
	 * Get the index type name for class
	 *
	 * @param clazz class to get type name
	 * @return index type name
	 */
	public static String getIndexTypeName(Class<?> clazz) {
		if (cache.isExist(CacheType.INDEX_TYPE_NAME, clazz)) {
			return (String) cache.getCache(CacheType.INDEX_TYPE_NAME, clazz);
		}
		String typeName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
		Indexable indexable = (Indexable) clazz.getAnnotation(Indexable.class);
		if (indexable != null && indexable.name() != null && !indexable.name().isEmpty()) {
			typeName = indexable.name();
		}
		cache.putCache(CacheType.INDEX_TYPE_NAME, clazz, typeName);
		return typeName;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> getPropertiesMap(Class<?> clazz) {
		Map<String, Object> propertiesMap =new HashMap<String, Object>();

		// process IndexableProperty
		Set<Field> indexablePropertyFields = getAllFields(clazz, withAnnotation(IndexableProperty.class));
		Set<Method> indexablePropertyMethods = getAllMethods(clazz, withAnnotation(IndexableProperty.class));
		if (!indexablePropertyFields.isEmpty()) {
			for (Field field : indexablePropertyFields) {
				processIndexableProperty(field, propertiesMap);
			}
		}
		if (!indexablePropertyMethods.isEmpty()) {
			for (Method method : indexablePropertyMethods) {
				processIndexableProperty(method, propertiesMap);
			}
		}

		// process IndexableComponent
		Set<Field> indexableComponentFields = getAllFields(clazz, withAnnotation(IndexableComponent.class));
		Set<Method> indexableComponentMethods = getAllMethods(clazz, withAnnotation(IndexableComponent.class));
		if (!indexableComponentFields.isEmpty()) {
			for (Field field : indexableComponentFields) {
				processIndexableComponent(field, propertiesMap);
			}
		}
		if (!indexableComponentMethods.isEmpty()) {
			for (Method method : indexableComponentMethods) {
				processIndexableComponent(method, propertiesMap);
			}
		}

		// process IndexableProperties
		Set<Field> indexablePropertiesFields = getAllFields(clazz, withAnnotation(IndexableProperties.class));
		Set<Method> indexablePropertiesMethods = getAllMethods(clazz, withAnnotation(IndexableProperties.class));
		if (!indexablePropertiesFields.isEmpty()) {
			for (Field field : indexablePropertiesFields) {
				processIndexableProperties(field, propertiesMap);
			}
		}
		if (!indexablePropertiesMethods.isEmpty()) {
			for (Method method : indexablePropertiesMethods) {
				processIndexableProperties(method, propertiesMap);
			}
		}
		return propertiesMap;
	}

	@SuppressWarnings("deprecation")
	private static Map<String, Object> getIndexableMap(Class<?> clazz) {
		Map<String, Object> objectMap = new HashMap<String, Object>();

		Indexable indexable = (Indexable) clazz.getAnnotation(Indexable.class);
		if (indexable == null) {
			throw new ElasticSearchOutputException("Class " + clazz.getName() + " is not Indexable");
		}

		if (!indexable.indexAnalyzer().isEmpty()) {
			objectMap.put("index_analyzer", indexable.indexAnalyzer());
		}

		if (!indexable.searchAnalyzer().isEmpty()) {
			objectMap.put("search_analyzer", indexable.searchAnalyzer());
		}

		if (indexable.dynamicDateFormats().length > 0) {
			objectMap.put("dynamic_date_formats", Lists.newArrayList(indexable.dynamicDateFormats()));
		}

		if (!indexable.dateDetection().equals(DateDetectionEnum.NA)) {
			objectMap.put("date_detection", Boolean.valueOf(indexable.dateDetection().toString()));
		}

		if (!indexable.numericDetection().equals(NumericDetectionEnum.NA)) {
			objectMap.put("numeric_detection", Boolean.valueOf(indexable.numericDetection().toString()));
		}

		// handle _parent
		if (indexable.parentClass() != void.class) {
			Map<String, Object> parentMap = new HashMap<String, Object>();
			parentMap.put("type", getIndexTypeName(indexable.parentClass()));
			objectMap.put("_parent", parentMap);
		}

		// handle _id
		Field indexableIdField = ReflectionHelper.getIdField(clazz, IndexableId.class);
		Map<String, Object> idMap = getIndexableIdMap(indexableIdField);
		if (!idMap.isEmpty()) {
			objectMap.put("_id", idMap);
		}

		// handle _type
		Map<String, Object> typeMap = new HashMap<String, Object>();
		if (indexable.typeFieldStore()) {
			typeMap.put("store", "yes");
		}

		if (!indexable.typeFieldIndex().equals(IndexEnum.NA)) {
			typeMap.put("index", indexable.typeFieldIndex().toString().toLowerCase());
		}

		if (!typeMap.isEmpty()) {
			objectMap.put("_type", typeMap);
		}

		// handle _source
		Map<String, Object> sourceMap = new HashMap<String, Object>();
		if (!indexable.sourceFieldEnabled()) {
			sourceMap.put("enabled", Boolean.FALSE);
		}

		if (indexable.sourceFieldCompress()) {
			sourceMap.put("compress", Boolean.TRUE);
		}

		if (!indexable.sourceFieldCompressThreshold().isEmpty()) {
			sourceMap.put("compress_threshold", indexable.sourceFieldCompressThreshold());
		}

		if (indexable.sourceFieldIncludes().length > 0) {
			sourceMap.put("includes", Lists.newArrayList(indexable.sourceFieldIncludes()));
		}

		if (indexable.sourceFieldExcludes().length > 0) {
			sourceMap.put("excludes", Lists.newArrayList(indexable.sourceFieldExcludes()));
		}

		if (!sourceMap.isEmpty()) {
			objectMap.put("_source", sourceMap);
		}

		// handle _all
		Map<String, Object> allMap = new HashMap<String, Object>();
		if (!indexable.allFieldEnabled()) {
			allMap.put("enabled", Boolean.FALSE);
		}

		if (indexable.allFieldStore()) {
			allMap.put("store", "yes");
		}

		if (!indexable.allFieldTermVector().equals(TermVectorEnum.NA)) {
			allMap.put("term_vector", indexable.allFieldTermVector().toString().toLowerCase());
		}

		if (!indexable.allFieldAnalyzer().isEmpty()) {
			allMap.put("analyzer", indexable.allFieldAnalyzer());
		}

		if (!indexable.allFieldIndexAnalyzer().isEmpty()) {
			allMap.put("index_analyzer", indexable.allFieldIndexAnalyzer());
		}

		if (!indexable.allFieldSearchAnalyzer().isEmpty()) {
			allMap.put("search_analyzer", indexable.allFieldSearchAnalyzer());
		}

		if (!allMap.isEmpty()) {
			objectMap.put("_all", allMap);
		}

		// handle _analyzer
		Map<String, Object> analyzerMap = new HashMap<String, Object>();
		if (!indexable.analyzerFieldPath().isEmpty()) {
			analyzerMap.put("path", indexable.analyzerFieldPath());
		}

		if (!analyzerMap.isEmpty()) {
			objectMap.put("_analyzer", analyzerMap);
		}

		// handle _boost
		Map<String, Object> boostMap = new HashMap<String, Object>();
		if (!indexable.boostFieldName().isEmpty()) {
			boostMap.put("name", indexable.boostFieldName());
		}

		if (indexable.boostFieldNullValue() != Double.MIN_VALUE) {
			boostMap.put("null_value", indexable.boostFieldNullValue());
		}

		if (!boostMap.isEmpty()) {
			objectMap.put("_boost", boostMap);
		}

		// handle _routing
		Map<String, Object> routingMap = new HashMap<String, Object>();
		if (!indexable.routingFieldStore()) {
			routingMap.put("store", "no");
		}

		if (!indexable.routingFieldIndex().equals(IndexEnum.NA)) {
			routingMap.put("index", indexable.routingFieldIndex().toString().toLowerCase());
		}

		if (indexable.routingFieldRequired()) {
			routingMap.put("required", Boolean.TRUE);
		}

		if (!indexable.routingFieldPath().isEmpty()) {
			routingMap.put("path", indexable.routingFieldPath());
		}

		if (!routingMap.isEmpty()) {
			objectMap.put("_routing", routingMap);
		}

		// handle _index
		Map<String, Object> indexMap = new HashMap<String, Object>();
		if (indexable.indexFieldEnabled()) {
			indexMap.put("enabled", Boolean.TRUE);
		}

		if (!indexMap.isEmpty()) {
			objectMap.put("_index", indexMap);
		}

		// handle _size
		Map<String, Object> sizeMap = Maps.newHashMap();
		if (indexable.sizeFieldEnabled()) {
			sizeMap.put("enabled", Boolean.TRUE);
		}

		if (indexable.sizeFieldStore()) {
			sizeMap.put("store", "yes");
		}

		if (!sizeMap.isEmpty()) {
			objectMap.put("_size", sizeMap);
		}

		// handle _timestamp
		Map<String, Object> timestampMap = new HashMap<String, Object>();
		if (indexable.timestampFieldEnabled()) {
			timestampMap.put("enabled", Boolean.TRUE);
		}

		if (indexable.timestampFieldStore()) {
			timestampMap.put("store", "yes");
		}

		if (!indexable.timestampFieldIndex().equals(IndexEnum.NA)) {
			timestampMap.put("index", indexable.timestampFieldIndex().toString().toLowerCase());
		}

		if (!indexable.timestampFieldPath().isEmpty()) {
			timestampMap.put("path", indexable.timestampFieldPath());
		}

		if (!indexable.timestampFieldFormat().isEmpty()) {
			timestampMap.put("format", indexable.timestampFieldFormat());
		}

		if (!timestampMap.isEmpty()) {
			objectMap.put("_timestamp", timestampMap);
		}

		// handle _ttl
		Map<String, Object> ttlMap = Maps.newHashMap();
		if (indexable.ttlFieldEnabled()) {
			ttlMap.put("enabled", Boolean.TRUE);
		}

		if (!indexable.ttlFieldStore()) {
			ttlMap.put("store", "no");
		}

		if (!indexable.ttlFieldIndex().equals(IndexEnum.NA)) {
			ttlMap.put("index", indexable.ttlFieldIndex().toString().toLowerCase());
		}

		if (!indexable.ttlFieldDefault().isEmpty()) {
			ttlMap.put("default", indexable.ttlFieldDefault());
		}

		if (!ttlMap.isEmpty()) {
			objectMap.put("_ttl", ttlMap);
		}

		return objectMap;
	}

	private static Map<String, Object> getIndexableIdMap(Field field) {
		Map<String, Object> idMap = new HashMap<String, Object>();

		IndexableId indexableId = field.getAnnotation(IndexableId.class);
		if (indexableId.index() != IndexEnum.NA) {
			idMap.put("index", indexableId.index().toString().toLowerCase());
		}

		if (indexableId.store()) {
			idMap.put("store", "yes");
		}

		IndexableProperty indexableProperty = field.getAnnotation(IndexableProperty.class);
		if (indexableProperty != null) {
			String fieldName = field.getName();
			if (indexableProperty.name() != null && !indexableProperty.name().isEmpty()) {
				fieldName = indexableProperty.name();
			}
			idMap.put("path", fieldName);  // only need to put this if the IndexableId field is also IndexableProperty
		}

		return idMap;
	}

	private static void processIndexableProperty(AccessibleObject accessibleObject, Map<String, Object> propertiesMap) {
		IndexableProperty indexableProperty = accessibleObject.getAnnotation(IndexableProperty.class);
		if (indexableProperty == null) {
			throw new ElasticSearchOutputException("Unable to find annotation IndexableProperty");
		}
		String fieldName = null;
		if (accessibleObject instanceof Field) {
			fieldName = ((Field) accessibleObject).getName();
		}
		if (indexableProperty.name() != null && !indexableProperty.name().isEmpty()) {
			fieldName = indexableProperty.name();
		}
		if (fieldName == null) {
			throw new ElasticSearchOutputException("Unable to find field name");
		}

		Map<String, Object> fieldMap = getIndexablePropertyMapping(accessibleObject, indexableProperty);
		if (fieldMap != null) {
			propertiesMap.put(fieldName, fieldMap);
		}
	}

	private static Map<String, Object> getIndexablePropertyMapping(AccessibleObject accessibleObject, IndexableProperty indexableProperty) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();

		String fieldType = getFieldType(indexableProperty.type(), accessibleObject);

		if (fieldType.equals(TypeEnum.JSON.toString().toLowerCase())) {
			logger.warn("Can't find mapping for json, please specify rawMapping if needed");
			return null;
		}

		fieldMap.put("type", fieldType);

		if (indexableProperty.index() != IndexEnum.NA) {
			fieldMap.put("index", indexableProperty.index().toString().toLowerCase());
		}

		if (indexableProperty.docValues()) {
			fieldMap.put("doc_values", Boolean.TRUE);
		}

		if (indexableProperty.docValuesFormat() != DocValuesFormatEnum.NA) {
			fieldMap.put("doc_values_format", indexableProperty.docValuesFormat().toString().toLowerCase());
		}

		if (!indexableProperty.indexName().isEmpty()) {
			fieldMap.put("index_name", indexableProperty.indexName());
		}

		if (indexableProperty.termVector() != TermVectorEnum.NA) {
			fieldMap.put("term_vector", indexableProperty.termVector().toString().toLowerCase());
		}

		if (indexableProperty.store()) {
			fieldMap.put("store", "yes");
		}

		if (indexableProperty.boost() != Double.MIN_VALUE) {
			fieldMap.put("boost", indexableProperty.boost());
		}

		if (!indexableProperty.nullValue().isEmpty()) {
			fieldMap.put("null_value", indexableProperty.nullValue());
		}

		if (indexableProperty.normsEnabled() != NormsEnabledEnum.NA) {
			fieldMap.put("norms.enabled", indexableProperty.normsEnabled().toString().toLowerCase());
		}

		if (indexableProperty.normsLoading() != NormsLoadingEnum.NA) {
			fieldMap.put("norms.loading", indexableProperty.normsLoading().toString().toLowerCase());
		}

		if (indexableProperty.indexOptions() != IndexOptionsEnum.NA) {
			fieldMap.put("index_options", indexableProperty.indexOptions().toString().toLowerCase());
		}

		if (!indexableProperty.analyzer().isEmpty()) {
			fieldMap.put("analyzer", indexableProperty.analyzer());
		}

		if (!indexableProperty.indexAnalyzer().isEmpty()) {
			fieldMap.put("index_analyzer", indexableProperty.indexAnalyzer());
		}

		if (!indexableProperty.searchAnalyzer().isEmpty()) {
			fieldMap.put("search_analyzer", indexableProperty.searchAnalyzer());
		}

		if (indexableProperty.includeInAll() != IncludeInAllEnum.NA) {
			fieldMap.put("include_in_all", indexableProperty.includeInAll().toString().toLowerCase());
		}

		if (indexableProperty.ignoreAbove() != Integer.MIN_VALUE) {
			fieldMap.put("ignore_above", indexableProperty.ignoreAbove());
		}

		if (indexableProperty.positionOffsetGap() != Integer.MIN_VALUE) {
			fieldMap.put("position_offset_gap", indexableProperty.positionOffsetGap());
		}

		if (indexableProperty.precisionStep() != Integer.MIN_VALUE) {
			fieldMap.put("precision_step", indexableProperty.precisionStep());
		}

		if (indexableProperty.ignoreMalformed()) {
			fieldMap.put("ignore_malformed", Boolean.TRUE);
		}

		if (!indexableProperty.coerce()) {
			fieldMap.put("coerce", Boolean.FALSE);
		}

		if (indexableProperty.postingsFormat() != PostingsFormatEnum.NA) {
			fieldMap.put("postings_format", indexableProperty.postingsFormat().toString().toLowerCase());
		}

		if (indexableProperty.similarity() != SimilarityEnum.NA) {
			switch (indexableProperty.similarity()) {
			case DEFAULT:
				fieldMap.put("similarity", indexableProperty.postingsFormat().toString().toLowerCase());
				break;
			case BM25: // BM25 should be uppercase
				fieldMap.put("similarity", indexableProperty.postingsFormat().toString().toUpperCase());
				break;
			default:
				break;
			}
		}

		if (!indexableProperty.format().isEmpty()) {
			fieldMap.put("format", indexableProperty.format());
		}

		if (indexableProperty.copyTo().length > 0) {
			fieldMap.put("copy_to", Lists.newArrayList(indexableProperty.copyTo()));
		}

		if (indexableProperty.geoPointLatLon()) {
			fieldMap.put("lat_lon", Boolean.TRUE);
		}

		if (indexableProperty.geoPointGeohash()) {
			fieldMap.put("geohash", Boolean.TRUE);
		}

		if (indexableProperty.geoPointGeohashPrecision() != Integer.MIN_VALUE) {
			fieldMap.put("geohash_precision", indexableProperty.geoPointGeohashPrecision());
		}

		if (indexableProperty.geoPointGeohashPrefix()) {
			fieldMap.put("geohash_prefix", Boolean.TRUE);
		}

		if (!indexableProperty.geoPointValidate()) {
			fieldMap.put("validate", Boolean.FALSE);
		}

		if (!indexableProperty.geoPointValidateLat()) {
			fieldMap.put("validate_lat", Boolean.FALSE);
		}

		if (!indexableProperty.geoPointValidateLon()) {
			fieldMap.put("validate_lon", Boolean.FALSE);
		}

		if (!indexableProperty.geoPointNormalize()) {
			fieldMap.put("normalize", Boolean.FALSE);
		}

		if (!indexableProperty.geoPointNormalizeLat()) {
			fieldMap.put("normalize_lat", Boolean.FALSE);
		}

		if (!indexableProperty.geoPointNormalizeLon()) {
			fieldMap.put("normalize_lon", Boolean.FALSE);
		}

		if (indexableProperty.geoShapeTree() != GeoShapeTreeEnum.NA) {
			fieldMap.put("tree", indexableProperty.geoShapeTree().toString().toLowerCase());
		}

		if (!indexableProperty.geoShapePrecision().isEmpty()) {
			fieldMap.put("precision", indexableProperty.geoShapePrecision());
		}

		if (indexableProperty.geoShapeTreeLevels() != Integer.MIN_VALUE) {
			fieldMap.put("tree_levels", indexableProperty.geoShapeTreeLevels());
		}

		if (indexableProperty.geoShapeDistanceErrorPct() != Float.MIN_VALUE) {
			fieldMap.put("distance_error_pct", indexableProperty.geoShapeDistanceErrorPct());
		}

		Map<String, Object> fieldDataMap = getFieldDataMap(indexableProperty);
		if (fieldDataMap != null && !fieldDataMap.isEmpty()) {
			fieldMap.put("fielddata", fieldDataMap);
		}

		return fieldMap;
	}

	private static Map<String, Object> getFieldDataMap(IndexableProperty indexableProperty) {
		Map<String, Object> fieldDataMap = new HashMap<String, Object>();
		if (!indexableProperty.fieldDataFormat().equals(FieldDataFormat.NA)) {
			fieldDataMap.put("format", indexableProperty.fieldDataFormat().toString().toLowerCase());
		}

		if (!indexableProperty.fieldDataLoading().equals(FieldDataLoading.NA)) {
			fieldDataMap.put("loading", indexableProperty.fieldDataLoading().toString().toLowerCase());
		}

		if (!indexableProperty.fieldDataFilterFrequencyMin().isEmpty()) {
			fieldDataMap.put("filter.frequency.min", indexableProperty.fieldDataFilterFrequencyMin());
		}

		if (!indexableProperty.fieldDataFilterFrequencyMax().isEmpty()) {
			fieldDataMap.put("filter.frequency.max", indexableProperty.fieldDataFilterFrequencyMax());
		}

		if (!indexableProperty.fieldDataFilterFrequencyMinSegmentSize().isEmpty()) {
			fieldDataMap.put("filter.frequency.min_segment_size", indexableProperty.fieldDataFilterFrequencyMinSegmentSize());
		}

		if (!indexableProperty.fieldDataFilterRegexPattern().isEmpty()) {
			fieldDataMap.put("filter.regex.pattern", indexableProperty.fieldDataFilterRegexPattern());
		}
		return fieldDataMap;
	}

	private static void processIndexableComponent(AccessibleObject accessibleObject, Map<String, Object> propertiesMap) {
		IndexableComponent indexableComponent = accessibleObject.getAnnotation(IndexableComponent.class);
		if (indexableComponent == null) {
			throw new ElasticSearchOutputException("Unable to find annotation IndexableComponent");
		}
		String fieldName = null;
		if (accessibleObject instanceof Field) {
			fieldName = ((Field) accessibleObject).getName();
		}
		if (indexableComponent.name() != null && !indexableComponent.name().isEmpty()) {
			fieldName = indexableComponent.name();
		}
		if (fieldName == null) {
			throw new ElasticSearchOutputException("Unable to find field name for IndexableComponent");
		}

		Map<String, Object> fieldMap = getIndexableComponentMapping(accessibleObject, indexableComponent);
		if (fieldMap != null) {
			propertiesMap.put(fieldName, fieldMap);
		}
	}

	@SuppressWarnings("deprecation")
	private static Map<String, Object> getIndexableComponentMapping(AccessibleObject accessibleObject, IndexableComponent indexableComponent) {
		Class<?> fieldClazz = null;
		if (accessibleObject instanceof Field) {
			fieldClazz = ReflectionHelper.getGenericType((Field) accessibleObject);
		} else if (accessibleObject instanceof Method) {
			fieldClazz = ReflectionHelper.getGenericType((Method) accessibleObject);
		}
		if (fieldClazz == null) {
			throw new ElasticSearchOutputException("Unknown AccessibleObject type");
		}

		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("properties", getPropertiesMap(fieldClazz));
		if (indexableComponent.nested()) {
			fieldMap.put("type", "nested");
		} else {
			fieldMap.put("type", "object");
		}

		if (indexableComponent.dynamic() != DynamicEnum.NA) {
			fieldMap.put("dynamic", indexableComponent.dynamic().toString().toLowerCase());
		}

		if (!indexableComponent.enabled()) {
			fieldMap.put("enabled", Boolean.FALSE);
		}

		if (indexableComponent.path() != ObjectFieldPathEnum.NA) {
			fieldMap.put("path", indexableComponent.path().toString().toLowerCase());
		}

		if (indexableComponent.includeInAll() != IncludeInAllEnum.NA) {
			fieldMap.put("include_in_all", indexableComponent.includeInAll().toString().toLowerCase());
		}

		return fieldMap;
	}

	@SuppressWarnings("deprecation")
	private static void processIndexableProperties(AccessibleObject accessibleObject, Map<String, Object> propertiesMap) {
		IndexableProperties indexableProperties = accessibleObject.getAnnotation(IndexableProperties.class);
		if (indexableProperties == null) {
			throw new ElasticSearchOutputException("Unable to find annotation IndexableProperties");
		}
		if (indexableProperties.properties().length < 1) {
			throw new ElasticSearchOutputException("IndexableProperties must have at lease one IndexableProperty");
		}

		String fieldName = null;
		if (accessibleObject instanceof Field) {
			fieldName = ((Field) accessibleObject).getName();
		}
		if (!indexableProperties.name().isEmpty()) {
			fieldName = indexableProperties.name();
		}

		if (fieldName == null) {
			throw new ElasticSearchOutputException("Unable to find field name for IndexableProperties");
		}

		Map<String, Object> multiFieldMap = new HashMap<String, Object>();
		multiFieldMap.put("type", getFieldType(indexableProperties.type(), accessibleObject));

		if (indexableProperties.path() != MultiFieldPathEnum.NA) {
			multiFieldMap.put("path", indexableProperties.path().toString().toLowerCase());
		}

		boolean emptyNameProcessed = false;
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		for (IndexableProperty property : indexableProperties.properties()) {
			String propertyName = property.name();
			if (propertyName.isEmpty()) {
				if (!emptyNameProcessed) {
					emptyNameProcessed = true;
					propertyName = fieldName;
				} else {
					throw new ElasticSearchOutputException("Field name cannot be empty in multi-field");
				}
			}
			Map<String, Object> fieldMap = getIndexablePropertyMapping(accessibleObject, property);
			if (propertyName.equals(fieldName)) {
				multiFieldMap.putAll(fieldMap);
			} else {
				fieldsMap.put(propertyName, fieldMap);
			}
		}
		multiFieldMap.put("fields", fieldsMap);
		propertiesMap.put(fieldName, multiFieldMap);
	}

	private static String getFieldType(TypeEnum fieldTypeEnum, AccessibleObject accessibleObject) {
		String fieldType;

		if (fieldTypeEnum.equals(TypeEnum.AUTO)) {
			Class<?> fieldClass = null;
			if (accessibleObject instanceof Field) {
				fieldClass = ReflectionHelper.getGenericType((Field) accessibleObject);
			} else if (accessibleObject instanceof Method) {
				fieldClass = ReflectionHelper.getGenericType((Method) accessibleObject);
			}
			if (fieldClass == null) {
				throw new ElasticSearchOutputException("Unknown AccessibleObject type");
			}
			fieldType = fieldClass.getSimpleName().toLowerCase();
		} else {
			fieldType = fieldTypeEnum.toString().toLowerCase();
		}
		return fieldType;
	}
}