package com.github.aureliano.evtbridge.core.profile;

import java.util.Properties;

public interface IProfile {

	public abstract void start();
	
	public abstract IProfile stop();
	
	public abstract IProfile intersection(IProfile profile);
	
	public abstract Properties parse();
}