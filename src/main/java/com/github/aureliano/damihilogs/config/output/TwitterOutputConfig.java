package com.github.aureliano.damihilogs.config.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public class TwitterOutputConfig implements IConfigOutput {

	private Properties metadata;
	private IParser<?> parser;
	private IEventFielter filter;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	private String consumerKey;
	private String consumerSecret;
	private String oauthToken;
	private String oauthTokenSecret;
	private String tweet;
	
	public TwitterOutputConfig() {
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
	}

	@Override
	public TwitterOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}

	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public String id() {
		return OutputConfigTypes.TWITTER_OUTPUT.name();
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public TwitterOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public TwitterOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}

	@Override
	public List<DataWritingListener> getDataWritingListeners() {
		return this.dataWritingListeners;
	}

	@Override
	public TwitterOutputConfig withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}

	@Override
	public TwitterOutputConfig addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}

	@Override
	public TwitterOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}
	
	@NotEmpty
	public String getConsumerKey() {
		return this.consumerKey;
	}
	
	public TwitterOutputConfig withConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
		return this;
	}

	@NotEmpty
	public String getConsumerSecret() {
		return this.consumerSecret;
	}
	
	public TwitterOutputConfig withConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
		return this;
	}

	@NotEmpty
	public String getOauthToken() {
		return this.oauthToken;
	}
	
	public TwitterOutputConfig withOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
		return this;
	}

	@NotEmpty
	public String getOauthTokenSecret() {
		return this.oauthTokenSecret;
	}
	
	public TwitterOutputConfig withOauthTokenSecret(String oauthTokenSecret) {
		this.oauthTokenSecret = oauthTokenSecret;
		return this;
	}
	
	@NotEmpty
	public String getTweet() {
		return this.tweet;
	}
	
	public TwitterOutputConfig withTweet(String tweet) {
		this.tweet = tweet;
		return this;
	}
	
	@Override
	public TwitterOutputConfig clone() {
		return new TwitterOutputConfig()
			.withParser(this.parser)
			.withFilter(this.filter)
			.withOutputFormatter(this.outputFormatter)
			.withDataWritingListeners(this.dataWritingListeners)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withConsumerKey(this.consumerKey)
			.withConsumerSecret(this.consumerSecret)
			.withOauthToken(this.oauthToken)
			.withOauthTokenSecret(this.oauthTokenSecret)
			.withTweet(this.tweet);
	}
	
	protected TwitterOutputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
}