package com.github.aureliano.evtbridge.core.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.evtbridge.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.exception.EventBridgeException;

public final class ConfigHelper {
	
	private static final List<String> AVAILABLE_EXECUTOR_NAMES = new ArrayList<String>();
	private static final List<String> UNAVAILABLE_EXECUTOR_NAMES = new ArrayList<String>();
	
	static {
		populateExecutorNamesMap();
	}

	private ConfigHelper() {
		super();
	}
	
	public static void validateConfiguration(IConfiguration configuration) {
		Set<ConstraintViolation> violations = ObjectValidator.instance().validate(configuration);
		if (violations.isEmpty()) {
			return;
		}
		
		StringBuilder builder = new StringBuilder("Validation failed for configuration " + configuration.id() + ":");
		for (ConstraintViolation violation : violations) {
			builder.append("\n - ").append(violation.getMessage());
		}
		
		throw new EventBridgeException(builder.toString());
	}

	public static void copyMetadata(IConfiguration from, IConfiguration to) {
		Set<Object> keys = from.getMetadata().keySet();
		for (Object key : keys) {
			to.putMetadata(key.toString(), from.getMetadata(key.toString()));
		}
	}
	
	public synchronized static String newUniqueConfigurationName() {
		for (String executorName : AVAILABLE_EXECUTOR_NAMES) {
			if (!UNAVAILABLE_EXECUTOR_NAMES.contains(executorName)) {
				AVAILABLE_EXECUTOR_NAMES.remove(executorName);
				UNAVAILABLE_EXECUTOR_NAMES.add(executorName);
				
				return executorName;
			}
		}
		
		throw new EventBridgeException("Could not create a new unique configuration name. Exceeded!");
	}
	
	private static void populateExecutorNamesMap() {
		String[] names = FileHelper.readResource("configuration-names").split("\n");
		
		for (String name : names) {
			AVAILABLE_EXECUTOR_NAMES.add(name);
		}
		
		Collections.shuffle(AVAILABLE_EXECUTOR_NAMES);
	}
	
	protected static void resetExecutorNamesMap() {
		AVAILABLE_EXECUTOR_NAMES.clear();
		UNAVAILABLE_EXECUTOR_NAMES.clear();
		populateExecutorNamesMap();
	}
}