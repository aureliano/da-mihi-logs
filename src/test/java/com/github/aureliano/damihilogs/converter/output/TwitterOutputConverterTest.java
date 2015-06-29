package com.github.aureliano.damihilogs.converter.output;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.config.output.TwitterOutputConfig;

public class TwitterOutputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("metadata", metadata);
		data.put("consumerKey", "consumer-key");
		data.put("consumerSecret", "consumer-secret");
		data.put("oauthToken", "oauth-token");
		data.put("oauthTokenSecret", "oauth-token-secret");
		data.put("tweet", "My status update");
		
		data.put("type", OutputConfigTypes.TWITTER_OUTPUT);
		
		TwitterOutputConfig conf = new TwitterOutputConverter().convert(data);
		
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));

		Assert.assertEquals("My status update", conf.getTweet());
		Assert.assertEquals("consumer-key", conf.getConsumerKey());
		Assert.assertEquals("consumer-secret", conf.getConsumerSecret());
		Assert.assertEquals("oauth-token", conf.getOauthToken());
		Assert.assertEquals("oauth-token-secret", conf.getOauthTokenSecret());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(OutputConfigTypes.TWITTER_OUTPUT.name(), new TwitterOutputConverter().id());
	}
}