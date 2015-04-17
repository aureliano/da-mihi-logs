package com.github.aureliano.damihilogs.config.output;

import org.junit.Assert;
import org.junit.Test;

public class ElasticSearchOutputConfigTest {

	@Test
	public void testGetDefaults() {
		ElasticSearchOutputConfig c = new ElasticSearchOutputConfig();
		Assert.assertEquals("localhost", c.getHost());
		Assert.assertEquals(9200, c.getPort());
		Assert.assertFalse(c.isPrintElasticSearchLog());
	}
	
	@Test
	public void testConfiguration() {
		ElasticSearchOutputConfig c = new ElasticSearchOutputConfig()
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPrintElasticSearchLog(true);
		
		Assert.assertEquals("127.0.0.1", c.getHost());
		Assert.assertEquals(8080, c.getPort());
		Assert.assertTrue(c.isPrintElasticSearchLog());
	}
	
	@Test
	public void testClone() {
		ElasticSearchOutputConfig c1 = new ElasticSearchOutputConfig()
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPrintElasticSearchLog(true)
			.putMetadata("test", "my test");
			
		ElasticSearchOutputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getHost(), c2.getHost());
		Assert.assertEquals(c1.getPort(), c2.getPort());
		Assert.assertEquals(c1.isPrintElasticSearchLog(), c2.isPrintElasticSearchLog());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
	}
}