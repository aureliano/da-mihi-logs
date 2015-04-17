package com.github.aureliano.damihilogs.es.annotations;

/**
 * "norms.enabled" field in mapping
 *
 * @see <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html">Mapping Core Types</a>
 */
public enum NormsEnabledEnum {
	/**
	 * Use default value in ElasticSearch
	 */
	NA,
	
	/**
	 * Set "norms.enabled" to "true"
	 */
	TRUE,
	
	/**
	 * Set "norms.enabled" to "false"
	 */
	FALSE
}