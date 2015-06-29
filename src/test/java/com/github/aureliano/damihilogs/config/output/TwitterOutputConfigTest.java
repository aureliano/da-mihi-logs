package com.github.aureliano.damihilogs.config.output;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.listener.DefaultDataWritingListener;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;
import com.github.aureliano.damihilogs.validation.ObjectValidator;

public class TwitterOutputConfigTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testConfiguration() {
		TwitterOutputConfig c = new TwitterOutputConfig()
			.withConsumerKey("key")
			.withConsumerSecret("key_secret")
			.withOauthToken("token")
			.withOauthTokenSecret("token_secret")
			.withTweet("My status update");
		
		Assert.assertEquals("key", c.getConsumerKey());
		Assert.assertEquals("key_secret", c.getConsumerSecret());
		Assert.assertEquals("token", c.getOauthToken());
		Assert.assertEquals("token_secret", c.getOauthTokenSecret());
		Assert.assertEquals("My status update", c.getTweet());
	}
	
	@Test
	public void testClone() {
		TwitterOutputConfig c1 = new TwitterOutputConfig()
			.withConsumerKey("key")
			.withConsumerSecret("key_secret")
			.withOauthToken("token")
			.withOauthTokenSecret("token_secret")
			.withTweet("My status update")
			.putMetadata("test", "my test")
			.addDataWritingListener(new DefaultDataWritingListener());
		
		TwitterOutputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getConsumerKey(), c2.getConsumerKey());
		Assert.assertEquals(c1.getConsumerSecret(), c2.getConsumerSecret());
		Assert.assertEquals(c1.getOauthToken(), c2.getOauthToken());
		Assert.assertEquals(c1.getOauthTokenSecret(), c2.getOauthTokenSecret());
		Assert.assertEquals(c1.getTweet(), c2.getTweet());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getDataWritingListeners().size(), c2.getDataWritingListeners().size());
	}
	
	@Test
	public void testOutputType() {
		Assert.assertEquals(OutputConfigTypes.TWITTER_OUTPUT.name(), new TwitterOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		TwitterOutputConfig c = this.createValidConfiguration();
		Assert.assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateConsumerKey();
		this._testValidateConsumerSecret();
		this._testValidateOauthToken();
		this._testValidateOauthTokenSecret();
		this._testValidateTweet();
	}
	
	private void _testValidateConsumerKey() {
		TwitterOutputConfig c = this.createValidConfiguration().withConsumerKey(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withConsumerKey("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateConsumerSecret() {
		TwitterOutputConfig c = this.createValidConfiguration().withConsumerSecret(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withConsumerSecret("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateOauthToken() {
		TwitterOutputConfig c = this.createValidConfiguration().withOauthToken(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withOauthToken("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateOauthTokenSecret() {
		TwitterOutputConfig c = this.createValidConfiguration().withOauthTokenSecret(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withOauthTokenSecret("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateTweet() {
		TwitterOutputConfig c = this.createValidConfiguration().withTweet(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withTweet("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private TwitterOutputConfig createValidConfiguration() {
		return new TwitterOutputConfig()
			.withConsumerKey("my-consumer-key")
			.withConsumerSecret("my-consumer-secret")
			.withOauthToken("my-token")
			.withOauthTokenSecret("my-token-secret")
			.withTweet("My status update");
	}
}