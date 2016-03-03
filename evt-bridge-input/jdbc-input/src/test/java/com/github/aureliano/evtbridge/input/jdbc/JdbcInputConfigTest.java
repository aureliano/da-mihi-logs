package com.github.aureliano.evtbridge.input.jdbc;

import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.exception.IExceptionHandler;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;

public class JdbcInputConfigTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testInputType() {
		assertEquals(InputConfigTypes.JDBC_INPUT.name(), new JdbcInputConfig().id());
	}
	
	@Test
	public void testConfiguration() {
		JdbcInputConfig c = new JdbcInputConfig().withConnection(this.createConnection());
		
		assertEquals("Px", c.getConnection().getPassword());
		assertEquals("org.postgresql.Driver", c.getConnection().getDriver());
		assertEquals("tomas_torquemada", c.getConnection().getUser());
		assertEquals("select * from spanish where really_converted is false", c.getConnection().getSql());
	}
	
	@Test
	public void testClone() {
		JdbcInputConfig c1 = new JdbcInputConfig()
			.withConfigurationId("Px_t")
			.putMetadata("test", "my test")
			.addExceptionHandler(new IExceptionHandler() {
				public void captureException(Runnable runnable, IConfigInput inputConfig, Throwable trowable) { }
			});
		
		JdbcInputConfig c2 = c1.clone();
		
		assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	@Test
	public void testValidation() {
		JdbcInputConfig c = this.createValidConfiguration();
		assertTrue(this.validator.validate(c).isEmpty());
		
		this._testConnection();
		this._testInvalidDriver();
		this._testInvalidUser();
		this._testInvalidUrl();
	}
	
	private void _testConnection() {
		JdbcInputConfig c = this.createValidConfiguration().withConnection(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private void _testInvalidDriver() {
		JdbcInputConfig c = this.createValidConfiguration();
		c.getConnection().withDriver(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.getConnection().withDriver("");
		violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private void _testInvalidUser() {
		JdbcInputConfig c = this.createValidConfiguration();
		c.getConnection().withUser(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.getConnection().withUser("");
		violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private void _testInvalidUrl() {
		JdbcInputConfig c = this.createValidConfiguration();
		c.getConnection().withUrl(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.getConnection().withUrl("");
		violations = this.validator.validate(c);
		
		assertTrue(violations.size() == 1);
		assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private JdbcInputConfig createValidConfiguration() {
		return new JdbcInputConfig().withConnection(this.createConnection());
	}

	private JdbcConnectionModel createConnection() {
		return new JdbcConnectionModel()
			.withPassword("Px")
			.withDriver("org.postgresql.Driver")
			.withUser("tomas_torquemada")
			.withUrl("url")
			.withSql("select * from spanish where really_converted is false");
	}
}