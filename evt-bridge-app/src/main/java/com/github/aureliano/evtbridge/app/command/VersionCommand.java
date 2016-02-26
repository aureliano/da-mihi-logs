package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.core.EventBridgeMetadata;

public class VersionCommand implements ICommand {

	public VersionCommand() {}
	
	public void execute() {
		EventBridgeMetadata metadata = EventBridgeMetadata.instance();
		
		String versionMessage = String.format(
			"%s (%s)",
			metadata.getProperty("app.version"),
			metadata.getProperty("app.release.date")
		);
		
		System.out.println(versionMessage);
	}
}