package com.github.aureliano.evtbridge.output.elasticsearch.enumeration;

public enum DocValuesFormatEnum {

	/**
	 * Use default value in ElasticSearch
	 */
	NA,
	
	/**
	 * Set "doc_values_format" to "memory"
	 */
	MEMORY,
	
	/**
	 * Set "doc_values_format" to "disk"
	 */
	DISK,
	
	/**
	 * Set "doc_values_format" to "default"
	 */
	DEFAULT
}