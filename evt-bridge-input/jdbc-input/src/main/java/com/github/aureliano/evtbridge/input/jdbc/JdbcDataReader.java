package com.github.aureliano.evtbridge.input.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.aureliano.evtbridge.core.agent.AbstractDataReader;
import com.github.aureliano.evtbridge.core.data.ObjectMapperSingleton;
import com.github.aureliano.evtbridge.core.jdbc.ConnectionPool;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;

public class JdbcDataReader extends AbstractDataReader {

	private JdbcConnectionModel schemaConnection;
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
	public String readLine() {
		try {
			if (!this.resultSet.next()) {
				return null;
			}
			
			Map<String, Object> row = new HashMap<String, Object>();
			
			for (short i = 0; i < this.columns; i++) {
				row.put(this.columnNames[i], this.resultSet.getObject(i + 1));
			}
			
			return this.parseToJson(row);
		} catch (SQLException ex) {
			throw new JdbcInputException(ex);
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
			logger.info(">>> Closing result set.");
			this.resultSet.close();
		} catch (SQLException ex) {
			throw new JdbcInputException(ex);
		}
	}
	
	private void initialize() {
		this.schemaConnection = ((JdbcInputConfig) super.inputConfiguration).getConnection();
		this.prepareDataReading();
	}
	
	private void prepareDataReading() {
		try {
			logger.info("Executing query: " + this.schemaConnection.getSql());
			this.resultSet = ConnectionPool.instance().getConnection(this.schemaConnection)
					.createStatement().executeQuery(this.schemaConnection.getSql());
			
			ResultSetMetaData metaData = this.resultSet.getMetaData();
			this.columns = metaData.getColumnCount();
			
			this.columnNames = new String[this.columns];
			for (short i = 0; i < this.columns; i++) {
				this.columnNames[i] = metaData.getColumnLabel(i + 1);
			}
		} catch (SQLException ex) {
			throw new JdbcInputException(ex);
		}
	}
	
	private String parseToJson(Map<String, Object> map) {
		try {
			return ObjectMapperSingleton.instance().getObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException ex) {
			throw new JdbcInputException(ex);
		}
	}
}