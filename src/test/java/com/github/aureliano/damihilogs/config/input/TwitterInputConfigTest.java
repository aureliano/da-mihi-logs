package com.github.aureliano.damihilogs.config.input;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.config.BoundingBoxLocation;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;
import com.github.aureliano.damihilogs.validation.ObjectValidator;

public class TwitterInputConfigTest {
	
	ObjectValidator validator = ObjectValidator.instance();
	
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
			})
			.addTrack("test1")
			.addTrack("test2")
			.addBoundingBoxLocation(new BoundingBoxLocation());
		
		TwitterInputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.isUseLastExecutionRecords(), c2.isUseLastExecutionRecords());
		Assert.assertEquals(c1.getConsumerKey(), c2.getConsumerKey());
		Assert.assertEquals(c1.getConsumerSecret(), c2.getConsumerSecret());
		Assert.assertEquals(c1.getOauthToken(), c2.getOauthToken());
		Assert.assertEquals(c1.getOauthTokenSecret(), c2.getOauthTokenSecret());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
		Assert.assertEquals(c1.getTrack().size(), c2.getTrack().size());
		Assert.assertEquals(c1.getBoundinBoxLocation().size(), c2.getBoundinBoxLocation().size());
	}
	
	@Test
	public void testInputType() {
		Assert.assertEquals(InputConfigTypes.TWITTER_INPUT.name(), new TwitterInputConfig().id());
	}
	
	@Test
	public void testValidation() {
		TwitterInputConfig c = this.createValidConfiguration();
		Assert.assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateConsumerKey();
		this._testValidateConsumerSecret();
		this._testValidateOauthToken();
		this._testValidateOauthTokenSecret();
	}
	
	private void _testValidateConsumerKey() {
		TwitterInputConfig c = this.createValidConfiguration().withConsumerKey(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withConsumerKey("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateConsumerSecret() {
		TwitterInputConfig c = this.createValidConfiguration().withConsumerSecret(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withConsumerSecret("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateOauthToken() {
		TwitterInputConfig c = this.createValidConfiguration().withOauthToken(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withOauthToken("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateOauthTokenSecret() {
		TwitterInputConfig c = this.createValidConfiguration().withOauthTokenSecret(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.withOauthTokenSecret("");
		violations = this.validator.validate(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private TwitterInputConfig createValidConfiguration() {
		return new TwitterInputConfig()
			.withConsumerKey("my-consumer-key")
			.withConsumerSecret("my-consumer-secret")
			.withOauthToken("my-token")
			.withOauthTokenSecret("my-token-secret");
	}
}