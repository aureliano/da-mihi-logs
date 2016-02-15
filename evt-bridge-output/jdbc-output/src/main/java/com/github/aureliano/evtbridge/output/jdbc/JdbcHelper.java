package com.github.aureliano.evtbridge.output.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JdbcHelper {

	private JdbcHelper() {
		super();
	}
	
	public static PreparedStatement createStatement(Connection connection, String table, Map<String, Object> data) {
		StringBuilder sql = new StringBuilder("insert into ")
		.append(table)
		.append("(");
	
		List<String> keys = new ArrayList<String>(data.size());
		int count = 0;
		
		for (String key : data.keySet()) {
			keys.add(key);
			
			if (count > 0) {
				sql.append(",");
			}
			
			count++;
			sql.append(key);
		}
		
		sql.append(") values(");
		
		for (short i = 0; i < count; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append("?");
		}
		
		sql.append(")");
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			count = 1;
			for (String key : keys) {
				stmt.setObject(count++, data.get(key));
			}
			
			return stmt;
		} catch (SQLException ex) {
			throw new JdbcOutputException(ex);
		}
	}
}