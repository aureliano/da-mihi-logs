package com.github.aureliano.evtbridge.output.file;

import com.github.aureliano.evtbridge.core.event.AfterWritingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeWritingEvent;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;

public class FileOutputDataWritingListener implements DataWritingListener {

	@Override
	public void beforeDataWriting(BeforeWritingEvent event) { }

	@Override
	public void afterDataWriting(AfterWritingEvent event) { }
}