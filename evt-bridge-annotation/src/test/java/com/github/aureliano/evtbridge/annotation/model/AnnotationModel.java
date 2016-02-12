package com.github.aureliano.evtbridge.annotation.model;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.evtbridge.annotation.validation.AssertFalse;
import com.github.aureliano.evtbridge.annotation.validation.AssertTrue;
import com.github.aureliano.evtbridge.annotation.validation.Decimal;
import com.github.aureliano.evtbridge.annotation.validation.Max;
import com.github.aureliano.evtbridge.annotation.validation.Min;
import com.github.aureliano.evtbridge.annotation.validation.NotEmpty;
import com.github.aureliano.evtbridge.annotation.validation.NotNull;
import com.github.aureliano.evtbridge.annotation.validation.Pattern;
import com.github.aureliano.evtbridge.annotation.validation.Size;

public class AnnotationModel {

	private String configurationId;
	private Double myDoubleField;
	private Boolean ok;
	private Boolean notOk;
	private List<Object> data;
	
	public AnnotationModel() {
		this.data = new ArrayList<>();
	}

	@NotNull
	@NotEmpty
	@Min(value = 3)
	@Max(value = 5)
	@Size(min = 3, max = 5)
	@Pattern(value = "[\\d\\w]{3,5}")
	public String getConfigurationId() {
		return this.configurationId;
	}

	public AnnotationModel withConfigurationId(String id) {
		this.configurationId = id;
		return this;
	}

	@AssertTrue
	public Boolean isOk() {
		return ok;
	}
	
	public AnnotationModel withOk(Boolean value) {
		this.ok = value;
		return this;
	}

	@AssertFalse
	public Boolean isNotOk() {
		return notOk;
	}
	
	public AnnotationModel withNotOk(Boolean value) {
		this.notOk = value;
		return this;
	}
	
	@Decimal(min = 4.5, max = 9.2)
	public Double getMyDoubleField() {
		return myDoubleField;
	}
	
	public AnnotationModel withMyDoubleField(Double myDoubleField) {
		this.myDoubleField = myDoubleField;
		return this;
	}

	@Min(value = 1)
	@Max(value = 1)
	@Size(min = 1, max = 1)
	public List<Object> getData() {
		return data;
	}

	public AnnotationModel withData(List<Object> data) {
		this.data = data;
		return this;
	}
}