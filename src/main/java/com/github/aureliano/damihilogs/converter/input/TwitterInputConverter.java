package com.github.aureliano.damihilogs.converter.input;

import java.util.Map;
import java.util.Set;

import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.config.input.TwitterInputConfig;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class TwitterInputConverter extends AbstractInputConverter<TwitterInputConfig> {

	public TwitterInputConverter() {
		super();
	}

	@Override
	public TwitterInputConfig convert(Map<String, Object> data) {
		TwitterInputConfig conf = new TwitterInputConfig();
		
		super.configureObject(conf, data);
		
		if (data.get("track") != null) {
			Set<String> track = (Set<String>) data.get("track");
			for (String item : track) {
				conf.addTrack(item);
			}
		}
		
		return conf
			.withConsumerKey(StringHelper.parse(data.get("consumerKey")))
			.withConsumerSecret(StringHelper.parse(data.get("consumerSecret")))
			.withOauthToken(StringHelper.parse(data.get("oauthToken")))
			.withOauthTokenSecret(StringHelper.parse(data.get("oauthTokenSecret")));
	}

	@Override
	public String id() {
		return InputConfigTypes.TWITTER_INPUT.name();
	}
}