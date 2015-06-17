package com.github.aureliano.damihilogs.executor.writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.output.JdbcOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.JdbcHelper;
import com.github.aureliano.damihilogs.jdbc.ConnectionPool;
import com.github.aureliano.damihilogs.jdbc.JdbcConnectionModel;

public class JdbcDataWriter extends AbstractDataWriter {

	private JdbcConnectionModel schemaConnection;
	private String table;
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
		logger.info("Insert query seed: " + this.schemaConnection.getSql());
		this.prepareDataWriting();
	}
	
	private void insert(Map<String, Object> data) {
		try {
			if (this.connection == null) {
				this.connection = ConnectionPool.instance().getConnection(this.schemaConnection);
			}
			
			PreparedStatement stmt = JdbcHelper.createStatement(connection, this.table, data);
			stmt.executeUpdate();
			
			stmt.close();
		} catch (SQLException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void prepareDataWriting() {
		this.table = JdbcHelper.getTableNameFromSql(this.schemaConnection.getSql());
	}
}