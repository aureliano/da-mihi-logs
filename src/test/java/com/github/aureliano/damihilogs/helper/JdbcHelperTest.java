package com.github.aureliano.damihilogs.helper;

import junit.framework.Assert;

import org.junit.Test;

public class JdbcHelperTest {

	@Test
	public void testGetTableNameFromSql() {
		Assert.assertNull(JdbcHelper.getTableNameFromSql(""));
		String table = JdbcHelper.getTableNameFromSql("insert into anywhere values(1)");
		Assert.assertEquals("anywhere", table);
		
		table = JdbcHelper.getTableNameFromSql("INSERT into anywhere values(1)");
		Assert.assertEquals("anywhere", table);
	}
}