package com.github.aureliano.evtbridge.output.elasticsearch;

import com.github.aureliano.evtbridge.core.event.AfterWritingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeWritingEvent;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;

public class ESOutputDataWritingListener implements DataWritingListener {

	@Override
	public void beforeDataWriting(BeforeWritingEvent event) { }

	@Override
	public void afterDataWriting(AfterWritingEvent event) { }
}