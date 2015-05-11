package com.github.aureliano.damihilogs.inout;

import java.io.File;
import java.io.FilenameFilter;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.FileHelper;

public class FileDecompressionTest {

	private FileDecompression decompressor;
	private File repository;
	
	private String unzippedPath;
	private String ungzippedPath;
	
	public FileDecompressionTest() {
		this.decompressor = FileDecompression.instance();
		this.repository = new File("target/thrash/compression");
		
		this.unzippedPath = this.repository.getPath() + File.separator + "unzipped";
		this.ungzippedPath = this.repository.getPath() + File.separator + "ungzipped.log";
		
		if (!this.repository.exists()) {
			if (!this.repository.mkdirs()) {
				Assert.fail("Could not create compression files repository.");
			}
		}
	}
	
	@Test
	public void testUnzip() {
		this.unzipSuccess();
		this.unzipError();
	}
	
	@Test
	public void testUngzip() {
		this.decompressor.decompress(
			new CompressMetadata()
				.withCompressionType(SupportedCompressionType.GZIP)
				.withInputFilePath("src/test/resources/compressed.gz")
				.withOutputFilePath(this.ungzippedPath));
		
		Assert.assertTrue(new File(this.ungzippedPath).exists());
	}
	
	private void unzipSuccess() {
		this.unzipOneSuccess();
		this.unzipManySuccess();
	}
	
	private void unzipOneSuccess() {
		this.decompressor.decompress(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.ZIP)
					.withInputFilePath("src/test/resources/one-inside.zip")
					.withOutputFilePath(FileHelper.buildPath(this.unzippedPath, "test")));
			
		Assert.assertTrue(new File(this.unzippedPath).exists());
	}
	
	private void unzipManySuccess() {
		this.decompressor.unzip(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.ZIP)
					.withInputFilePath("src/test/resources/many.zip")
					.withOutputFilePath(FileHelper.buildPath(this.unzippedPath, "many")));
			
		File dir = new File(FileHelper.buildPath(this.unzippedPath, "many"));
		Assert.assertTrue(dir.exists());
		Assert.assertEquals(3, dir.list().length);
		
		int size = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals("report");
			}
		})[0].list().length;
		
		Assert.assertEquals(3, size);
	}
	
	private void unzipError() {
		this.unzipManyIsNotDirectory();
		this.unzipOneIsDirectory();
		this.unzipOneThereAreMoreFiles();
	}
	
	private void unzipManyIsNotDirectory() {
		String output = FileHelper.buildPath("src", "test", "resources", "server.log");
		
		try {
			this.decompressor.unzip(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.ZIP)
					.withInputFilePath("src/test/resources/many.zip")
					.withOutputFilePath(output));
			Assert.fail("Expected to catch an exception.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(output + " is not a directory.", ex.getMessage());
		}
	}
	
	private void unzipOneIsDirectory() {
		try {
			this.decompressor.decompress(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.ZIP)
					.withInputFilePath("src/test/resources/one-inside.zip")
					.withOutputFilePath(this.unzippedPath));
			Assert.fail("Expected to catch an exception.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(this.unzippedPath + " is a directory.", ex.getMessage());
		}
	}
	
	private void unzipOneThereAreMoreFiles() {
		String output = FileHelper.buildPath(this.unzippedPath, "test_error.log");
		String input = "src/test/resources/many.zip";
		
		try {
			this.decompressor.decompress(
				new CompressMetadata()
					.withCompressionType(SupportedCompressionType.ZIP)
					.withInputFilePath(input)
					.withOutputFilePath(output));
			Assert.fail("Expected to catch an exception.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("There is more than one file in " + input, ex.getMessage());
		}
	}
}