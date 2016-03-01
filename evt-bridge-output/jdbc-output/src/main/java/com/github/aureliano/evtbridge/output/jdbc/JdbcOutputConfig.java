package com.github.aureliano.evtbridge.output.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.Valid;
import com.github.aureliano.evtbridge.core.config.IConfigOutput;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.filter.IEventFielter;
import com.github.aureliano.evtbridge.core.formatter.IOutputFormatter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;
import com.github.aureliano.evtbridge.core.parser.IParser;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.register.ServiceRegistration;

public class JdbcOutputConfig implements IConfigOutput {

	static {
		ServiceRegistration registration = new ServiceRegistration()
			.withId(OutputConfigTypes.JDBC.name())
			.withAgent(JdbcDataWriter.class)
			.withConfiguration(JdbcOutputConfig.class);
		
		ApiServiceRegistrator.instance().registrate(registration);
	}
	
	private JdbcConnectionModel connection;
	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	public JdbcOutputConfig() {
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
	}

	@Override
	public JdbcOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}

	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}

	@Override
	public String id() {
		return OutputConfigTypes.JDBC.name();
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public JdbcOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public JdbcOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return this.outputFormatter;
	}

	@Override
	public List<DataWritingListener> getDataWritingListeners() {
		return this.dataWritingListeners;
	}

	@Override
	public JdbcOutputConfig withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}

	@Override
	public JdbcOutputConfig addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}

	@Override
	public JdbcOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}
	
	@NotNull
	@Valid
	public JdbcConnectionModel getConnection() {
		return connection;
	}
	
	public JdbcOutputConfig withConnection(JdbcConnectionModel connection) {
		this.connection = connection;
		return this;
	}
	
	@Override
	public JdbcOutputConfig clone() {
		return new JdbcOutputConfig()
			.withParser(this.parser)
			.withFilter(this.filter)
			.withOutputFormatter(this.outputFormatter)
			.withDataWritingListeners(this.dataWritingListeners)
			.withMetadata(DataHelper.copyProperties(this.metadata))
			.withConnection((this.connection == null) ? null : this.connection.clone());
	}
	
	protected JdbcOutputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
}