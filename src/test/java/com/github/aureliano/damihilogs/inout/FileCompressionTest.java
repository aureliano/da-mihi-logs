package com.github.aureliano.damihilogs.inout;

import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.helper.FileHelper;

public class FileCompressionTest {
	
	private FileCompression compressor;
	private File repository;
	
	private String zippedPath;
	private String gzippedPath;
	
	public FileCompressionTest() {
		this.compressor = FileCompression.instance();
		this.repository = new File("target/thrash/compression");
		
		this.zippedPath = this.repository.getPath() + File.separator + "zipped.zip";
		this.gzippedPath = this.repository.getPath() + File.separator + "gzipped.gz";
		
		if (!this.repository.exists()) {
			if (!this.repository.mkdirs()) {
				Assert.fail("Could not create compression files repository.");
			}
		}
	}
	
	@Test
	public void testZip() {
		this.zipOne();
		this.zipMany();
	}
	
	@Test
	public void testGzip() {
		this.compressor.compress(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.GZIP)
					.withInputFilePath("src/test/resources/simple_file")
					.withOutputFilePath(this.gzippedPath));
			
		Assert.assertTrue(new File(this.gzippedPath).exists());
	}
	
	private void zipOne() {
		this.compressor.compress(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.ZIP)
					.withInputFilePath("src/test/resources/simple_file")
					.withOutputFilePath(this.zippedPath));
			
		Assert.assertTrue(new File(this.zippedPath).exists());
	}
	
	private void zipMany() {
		String dir = FileHelper.buildPath("src", "test", "resources");
		
		this.compressor.zip(
			new CompressMetadata()
				.withCompressionType(SupportedCompressionType.ZIP)
				.withOutputFilePath(this.zippedPath),
			Arrays.asList(
				new File(FileHelper.buildPath(dir, "datalog.log")),
				new File(FileHelper.buildPath(dir, "simple_file"))
			)
		);
			
		Assert.assertTrue(new File(this.zippedPath).exists());
	}
}