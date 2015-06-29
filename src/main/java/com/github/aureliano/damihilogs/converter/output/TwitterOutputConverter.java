package com.github.aureliano.damihilogs.converter.output;

import java.util.Map;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.config.output.TwitterOutputConfig;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class TwitterOutputConverter extends AbstractOutputConverter<TwitterOutputConfig> {

	public TwitterOutputConverter() {
		super();
	}

	@Override
	public TwitterOutputConfig convert(Map<String, Object> data) {
		TwitterOutputConfig conf = new TwitterOutputConfig();
		
		super.configureObject(conf, data);
		
		return conf
				.withConsumerKey(StringHelper.parse(data.get("consumerKey")))
				.withConsumerSecret(StringHelper.parse(data.get("consumerSecret")))
				.withOauthToken(StringHelper.parse(data.get("oauthToken")))
				.withOauthTokenSecret(StringHelper.parse(data.get("oauthTokenSecret")))
				.withTweet(StringHelper.parse(data.get("tweet")));
	}

	@Override
	public String id() {
		return OutputConfigTypes.TWITTER_OUTPUT.name();
	}
}