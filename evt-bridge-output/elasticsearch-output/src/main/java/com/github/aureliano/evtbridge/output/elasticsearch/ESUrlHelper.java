package com.github.aureliano.evtbridge.output.elasticsearch;

public final class ESUrlHelper {

	private ESUrlHelper() {
		super();
	}
	
	public static String buildGetIndexUrl(IElasticSearchConfiguration configuration) {
		return new StringBuilder("http://")
			.append(configuration.getHost())
			.append(":")
			.append(configuration.getPort())
			.append("/")
			.append(configuration.getIndex())
			.toString();
	}
	
	public static String buildGetIndexTypeUrl(IElasticSearchConfiguration configuration, String mappingType) {
		return  new StringBuilder()
			.append(buildGetIndexUrl(configuration))
			.append("/")
			.append(mappingType)
			.toString();
	}
	
	public static String buildGetIndexTypePutUrl(IElasticSearchConfiguration configuration, String mappingType) {
		return new StringBuilder()
			.append(buildGetIndexUrl(configuration))
			.append("/_mapping/")
			.append(mappingType)
			.toString();
	}
	
	public static String buildPutDocumentUrl(IElasticSearchConfiguration configuration, String mappingType, String documentId) {
		return buildDocumentUrl(configuration, mappingType, documentId);
	}
	
	public static String buildDeleteDocumentUrl(IElasticSearchConfiguration configuration, String mappingType, String documentId) {
		return buildDocumentUrl(configuration, mappingType, documentId);
	}
	
	private static String buildDocumentUrl(IElasticSearchConfiguration configuration, String mappingType, String documentId) {
		return new StringBuilder()
			.append(buildGetIndexTypeUrl(configuration, mappingType))
			.append("/")
			.append(documentId)
			.toString();
	}
}