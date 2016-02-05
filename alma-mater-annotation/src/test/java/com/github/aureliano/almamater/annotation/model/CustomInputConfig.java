package com.github.aureliano.almamater.annotation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.almamater.annotation.validation.AssertFalse;
import com.github.aureliano.almamater.annotation.validation.AssertTrue;
import com.github.aureliano.almamater.annotation.validation.Decimal;
import com.github.aureliano.almamater.annotation.validation.Max;
import com.github.aureliano.almamater.annotation.validation.Min;
import com.github.aureliano.almamater.annotation.validation.NotEmpty;
import com.github.aureliano.almamater.annotation.validation.NotNull;
import com.github.aureliano.almamater.annotation.validation.Pattern;
import com.github.aureliano.almamater.annotation.validation.Size;
import com.github.aureliano.almamater.core.config.IConfigInput;
import com.github.aureliano.almamater.core.config.IConfiguration;

public class CustomInputConfig implements IConfigInput {

	private String id;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	
	private Integer myNewProperty;
	private Boolean ok;
	private Boolean notOk;
	private Double myDoubleField;
	
	private List<Object> data;
	
	public CustomInputConfig() {
		this.useLastExecutionRecords = false;
		this.metadata = new Properties();
		
		this.data = new ArrayList<>();
	}
	
	@Override
	public CustomInputConfig clone() {
		return new CustomInputConfig()
			.withConfigurationId(this.id)
			.withMyNewProperty(this.myNewProperty);
	}
	
	@Override
	public IConfiguration putMetadata(String key, String value) {
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
	@NotNull
	@NotEmpty
	@Min(value = 3)
	@Max(value = 5)
	@Size(min = 3, max = 5)
	@Pattern(value = "[\\d\\w]{3,5}")
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public CustomInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public CustomInputConfig withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@AssertTrue
	public Boolean isOk() {
		return ok;
	}
	
	public CustomInputConfig withOk(Boolean value) {
		this.ok = value;
		return this;
	}

	@AssertFalse
	public Boolean isNotOk() {
		return notOk;
	}
	
	public CustomInputConfig withNotOk(Boolean value) {
		this.notOk = value;
		return this;
	}
	
	@Decimal(min = 4.5, max = 9.2)
	public Double getMyDoubleField() {
		return myDoubleField;
	}
	
	public CustomInputConfig withMyDoubleField(Double myDoubleField) {
		this.myDoubleField = myDoubleField;
		return this;
	}

	public Integer getMyNewProperty() {
		return this.myNewProperty;
	}

	public CustomInputConfig withMyNewProperty(Integer myNewProperty) {
		this.myNewProperty = myNewProperty;
		return this;
	}

	@Min(value = 1)
	@Max(value = 1)
	@Size(min = 1, max = 1)
	public List<Object> getData() {
		return data;
	}

	public CustomInputConfig withData(List<Object> data) {
		this.data = data;
		return this;
	}

	@Override
	public String id() {
		return "CUSTOM_INPUT_CONFIG";
	}
}