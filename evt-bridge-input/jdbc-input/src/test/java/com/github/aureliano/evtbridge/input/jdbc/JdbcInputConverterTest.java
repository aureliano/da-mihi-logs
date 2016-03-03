package com.github.aureliano.evtbridge.input.jdbc;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.InputConfigTypes;

public class JdbcInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		Map<String, Object> connection = new HashMap<String, Object>();
		connection.put("user", "usr");
		connection.put("password", "pwd");
		connection.put("driver", "sgdb.driver.class");
		connection.put("url", "jdbc:sgdb://host:1/test");
		connection.put("sql", "select * from anywhere");
		
		data.put("id", "test-123");
		data.put("metadata", metadata);
		data.put("connection", connection);
		
		data.put("type", InputConfigTypes.JDBC_INPUT);
		
		JdbcInputConfig conf = new JdbcInputConverter().convert(data);
		
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));

		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals("usr", conf.getConnection().getUser());
		Assert.assertEquals("pwd", conf.getConnection().getPassword());
		Assert.assertEquals("sgdb.driver.class", conf.getConnection().getDriver());
		Assert.assertEquals("jdbc:sgdb://host:1/test", conf.getConnection().getUrl());
		Assert.assertEquals("select * from anywhere", conf.getConnection().getSql());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(InputConfigTypes.JDBC_INPUT.name(), new JdbcInputConverter().id());
	}
}