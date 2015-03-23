package com.github.aureliano.damihilogs.matcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MultiLineMatcherTest {

	private List<String> logLines;
	
	@Before
	public void beforeTest() {
		if (this.logLines == null) {
			this.logLines = this.loadLog();
		}
	}
	
	@Test
	public void testParcialMatch() {
		IMatcher m = new MultiLineMatcher("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}\\s");
		String text = "2015-02-19 00:00:12,269 INFO  [my-app] (ajp-127.0.0.2-8009-386) [br.com.app.my_app.util.LoggingSessionListener]<memory freeMem=\"1314.79\" allocatedMem=\"3005.12\" maxMem=\"5461.38\" totalFreeMem=\"3771.04\" sgbdSessions=\"0\" webSessions=\"47\" loadAverage=\"0.31\"/>";
		
		assertTrue(m.partialMatch(text));		
		assertFalse(m.partialMatch("A line that shouldn't pass."));
		
		StringBuilder builder = new StringBuilder();
		for (String line : this.logLines) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			
			builder.append(line);
			assertTrue(m.partialMatch(builder.toString()));
		}
	}
	
	@Test
	public void testMatches() {
		IMatcher m = new MultiLineMatcher("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}\\s");
		String text = "2015-02-19 00:00:12,269 INFO  [my-app] (ajp-127.0.0.2-8009-386) [br.com.app.my_app.util.LoggingSessionListener]<memory freeMem=\"1314.79\" allocatedMem=\"3005.12\" maxMem=\"5461.38\" totalFreeMem=\"3771.04\" sgbdSessions=\"0\" webSessions=\"47\" loadAverage=\"0.31\"/>";
		
		assertTrue(m.matches(text));
		assertFalse(m.partialMatch("A line that shouldn't pass."));
		
		assertFalse(m.matches(stringfy(this.logLines)));		
		this.logLines.remove(this.logLines.size() - 1);
		assertTrue(m.matches(stringfy(this.logLines)));
		
	}
	
	@Test
	public void testMatchesMaxAttemptsExceed() {
		int maxParseAttempts = 5;
		IMatcher m = new MultiLineMatcher("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}\\s", null, maxParseAttempts);
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= maxParseAttempts; i++) {
			builder.append(this.logLines.get(i)).append("\n");
			
			try {
				assertTrue(m.partialMatch(builder.toString()));
			} catch (DaMihiLogsException ex) {
				assertEquals(ex.getMessage(), "Max parse attempts overflow (" + 5 + " >= " + 5 + ").");
				return;
			}
		}
		
		Assert.fail("Expected to throw a " + DaMihiLogsException.class.getName());
	}
	
	private String stringfy(List<String> lines) {
		StringBuilder builder = new StringBuilder();
		for (String line : this.logLines) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			
			builder.append(line);
		}
		
		return builder.toString();
	}
	
	private List<String> loadLog() {
		List<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/server.log"));
			String line = reader.readLine();
			
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return lines;
	}
}