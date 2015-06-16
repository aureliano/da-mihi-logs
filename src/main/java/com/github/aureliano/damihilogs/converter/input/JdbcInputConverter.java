package com.github.aureliano.damihilogs.converter.input;

import java.util.Map;

import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.config.input.JdbcConnectionModel;
import com.github.aureliano.damihilogs.config.input.JdbcInputConfig;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class JdbcInputConverter extends AbstractInputConverter<JdbcInputConfig> {

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
		return InputConfigTypes.JDBC_INPUT.name();
	}
}