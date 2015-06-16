package com.github.aureliano.damihilogs.config.output;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.JdbcConnectionModel;

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
	
	private JdbcConnectionModel createConnection() {
		return new JdbcConnectionModel()
			.withPassword("Px")
			.withDriver("org.postgresql.Driver")
			.withUser("tomas_torquemada")
			.withSql("insert into spanish");
	}
}