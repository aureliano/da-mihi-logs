package com.github.aureliano.damihilogs.converter.input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.config.input.TwitterInputConfig;

public class TwitterInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("id", "test-123");
		data.put("metadata", metadata);
		data.put("consumerKey", "consumer-key");
		data.put("consumerSecret", "consumer-secret");
		data.put("oauthToken", "oauth-token");
		data.put("oauthTokenSecret", "oauth-token-secret");
		data.put("track", new HashSet<String>(Arrays.asList("twitter")));
		
		data.put("type", InputConfigTypes.TWITTER_INPUT);
		
		TwitterInputConfig conf = new TwitterInputConverter().convert(data);
		
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));

		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals("consumer-key", conf.getConsumerKey());
		Assert.assertEquals("consumer-secret", conf.getConsumerSecret());
		Assert.assertEquals("oauth-token", conf.getOauthToken());
		Assert.assertEquals("oauth-token-secret", conf.getOauthTokenSecret());
		Assert.assertEquals("twitter", conf.getTrack().iterator().next());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(InputConfigTypes.TWITTER_INPUT.name(), new TwitterInputConverter().id());
	}
}