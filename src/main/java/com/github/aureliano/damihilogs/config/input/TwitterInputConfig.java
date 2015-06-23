package com.github.aureliano.damihilogs.config.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class TwitterInputConfig implements IConfigInput {

	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	private IMatcher matcher;
	private List<IExceptionHandler> exceptionHandlers;
	private List<DataReadingListener> dataReadingListeners;
	private List<ExecutionListener> inputExecutionListeners;
	
	private String consumerKey;
	private String consumerSecret;
	private String oauthToken;
	private String oauthTokenSecret;
	
	public TwitterInputConfig() {
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		
		this.exceptionHandlers = new ArrayList<IExceptionHandler>();
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.inputExecutionListeners = new ArrayList<ExecutionListener>();
	}

	@Override
	public TwitterInputConfig putMetadata(String key, String value) {
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
		return InputConfigTypes.TWITTER_INPUT.name();
	}

	@Override
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public TwitterInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public IMatcher getMatcher() {
		return this.matcher;
	}

	@Override
	public TwitterInputConfig withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public TwitterInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@Override
	public TwitterInputConfig addExceptionHandler(IExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
		return this;
	}

	@Override
	public List<IExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}

	@Override
	public List<DataReadingListener> getDataReadingListeners() {
		return this.dataReadingListeners;
	}

	@Override
	public TwitterInputConfig withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}

	@Override
	public TwitterInputConfig addDataReadingListener(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}

	@Override
	public List<ExecutionListener> getExecutionListeners() {
		return this.inputExecutionListeners;
	}

	@Override
	public TwitterInputConfig withExecutionListeners(List<ExecutionListener> inputExecutionListeners) {
		this.inputExecutionListeners = inputExecutionListeners;
		return this;
	}

	@Override
	public TwitterInputConfig addExecutionListener(ExecutionListener listener) {
		this.inputExecutionListeners.add(listener);
		return this;
	}
	
	public String getConsumerKey() {
		return this.consumerKey;
	}
	
	public TwitterInputConfig withConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
		return this;
	}
	
	public String getConsumerSecret() {
		return this.consumerSecret;
	}
	
	public TwitterInputConfig withConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
		return this;
	}
	
	public String getOauthToken() {
		return this.oauthToken;
	}
	
	public TwitterInputConfig withOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
		return this;
	}
	
	public String getOauthTokenSecret() {
		return this.oauthTokenSecret;
	}
	
	public TwitterInputConfig withOauthTokenSecret(String oauthTokenSecret) {
		this.oauthTokenSecret = oauthTokenSecret;
		return this;
	}
	
	@Override
	public TwitterInputConfig clone() {
		return new TwitterInputConfig()
			.withConfigurationId(this.id)
			.withUseLastExecutionRecords(this.useLastExecutionRecords)
			.withConsumerKey(this.consumerKey)
			.withConsumerSecret(this.consumerSecret)
			.withOauthToken(this.oauthToken)
			.withOauthTokenSecret(this.oauthTokenSecret)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withMatcher(this.matcher)
			.withExceptionHandlers(this.exceptionHandlers)
			.withDataReadingListeners(this.dataReadingListeners)
			.withExecutionListeners(this.inputExecutionListeners);
	}
	
	protected TwitterInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	protected TwitterInputConfig withExceptionHandlers(List<IExceptionHandler> handlers) {
		this.exceptionHandlers = handlers;
		return this;
	}
}