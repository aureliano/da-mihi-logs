package com.github.aureliano.evtbridge.input.url;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.ConnectionSchema;

public class UrlInputHelperTest {

	@Test
	public void testBuildUrl() {
		UrlInputConfig c = new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTP)
			.withHost("www.google.com");
		assertEquals("http://www.google.com", UrlInputHelper.buildUrl(c));
		
		c.withConnectionSchema(ConnectionSchema.HTTPS)
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPath("logs")
			.addParameter("year", "2015")
			.addParameter("another", "some text");
		String url = "https://127.0.0.1:8080/logs?year=2015&another=some+text";
		assertEquals(url, UrlInputHelper.buildUrl(c));
		
		c.withHost("127.0.0.1/").withPath("/logs");
		assertEquals(url, UrlInputHelper.buildUrl(c));
	}
}