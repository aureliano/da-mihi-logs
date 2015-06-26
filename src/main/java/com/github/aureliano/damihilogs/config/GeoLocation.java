package com.github.aureliano.damihilogs.config;

import com.github.aureliano.damihilogs.annotation.validation.Decimal;
import com.github.aureliano.damihilogs.annotation.validation.NotNull;

public class GeoLocation {

	private Double latitude;
	private Double longitude;
	
	public GeoLocation() {
		super();
	}

	@NotNull
	@Decimal(min = -90, max = +90)
	public Double getLatitude() {
		return this.latitude;
	}

	public GeoLocation withLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	@NotNull
	@Decimal(min = -180, max = +180)
	public Double getLongitude() {
		return this.longitude;
	}

	public GeoLocation withLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.latitude == null) ? 0 : this.latitude.hashCode());
		result = prime * result + ((this.longitude == null) ? 0 : this.longitude.hashCode());
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
		GeoLocation other = (GeoLocation) obj;
		if (this.latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!this.latitude.equals(other.latitude))
			return false;
		if (this.longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!this.longitude.equals(other.longitude))
			return false;
		return true;
	}
}