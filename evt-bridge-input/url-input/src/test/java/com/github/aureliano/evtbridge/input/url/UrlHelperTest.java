package com.github.aureliano.evtbridge.input.url;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.ConnectionSchema;

public class UrlHelperTest {

	@Test
	public void testBuildUrl() {
		UrlInputConfig c = new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTP)
			.withHost("www.google.com");
		Assert.assertEquals("http://www.google.com", UrlHelper.buildUrl(c));
		
		c.withConnectionSchema(ConnectionSchema.HTTPS)
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPath("logs")
			.addParameter("year", "2015")
			.addParameter("another", "some text");
		String url = "https://127.0.0.1:8080/logs?year=2015&another=some+text";
		Assert.assertEquals(url, UrlHelper.buildUrl(c));
		
		c.withHost("127.0.0.1/").withPath("/logs");
		Assert.assertEquals(url, UrlHelper.buildUrl(c));
	}
}