package com.github.aureliano.evtbridge.common.model;

import java.util.List;

public class CommonModel {

	public Integer id;
	public String name;
	private List<String> values;
	
	public CommonModel() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CommonModel withId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public CommonModel withName(String name) {
		this.name = name;
		return this;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public CommonModel withValues(List<String> values) {
		this.values = values;
		return this;
	}
}