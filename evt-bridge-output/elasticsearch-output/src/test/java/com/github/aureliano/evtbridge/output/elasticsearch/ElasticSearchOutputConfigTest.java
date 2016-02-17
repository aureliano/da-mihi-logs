package com.github.aureliano.evtbridge.output.elasticsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;

public class ElasticSearchOutputConfigTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testGetDefaults() {
		ElasticSearchOutputConfig c = new ElasticSearchOutputConfig();
		assertEquals("localhost", c.getHost());
		assertEquals(9200, c.getPort());
		assertFalse(c.isPrintElasticSearchLog());
	}
	
	@Test
	public void testConfiguration() {
		ElasticSearchOutputConfig c = new ElasticSearchOutputConfig()
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPrintElasticSearchLog(true);
		
		assertEquals("127.0.0.1", c.getHost());
		assertEquals(8080, c.getPort());
		assertTrue(c.isPrintElasticSearchLog());
	}
	
	@Test
	public void testClone() {
		ElasticSearchOutputConfig c1 = new ElasticSearchOutputConfig()
			.withHost("127.0.0.1")
			.withPort(8080)
			.withPrintElasticSearchLog(true)
			.putMetadata("test", "my test");
			
		ElasticSearchOutputConfig c2 = c1.clone();
		
		assertEquals(c1.getHost(), c2.getHost());
		assertEquals(c1.getPort(), c2.getPort());
		assertEquals(c1.isPrintElasticSearchLog(), c2.isPrintElasticSearchLog());
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
	}
	
	@Test
	public void testOutputType() {
		assertEquals(OutputConfigTypes.ELASTIC_SEARCH.name(), new ElasticSearchOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		ElasticSearchOutputConfig c = this.createValidConfiguration();
		assertTrue(this.validator.validate(c).isEmpty());
		
		this._testValidateIndex();
		this._testValidateMappingType();
	}

	private void _testValidateIndex() {
		ElasticSearchOutputConfig c = this.createValidConfiguration().withIndex(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		violations = this.validator.validate(c.withIndex(""));
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private void _testValidateMappingType() {
		ElasticSearchOutputConfig c = this.createValidConfiguration().withMappingType(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		violations = this.validator.validate(c.withMappingType(""));
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}

	private ElasticSearchOutputConfig createValidConfiguration() {
		return new ElasticSearchOutputConfig()
			.withIndex("index").withMappingType("type");
	}
}