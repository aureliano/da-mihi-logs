package com.github.aureliano.damihilogs.helper;

import junit.framework.Assert;

import org.junit.Test;

public class JdbcHelperTest {

	@Test
	public void testGetTableNameFromSql() {
		String table = JdbcHelper.getTableNameFromSql("insert into anywhere values(1)");
		Assert.assertEquals("anywhere", table);
		
		Assert.assertNull(JdbcHelper.getTableNameFromSql(""));
	}
}