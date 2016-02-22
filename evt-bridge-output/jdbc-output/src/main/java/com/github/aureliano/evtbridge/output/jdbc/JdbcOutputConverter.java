package com.github.aureliano.evtbridge.output.jdbc;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigOutputConverter;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;

public class JdbcOutputConverter extends AbstractConfigOutputConverter<JdbcOutputConfig> {

	public JdbcOutputConverter() {
		super();
	}

	@Override
	public JdbcOutputConfig convert(Map<String, Object> data) {
		JdbcOutputConfig conf = new JdbcOutputConfig();
		
		this.configureObject(conf, data);
		Map<String, Object> connection = DataHelper.getAsHash(data, "connection");
		if (connection == null) {
			return conf;
		}
		
		JdbcConnectionModel model = new JdbcConnectionModel();
		String value = StringHelper.parse(connection.get("user"));
		if (!StringHelper.isEmpty(value)) {
			model.withUser(value);
		}
		
		value = StringHelper.parse(connection.get("password"));
		if (!StringHelper.isEmpty(value)) {
			model.withPassword(value);
		}
		
		value = StringHelper.parse(connection.get("sql"));
		if (!StringHelper.isEmpty(value)) {
			model.withSql(value);
		}
		
		value = StringHelper.parse(connection.get("driver"));
		if (!StringHelper.isEmpty(value)) {
			model.withDriver(value);
		}
		
		value = StringHelper.parse(connection.get("url"));
		if (!StringHelper.isEmpty(value)) {
			model.withUrl(value);
		}
		
		return conf.withConnection(model);
	}

	@Override
	public String id() {
		return OutputConfigTypes.JDBC_OUTPUT.name();
	}
}