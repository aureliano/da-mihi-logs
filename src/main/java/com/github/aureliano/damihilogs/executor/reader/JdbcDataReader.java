package com.github.aureliano.damihilogs.executor.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.aureliano.damihilogs.config.input.JdbcConnectionModel;
import com.github.aureliano.damihilogs.config.input.JdbcInputConfig;
import com.github.aureliano.damihilogs.data.ObjectMapperSingleton;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class JdbcDataReader extends AbstractDataReader {

	private JdbcConnectionModel schemaConnection;
	private Connection connection;
	private ResultSet resultSet;
	private int columns;
	private String[] columnNames;
	
	private static final Logger logger = Logger.getLogger(JdbcDataReader.class);
	
	public JdbcDataReader() {
		super();
	}
	
	@Override
	public void initializeResources() {
		this.initialize();
	}
	
	@Override
	public String nextData() {
		String line = super.readNextLine();
		
		if (line == null) {
			super.markedToStop = true;
			return null;
		}
		
		return super.prepareLogEvent(line);
	}
	
	@Override
	public String readLine() {
		try {
			if (!this.resultSet.next()) {
				return null;
			}
			
			Map<String, Object> row = new HashMap<String, Object>();
			
			for (short i = 0; i < this.columns; i++) {
				row.put(this.columnNames[i], this.resultSet.getObject(i));
			}
			
			return this.parseToJson(row);
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	@Override
	public void loadLastExecutionLog(Properties properties) { }
	
	@Override
	public Map<String, Object> executionLog() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public void finalizeResources() {
		try {
			this.resultSet.close();
			this.connection.close();
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void initialize() {
		this.schemaConnection = ((JdbcInputConfig) super.inputConfiguration).getConnection();
		
		logger.info("Connecting to //" + this.schemaConnection.getUrl());
		logger.info("Querying: " + this.schemaConnection.getSql());
		logger.debug("Driver: " + this.schemaConnection.getDriver());
		logger.debug("User: " + this.schemaConnection.getUser());
		
		this.registrateDriver();
		this.openDatabaseConnection();
		this.prepareDataReading();
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
	
	private void prepareDataReading() {
		try {
			this.resultSet = this.connection.createStatement().executeQuery(this.schemaConnection.getSql());
			
			ResultSetMetaData metaData = this.resultSet.getMetaData();
			this.columns = metaData.getColumnCount();
			
			this.columnNames = new String[this.columns];
			for (short i = 0; i < this.columns; i++) {
				this.columnNames[i] = metaData.getColumnLabel(i);
			}
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private String parseToJson(Map<String, Object> map) {
		try {
			return ObjectMapperSingleton.instance().getObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}