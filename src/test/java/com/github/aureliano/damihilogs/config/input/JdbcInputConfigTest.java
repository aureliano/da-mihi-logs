package com.github.aureliano.damihilogs.config.input;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.IExceptionHandler;

public class JdbcInputConfigTest {

	@Test
	public void testInputType() {
		Assert.assertEquals(InputConfigTypes.JDBC_INPUT.name(), new JdbcInputConfig().id());
	}
	
	@Test
	public void testConfiguration() {
		JdbcInputConfig c = new JdbcInputConfig().withConnection(this.createConnection());
		
		Assert.assertEquals("Px", c.getConnection().getPassword());
		Assert.assertEquals("org.postgresql.Driver", c.getConnection().getDriver());
		Assert.assertEquals("tomas_torquemada", c.getConnection().getUser());
		Assert.assertEquals("select * from spanish where really_converted is false", c.getConnection().getSql());
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
		
		Assert.assertEquals(c1.getConfigurationId(), c2.getConfigurationId());
		Assert.assertEquals(c1.getMetadata("test"), c2.getMetadata("test"));
		Assert.assertEquals(c1.getExceptionHandlers().size(), c2.getExceptionHandlers().size());
	}
	
	private JdbcConnectionModel createConnection() {
		return new JdbcConnectionModel()
			.withPassword("Px")
			.withDriver("org.postgresql.Driver")
			.withUser("tomas_torquemada")
			.withSql("select * from spanish where really_converted is false");
	}
}