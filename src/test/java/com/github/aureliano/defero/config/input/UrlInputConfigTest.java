package com.github.aureliano.defero.config.input;

import org.junit.Assert;
import org.junit.Test;

public class UrlInputConfigTest {

	@Test
	public void testGetDefaults() {
		UrlInputConfig c = new UrlInputConfig();
		Assert.assertEquals(ConnectionSchema.HTTP, c.getConnectionSchema());
		Assert.assertEquals(-1, c.getPort());
		Assert.assertNotNull(c.getParameters());
		Assert.assertTrue(c.getParameters().isEmpty());
		Assert.assertEquals(UrlInputConfig.DEFAULT_READ_TIMEOUT, c.getReadTimeout());
		Assert.assertFalse(c.isNoCheckCertificate());
	}
	
	@Test
	public void testConfiguration() {
		UrlInputConfig c = new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTPS)
			.withHost("localhost")
			.withPort(8080)
			.withPath("logs")
			.addParameter("test", "Is it a test?")
			.withReadTimeout(5 * 1000)
			.withUser("user_name")
			.withPassword("my-password")
			.withNoCheckCertificate(true);
		
		
		Assert.assertEquals(ConnectionSchema.HTTPS, c.getConnectionSchema());
		Assert.assertEquals("localhost", c.getHost());
		Assert.assertEquals(8080, c.getPort());
		Assert.assertEquals("logs", c.getPath());
		Assert.assertTrue(c.getParameters().size() == 1);
		Assert.assertEquals("Is it a test?", c.getParameters().get("test"));
		Assert.assertEquals(5000, c.getReadTimeout());
		Assert.assertEquals("user_name", c.getUser());
		Assert.assertEquals("my-password", c.getPassword());
		Assert.assertTrue(c.isNoCheckCertificate());
	}
}