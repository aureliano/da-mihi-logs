package com.github.aureliano.damihilogs.executor.writer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.output.JdbcOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.JdbcHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.jdbc.JdbcConnectionModel;

public class JdbcDataWriter extends AbstractDataWriter {

	private JdbcConnectionModel schemaConnection;
	private Connection connection;
	private String table;
	
	private static final Logger logger = Logger.getLogger(JdbcDataWriter.class);
	
	public JdbcDataWriter() {
		super();
	}

	@Override
	public void initializeResources() {
		this.initialize();
	}

	@Override
	public void finalizeResources() {
		try {
			this.connection.close();
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(Object data) {
		this.insert((Map<String, Object>) data);
	}
	
	private void initialize() {
		this.schemaConnection = ((JdbcOutputConfig) super.outputConfiguration).getConnection();
		
		logger.info("Connecting to //" + this.schemaConnection.getUrl());
		logger.info("Inserting: " + this.schemaConnection.getSql());
		logger.debug("Driver: " + this.schemaConnection.getDriver());
		logger.debug("User: " + this.schemaConnection.getUser());
		
		this.registrateDriver();
		this.openDatabaseConnection();
		this.prepareDataWriting();
	}
	
	private void insert(Map<String, Object> data) {
		try {
			PreparedStatement stmt = JdbcHelper.createStatement(this.connection, this.table, data);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void registrateDriver() {
		try {
			Class.forName(this.schemaConnection.getDriver());
		} catch (ClassNotFoundException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void openDatabaseConnection() {
		try {
			if (!StringHelper.isEmpty(this.schemaConnection.getUser())) {
				this.connection = DriverManager.getConnection(
					this.schemaConnection.getUrl(),
					this.schemaConnection.getUser(),
					this.schemaConnection.getPassword()
				);
			} else {
				this.connection = DriverManager.getConnection(this.schemaConnection.getUrl());
			}
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void prepareDataWriting() {
		this.table = JdbcHelper.getTableNameFromSql(this.schemaConnection.getSql());
	}
}