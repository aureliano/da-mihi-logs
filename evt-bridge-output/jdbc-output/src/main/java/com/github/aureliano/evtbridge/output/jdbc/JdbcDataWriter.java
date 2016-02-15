package com.github.aureliano.evtbridge.output.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.agent.AbstractDataWriter;
import com.github.aureliano.evtbridge.core.jdbc.ConnectionPool;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;

public class JdbcDataWriter extends AbstractDataWriter {

	private JdbcConnectionModel schemaConnection;
	private Connection connection;
	
	private static final Logger logger = Logger.getLogger(JdbcDataWriter.class);
	
	public JdbcDataWriter() {
		super();
	}

	@Override
	public void initializeResources() {
		this.initialize();
	}

	@Override
	public void finalizeResources() { }

	@SuppressWarnings("unchecked")
	@Override
	public void write(Object data) {
		this.insert((Map<String, Object>) data);
	}
	
	private void initialize() {
		this.schemaConnection = ((JdbcOutputConfig) super.outputConfiguration).getConnection();
		if (StringHelper.isEmpty(this.schemaConnection.getTable())) {
			throw new JdbcOutputException("Table attribute must be provided. Found empty string.");
		}
		logger.info("Table for inserting: " + this.schemaConnection.getTable());
	}
	
	private void insert(Map<String, Object> data) {
		try {
			if (this.connection == null) {
				this.connection = ConnectionPool.instance().getConnection(this.schemaConnection);
			}
			
			PreparedStatement stmt = JdbcHelper.createStatement(connection, this.schemaConnection.getTable(), data);
			stmt.executeUpdate();
			
			stmt.close();
		} catch (SQLException ex) {
			throw new JdbcOutputException(ex);
		}
	}
}