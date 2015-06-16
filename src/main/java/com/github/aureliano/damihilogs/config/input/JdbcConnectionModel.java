package com.github.aureliano.damihilogs.config.input;

public class JdbcConnectionModel {

	private String user;
	private String password;
	private String host;
	private String database;
	private Integer port;
	private String driver;
	private String sql;
	private String url;
	
	public JdbcConnectionModel() {
		super();
	}
	
	public String getUser() {
		return user;
	}

	public JdbcConnectionModel withUser(String user) {
		this.user = user;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public JdbcConnectionModel withPassword(String password) {
		this.password = password;
		return this;
	}
	
	public String getHost() {
		return host;
	}
	
	public JdbcConnectionModel withHost(String host) {
		this.host = host;
		return this;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public JdbcConnectionModel withDatabase(String database) {
		this.database = database;
		return this;
	}

	public Integer getPort() {
		return port;
	}

	public JdbcConnectionModel withPort(Integer port) {
		this.port = port;
		return this;
	}
	
	public String getSql() {
		return sql;
	}
	
	public JdbcConnectionModel withSql(String sql) {
		this.sql = sql;
		return this;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public JdbcConnectionModel withDriver(String driver) {
		this.driver = driver;
		return this;
	}
	
	public String getOptionalUrl() {
		return url;
	}
	
	public JdbcConnectionModel withOptionalUrl(String url) {
		this.url = url;
		return this;
	}
	
	@Override
	public JdbcConnectionModel clone() {
		return new JdbcConnectionModel()
			.withUser(this.user)
			.withPassword(this.password)
			.withHost(this.host)
			.withDatabase(this.database)
			.withPort(this.port)
			.withSql(this.sql)
			.withDriver(this.driver)
			.withOptionalUrl(this.url);
	}
}