package com.github.aureliano.damihilogs.config.input;

public class JdbcConnectionModel {

	private String user;
	private String password;
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
	
	public String getUrl() {
		return url;
	}
	
	public JdbcConnectionModel withUrl(String url) {
		this.url = url;
		return this;
	}
	
	@Override
	public JdbcConnectionModel clone() {
		return new JdbcConnectionModel()
			.withUser(this.user)
			.withPassword(this.password)
			.withSql(this.sql)
			.withDriver(this.driver)
			.withUrl(this.url);
	}
}