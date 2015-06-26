package com.github.aureliano.damihilogs.config;

import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.annotation.validation.Valid;

public class BoundingBoxLocation {

	private GeoLocation fromLocation;
	private GeoLocation toLocation;
	
	public BoundingBoxLocation() {
		super();
	}

	@NotNull
	@Valid
	public GeoLocation getFromLocation() {
		return this.fromLocation;
	}

	public BoundingBoxLocation withFromLocation(GeoLocation fromLocation) {
		this.fromLocation = fromLocation;
		return this;
	}

	@NotNull
	@Valid
	public GeoLocation getToLocation() {
		return this.toLocation;
	}

	public BoundingBoxLocation withToLocation(GeoLocation toLocation) {
		this.toLocation = toLocation;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.fromLocation == null) ? 0 : this.fromLocation.hashCode());
		result = prime * result + ((this.toLocation == null) ? 0 : this.toLocation.hashCode());
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
		BoundingBoxLocation other = (BoundingBoxLocation) obj;
		if (this.fromLocation == null) {
			if (other.fromLocation != null)
				return false;
		} else if (!this.fromLocation.equals(other.fromLocation))
			return false;
		if (this.toLocation == null) {
			if (other.toLocation != null)
				return false;
		} else if (!this.toLocation.equals(other.toLocation))
			return false;
		return true;
	}
}