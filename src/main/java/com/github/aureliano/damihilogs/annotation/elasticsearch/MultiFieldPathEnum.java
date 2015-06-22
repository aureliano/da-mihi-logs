package com.github.aureliano.damihilogs.annotation.elasticsearch;

/**
 * "path" in multi_field mapping
 *
 * @see <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-multi-field-type.html#_accessing_fields">Accessing fields</a>
 */
public enum MultiFieldPathEnum {
	/**
	 * Use default value in ElasticSearch
	 */
	NA,
	
	/**
	 * Set "path" to "just_name"
	 */
	JUST_NAME,
	
	/**
	 * Set "path" to "full"
	 */
	FULL
}