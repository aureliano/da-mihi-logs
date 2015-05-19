package com.github.aureliano.damihilogs;

import java.util.Map;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.helper.ConfigurationSchemaHelper;
import com.github.aureliano.damihilogs.helper.FileHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class Application {

	private String configurationPath;
	
	public Application() {
		super();
	}
	
	public static void main(String[] args) {
		new Application().startUp(args);
	}
	
	public void startUp(String[] args) {
		if (args.length == 0) {
			this.printHelp();
		} else if (args.length > 1) {
			if (args[0].equals("help")) {
				this.printSchema(args[1]);
			} else {
				throw new DaMihiLogsException("You must provide only one JSON configuration file.");
			}
		} else {
			this.withConfigurationFile(args[0]).execute();;
		}
	}
	
	public Application withConfigurationFile(String path) {
		this.configurationPath = path;
		return this;
	}
	
	protected void execute() {
		EventCollectorConfiguration configuration = this.parseConfiguration();
		AppEventsCollector collector = this.prepareAppEventsCollector(configuration);
		this.execute(collector);
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
	
	protected void printHelp() {
		String help = FileHelper.readResource("app-help");
		Map<String, Object> rootSchema = ConfigurationSchemaHelper.loadRootSchema();
		String schemaNames = StringHelper.join(ConfigurationSchemaHelper.fetchSchemaNames(rootSchema).toArray(), "\n- ");
		schemaNames = schemaNames.replaceAll("- id", "- rootSchema");
		help = help.replaceAll("\\$\\{schemaNames\\}", "- " + schemaNames);
		
		System.out.println(help);
	}
	
	protected void printSchema(String path) {
		System.out.println(ConfigurationSchemaHelper.fetchSchema(path));
	}
}