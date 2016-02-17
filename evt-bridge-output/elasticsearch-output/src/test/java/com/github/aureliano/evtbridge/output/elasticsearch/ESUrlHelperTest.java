package com.github.aureliano.evtbridge.output.elasticsearch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.evtbridge.output.elasticsearch.ElasticSearchOutputConfig;;

public class ESUrlHelperTest {

	private IElasticSearchConfiguration configuration;
	
	public ESUrlHelperTest() {
		this.configuration = new ElasticSearchOutputConfig()
			.withHost("localhost")
			.withPort(9200)
			.withIndex("test");
	}
	
	@Test
	public void testBuildGetIndexUrl() {
		String expected = "http://localhost:9200/test";
		String actual = ESUrlHelper.buildGetIndexUrl(this.configuration);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildGetIndexTypeUrl() {
		String expected = "http://localhost:9200/test/any_type";
		String actual = ESUrlHelper.buildGetIndexTypeUrl(this.configuration, "any_type");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildGetIndexTypePutUrl() {
		String expected = "http://localhost:9200/test/_mapping/any_type";
		String actual = ESUrlHelper.buildGetIndexTypePutUrl(this.configuration, "any_type");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildPutDocumentUrl() {
		String expected = "http://localhost:9200/test/any_type/1";
		String actual = ESUrlHelper.buildPutDocumentUrl(this.configuration, "any_type", "1");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildDeleteDocumentUrl() {
		String expected = "http://localhost:9200/test/any_type/1";
		String actual = ESUrlHelper.buildDeleteDocumentUrl(this.configuration, "any_type", "1");
		
		assertEquals(expected, actual);
	}
}