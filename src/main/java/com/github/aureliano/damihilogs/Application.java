package com.github.aureliano.damihilogs;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ConfigHelper;

public class Application {

	private String configurationPath;
	
	public Application() {
		super();
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		
		if (args.length == 0) {
			app.printHelp();
		} else if (args.length > 1) {
			throw new DaMihiLogsException("You must provide only one JSON configuration file.");
		} else {
			app.withConfigurationFile(args[0]).execute();;
		}
	}
	
	public void execute() {
		EventCollectorConfiguration configuration = this.parseConfiguration();
		AppEventsCollector collector = this.prepareAppEventsCollector(configuration);
		this.execute(collector);
	}
	
	public Application withConfigurationFile(String path) {
		this.configurationPath = path;
		return this;
	}
	
	private void execute(AppEventsCollector collector) {
		collector.execute();
	}
	
	private AppEventsCollector prepareAppEventsCollector(EventCollectorConfiguration configuration) {
		return new AppEventsCollector().withConfiguration(configuration);
	}
	
	private EventCollectorConfiguration parseConfiguration() {
		return ConfigHelper.loadConfiguration(this.configurationPath);
	}
	
	private void printHelp() {
		throw new DaMihiLogsException("Help not created yet.");
	}
}