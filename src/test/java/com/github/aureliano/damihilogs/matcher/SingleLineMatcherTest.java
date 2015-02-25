package com.github.aureliano.damihilogs.matcher;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SingleLineMatcherTest {

	@Test
	public void testParcialMatch() {
		IMatcher m = new SingleLineMatcher();
		String text = "2015-02-19 00:00:12,269 INFO  [my-app] (ajp-127.0.0.2-8009-386) [br.com.app.my_app.util.LoggingSessionListener]<memory freeMem=\"1314.79\" allocatedMem=\"3005.12\" maxMem=\"5461.38\" totalFreeMem=\"3771.04\" sgbdSessions=\"0\" webSessions=\"47\" loadAverage=\"0.31\"/>";
		
		assertTrue(m.partialMatch(text));
		
		m = new SingleLineMatcher("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}\\s(INFO|WARN|ERROR)\\s+\\[my-app\\]");
		String invalidText = "A line that shouldn't pass.";
		
		assertFalse(m.partialMatch(invalidText));
		assertTrue(m.partialMatch(text));
	}
	
	@Test
	public void testMatches() {
		IMatcher m = new SingleLineMatcher();
		String text = "2015-02-19 00:00:12,269 INFO  [my-app] (ajp-127.0.0.2-8009-386) [br.com.app.my_app.util.LoggingSessionListener]<memory freeMem=\"1314.79\" allocatedMem=\"3005.12\" maxMem=\"5461.38\" totalFreeMem=\"3771.04\" sgbdSessions=\"0\" webSessions=\"47\" loadAverage=\"0.31\"/>";
		
		assertTrue(m.matches(text));
		
		m = new SingleLineMatcher("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}\\s(INFO|WARN|ERROR)\\s+\\[my-app\\]");
		String invalidText = "A line that shouldn't pass.";
		
		assertFalse(m.matches(invalidText));
		assertTrue(m.matches(text));
	}
}