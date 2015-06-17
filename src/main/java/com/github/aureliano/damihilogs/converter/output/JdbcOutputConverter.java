package com.github.aureliano.damihilogs.converter.output;

import java.util.Map;

import com.github.aureliano.damihilogs.config.input.JdbcConnectionModel;
import com.github.aureliano.damihilogs.config.output.JdbcOutputConfig;
import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class JdbcOutputConverter extends AbstractOutputConverter<JdbcOutputConfig> {

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