package com.github.aureliano.damihilogs.converter.input;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.BoundingBoxLocation;
import com.github.aureliano.damihilogs.config.GeoLocation;
import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.config.input.TwitterInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class TwitterInputConverter extends AbstractInputConverter<TwitterInputConfig> {

	public TwitterInputConverter() {
		super();
	}

	@Override
	public TwitterInputConfig convert(Map<String, Object> data) {
		TwitterInputConfig conf = new TwitterInputConfig();
		
		super.configureObject(conf, data);
		
		if (data.get("track") != null) {
			List<String> track = (List<String>) data.get("track");
			for (String item : track) {
				conf.addTrack(item);
			}
		}
		
		if (data.get("locations") != null) {
			List<Map<String, Object>> locations = (List<Map<String, Object>>) data.get("locations");
			for (Map<String, Object> boundingBoxLocation : locations) {
				BoundingBoxLocation location = this.convertBoundingBoxLocation(boundingBoxLocation);
				conf.addBoundingBoxLocation(location);
			}
		}
		
		return conf
			.withConsumerKey(StringHelper.parse(data.get("consumerKey")))
			.withConsumerSecret(StringHelper.parse(data.get("consumerSecret")))
			.withOauthToken(StringHelper.parse(data.get("oauthToken")))
			.withOauthTokenSecret(StringHelper.parse(data.get("oauthTokenSecret")));
	}
	
	private BoundingBoxLocation convertBoundingBoxLocation(Map<String, Object> data) {
		BoundingBoxLocation location = new BoundingBoxLocation();
		Map<String, Object> box = DataHelper.getAsHash(data, "boundingBoxLocation");

		GeoLocation fromLocation = this.convertGeoLocation(DataHelper.getAsHash(box, "fromLocation"));
		GeoLocation toLocation = this.convertGeoLocation(DataHelper.getAsHash(box, "toLocation"));
		
		return location
			.withFromLocation(fromLocation)
			.withToLocation(toLocation);
	}

	private GeoLocation convertGeoLocation(Map<String, Object> data) {
		GeoLocation location = new GeoLocation();
		
		String value = StringHelper.parse(data.get("latitude"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new DaMihiLogsException("Property latitude in input twitter configuration was expected to be a double value.");
			}
			location.withLatitude(Double.parseDouble(value));
		}
		
		value = StringHelper.parse(data.get("longitude"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new DaMihiLogsException("Property longitude in input twitter configuration was expected to be a double value.");
			}
			location.withLongitude(Double.parseDouble(value));
		}
		
		return location;
	}

	@Override
	public String id() {
		return InputConfigTypes.TWITTER_INPUT.name();
	}
}