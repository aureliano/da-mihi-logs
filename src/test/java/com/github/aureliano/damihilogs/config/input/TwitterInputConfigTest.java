package com.github.aureliano.damihilogs.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public class TwitterInputConfigTest {

	@Test
	public void testGetDefaults() {
		Assert.assertFalse(new TwitterInputConfig().isUseLastExecutionRecords());
	}
	
	@Test
	public void testConfiguration() {
		TwitterInputConfig c = new TwitterInputConfig()
			.withConfigurationId("my-twitter-cfg")
			.withUseLastExecutionRecords(true)
			.withConsumerKey("key")
			.withConsumerSecret("key_secret")
			.withOauthToken("token")
			.withOauthTokenSecret("token_secret");
		
		Assert.assertEquals("my-twitter-cfg", c.getConfigurationId());
		Assert.assertEquals(true, c.isUseLastExecutionRecords());
		Assert.assertEquals("key", c.getConsumerKey());
		Assert.assertEquals("key_secret", c.getConsumerSecret());
		Assert.assertEquals("token", c.getOauthToken());
		Assert.assertEquals("token_secret", c.getOauthTokenSecret());
	}
	
	@Test
	public void testClone() {
		TwitterInputConfig c1 = new TwitterInputConfig()
			.withConfigurationId("my-twitter-cfg")
			.withUseLastExecutionRecords(true)
			.withConsumerKey("key")
			.withConsumerSecret("key_secret")
			.withOauthToken("token")
			.withOauthTokenSecret("token_secret")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		TwitterInputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.isUseLastExecutionRecords(), c2.isUseLastExecutionRecords());
		Assert.assertEquals(c1.getConsumerKey(), c2.getConsumerKey());
		Assert.assertEquals(c1.getConsumerSecret(), c2.getConsumerSecret());
		Assert.assertEquals(c1.getOauthToken(), c2.getOauthToken());
		Assert.assertEquals(c1.getOauthTokenSecret(), c2.getOauthTokenSecret());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testInputType() {
		Assert.assertEquals(InputConfigTypes.TWITTER_INPUT.name(), new TwitterInputConfig().id());
	}
}