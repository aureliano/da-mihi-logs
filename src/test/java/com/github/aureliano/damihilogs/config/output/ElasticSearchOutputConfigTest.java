package com.github.aureliano.damihilogs.config.output;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.validation.ConfigurationValidation;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;

public class ElasticSearchOutputConfigTest {

	@Test
	public void testGetDefaults() {
		ElasticSearchOutputConfig c = new ElasticSearchOutputConfig();
		Assert.assertEquals("localhost", c.getHost());
		Assert.assertEquals(9200, c.getPort());
		Assert.assertFalse(c.isPrintElasticSearchLog());
	}
	
	@Test
	public void testConfiguration() {
		ElasticSearchOutputConfig c = new ElasticSearchOutputConfig()
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPrintElasticSearchLog(true);
		
		Assert.assertEquals("127.0.0.1", c.getHost());
		Assert.assertEquals(8080, c.getPort());
		Assert.assertTrue(c.isPrintElasticSearchLog());
	}
	
	@Test
	public void testClone() {
		ElasticSearchOutputConfig c1 = new ElasticSearchOutputConfig()
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPrintElasticSearchLog(true)
			.putMetadata("test", "my test");
			
		ElasticSearchOutputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getHost(), c2.getHost());
		Assert.assertEquals(c1.getPort(), c2.getPort());
		Assert.assertEquals(c1.isPrintElasticSearchLog(), c2.isPrintElasticSearchLog());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
	}
	
	@Test
	public void testOutputType() {
		Assert.assertEquals(OutputConfigTypes.ELASTIC_SEARCH.name(), new ElasticSearchOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		ElasticSearchOutputConfig c = this.createValidConfiguration();
		Assert.assertTrue(ConfigurationValidation.applyValidation(c).isEmpty());
		
		this._testValidateIndex();
		this._testValidateMappingType();
	}

	private void _testValidateIndex() {
		ElasticSearchOutputConfig c = this.createValidConfiguration().withIndex(null);
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		violations = ConfigurationValidation.applyValidation(c.withIndex(""));
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateMappingType() {
		ElasticSearchOutputConfig c = this.createValidConfiguration().withMappingType(null);
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation(c);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		violations = ConfigurationValidation.applyValidation(c.withMappingType(""));
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private ElasticSearchOutputConfig createValidConfiguration() {
		return new ElasticSearchOutputConfig()
			.withIndex("index").withMappingType("type");
	}
}