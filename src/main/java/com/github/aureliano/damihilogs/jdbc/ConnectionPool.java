package com.github.aureliano.damihilogs.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class ConnectionPool {

	private static ConnectionPool instance;
	private static final Logger logger = Logger.getLogger(ConnectionPool.class);
	
	private Map<JdbcConnectionModel, Connection> pool;
	
	private ConnectionPool() {
		logger.info("Initializing JDBC connection pool.");
		this.pool = new HashMap<JdbcConnectionModel, Connection>();
	}
	
	public final static synchronized ConnectionPool instance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		
		return instance;
	}
	
	public static boolean isInitialized() {
		return (instance != null);
	}
	
	public synchronized Connection getConnection(JdbcConnectionModel schema) {
		Connection connection = this.pool.get(schema);
		
		if (connection == null) {
			connection = this.openConnection(schema);
			this.pool.put(schema, connection);
		}
		
		return connection;
	}
	
	public synchronized void closeConnections() {
		logger.info("Closing " + this.pool.size() + " JDBC connections in the pool.");
		
		for (Connection connection : this.pool.values()) {
			try {
				connection.close();
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		this.pool.clear();
	}

	private Connection openConnection(JdbcConnectionModel schema) {
		try {
			logger.info("Registrating JDBC driver " + schema.getDriver());
			Class.forName(schema.getDriver());
			
			Connection connection = null;
			if (!StringHelper.isEmpty(schema.getUser())) {
				logger.info("Connecting to " + schema.getUrl() + " with user " + schema.getUser() + " and password " + schema.getPassword());
				connection = DriverManager.getConnection(
					schema.getUrl(),
					schema.getUser(),
					schema.getPassword()
				);
			} else {
				logger.info("Connecting to " + schema.getUrl());
				connection = DriverManager.getConnection(schema.getUrl());
			}
			
			return connection;
		} catch (ClassNotFoundException ex) {
			throw new DaMihiLogsException(ex);
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}