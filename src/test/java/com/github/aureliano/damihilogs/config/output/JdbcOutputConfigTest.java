package com.github.aureliano.damihilogs.config.output;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.jdbc.JdbcConnectionModel;
import com.github.aureliano.damihilogs.validation.ConfigurationValidation;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;

public class JdbcOutputConfigTest {

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
		Assert.assertEquals(OutputConfigTypes.JDBC_OUTPUT.name(), new JdbcOutputConfig().id());
	}
	
	@Test
	public void testValidation() {
		JdbcOutputConfig c = this.createValidConfiguration();
		Assert.assertTrue(ConfigurationValidation.applyValidation(c).isEmpty());
		
		c.withConnection(null);
		Set<ConstraintViolation> violations = ConfigurationValidation.applyValidation(c);
		
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private JdbcOutputConfig createValidConfiguration() {
		return new JdbcOutputConfig().withConnection(new JdbcConnectionModel());
	}
	
	private JdbcConnectionModel createConnection() {
		return new JdbcConnectionModel()
			.withPassword("Px")
			.withDriver("org.postgresql.Driver")
			.withUser("tomas_torquemada")
			.withSql("insert into spanish");
	}
}