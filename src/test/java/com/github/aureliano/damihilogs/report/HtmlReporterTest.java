package com.github.aureliano.damihilogs.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class HtmlReporterTest {
	
	private static final File OUTPUT_DIR = new File("target/exec_report");
	
	@Before
	public void beforeTest() {
		if (!OUTPUT_DIR.exists()) {
			OUTPUT_DIR.mkdirs();
		}
	}

	@Test
	public void testBuildReport() {
		HtmlReporter r = new HtmlReporter()
			.withPageTitle("Resurrexit, sicut dixit")
			.withDescription("Deus, qui per ressurrectionem Filii tui, Domini nostri Iesu Christi, mundum laetificare dignatus es: praesta, quaesumus; ut per eius Genetricem Virginem Mariam, perpetuae capiamus gaudia vitae. Per eundem Christum Dominum nostrum.")
			.withOutputDir(OUTPUT_DIR);
		r.buildReport();
		
		String html = this.loadHtmlIndexReport();
		Assert.assertTrue(html.contains("<title>Resurrexit, sicut dixit</title>"));
		Assert.assertTrue(html.contains("<h2>Resurrexit, sicut dixit</h2>"));
	}
	
	@Test
	public void testValidateOutputDir() {
		HtmlReporter r = new HtmlReporter();
		try {
			r.validateOutputDir();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Report output dir not provided.", ex.getMessage());
		}
		
		File dir = new File("src/test/resources/datalog.log");
		r.withOutputDir(dir);
		try {
			r.validateOutputDir();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(dir.getPath() + " must be a directory (existing or not).", ex.getMessage());
		}
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(ReporterTypes.HTML.name(), new HtmlReporter().id());
	}
	
	private String loadHtmlIndexReport() {
		return this.readFile(OUTPUT_DIR.getPath() + File.separator + "index.html");
	}
	
	private String readFile(String path) {
		File f = new File(path);
		StringBuilder b = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				if (b.length() > 0) {
					b.append("\n");
				}
				b.append(line);
			}
			
			reader.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return b.toString();
	}
}