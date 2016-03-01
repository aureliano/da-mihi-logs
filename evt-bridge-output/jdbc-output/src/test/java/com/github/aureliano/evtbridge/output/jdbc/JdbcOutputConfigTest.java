package com.github.aureliano.evtbridge.output.jdbc;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;

public class JdbcOutputConfigTest {
	
	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testConfiguration() {
		JdbcOutputConfig c = new JdbcOutputConfig().withConnection(this.createConnection());
		
		Assert.assertEquals("Px", c.getConnection().getPassword());
		Assert.assertEquals("org.postgresql.Driver", c.getConnection().getDriver());
		Assert.assertEquals("tomas_torquemada", c.getConnection().getUser());
		Assert.assertEquals("insert into spanish", c.getConnection().getSql());
	}
	
	@Test
	public void testClone() {
		JdbcOutputConfig c1 = new JdbcOutputConfig()
			.putMetadata("test", "my test");
		
		JdbcOutputConfig c2 = c1.clone();
		
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
	}
	
	@Test
	public void testOutputType() {
		Assert.assertEquals(OutputConfigTypes.JDBC.name(), new JdbcOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		JdbcOutputConfig c = this.createValidConfiguration();
		Assert.assertTrue(this.validator.validate(c).isEmpty());
		
		this._testConnection();
		this._testInvalidDriver();
		this._testInvalidUser();
		this._testInvalidUrl();
	}
	
	private void _testConnection() {
		JdbcOutputConfig c = this.createValidConfiguration().withConnection(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private void _testInvalidDriver() {
		JdbcOutputConfig c = this.createValidConfiguration();
		c.getConnection().withDriver(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.getConnection().withDriver("");
		violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private void _testInvalidUser() {
		JdbcOutputConfig c = this.createValidConfiguration();
		c.getConnection().withUser(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.getConnection().withUser("");
		violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private void _testInvalidUrl() {
		JdbcOutputConfig c = this.createValidConfiguration();
		c.getConnection().withUrl(null);
		Set<ConstraintViolation> violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
		
		c.getConnection().withUrl("");
		violations = this.validator.validate(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotEmpty.class, violations.iterator().next().getValidator());
	}
	
	private JdbcOutputConfig createValidConfiguration() {
		return new JdbcOutputConfig().withConnection(this.createConnection());
	}

	private JdbcConnectionModel createConnection() {
		return new JdbcConnectionModel()
			.withPassword("Px")
			.withDriver("org.postgresql.Driver")
			.withUser("tomas_torquemada")
			.withUrl("url")
			.withSql("insert into spanish");
	}
}