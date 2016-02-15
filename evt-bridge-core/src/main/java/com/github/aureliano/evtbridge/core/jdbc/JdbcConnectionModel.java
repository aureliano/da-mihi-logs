package com.github.aureliano.evtbridge.core.jdbc;

import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;

public class JdbcConnectionModel {

	private String user;
	private String password;
	private String driver;
	private String sql;
	private String url;
	private String table;
	
	public JdbcConnectionModel() {
		super();
	}
	
	@NotEmpty
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
	
	@NotEmpty
	public String getDriver() {
		return driver;
	}
	
	public JdbcConnectionModel withDriver(String driver) {
		this.driver = driver;
		return this;
	}
	
	@NotEmpty
	public String getUrl() {
		return url;
	}
	
	public JdbcConnectionModel withUrl(String url) {
		this.url = url;
		return this;
	}
	
	public String getTable() {
		return table;
	}
	
	public JdbcConnectionModel withTable(String table) {
		this.table = table;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JdbcConnectionModel other = (JdbcConnectionModel) obj;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}