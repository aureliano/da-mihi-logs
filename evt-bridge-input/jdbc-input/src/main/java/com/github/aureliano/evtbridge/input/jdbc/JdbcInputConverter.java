package com.github.aureliano.evtbridge.input.jdbc;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.jdbc.JdbcConnectionModel;

public class JdbcInputConverter extends AbstractConfigInputConverter<JdbcInputConfig> {

	public JdbcInputConverter() {
		super();
	}

	@Override
	public JdbcInputConfig convert(Map<String, Object> data) {
		JdbcInputConfig conf = new JdbcInputConfig();
		
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
		return InputConfigTypes.JDBC.name();
	}
}