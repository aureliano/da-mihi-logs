package com.github.aureliano.damihilogs.inout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.FileHelper;

public class FileDecompression {

	private final int BUFFER_SIZE = 4096;
	
	private static final Logger logger = Logger.getLogger(FileDecompression.class);
	private static FileDecompression instance;
	
	private FileDecompression() {
		super();
	}
	
	public static final FileDecompression instance() {
		if (instance == null) {
			instance = new FileDecompression();
		}
		
		return instance;
	}
	
	public void decompress(CompressMetadata metadata) {
		switch (metadata.getCompressionType()) {
			case ZIP : this._unzip(metadata);
			break;
			case GZIP : this.ungzip(metadata);
		}
		
		logger.info(metadata.getInputFilePath() + " decompressed to " + metadata.getOutputFilePath() + " using " + metadata.getCompressionType().name());
	}
	
	public void unzip(CompressMetadata metadata) {
		File outputFile = new File(metadata.getOutputFilePath());
		if (!outputFile.exists()) {
			FileHelper.createDirectory(outputFile, false);
		} else if (!outputFile.isDirectory()) {
			throw new DaMihiLogsException(metadata.getOutputFilePath() + " is not a directory.");
		}
		
		int endOfInputStream = -1;
		final byte[] buffer = new byte[BUFFER_SIZE];
		
		try {
			ZipInputStream inputStream = new ZipInputStream(new FileInputStream(new File(metadata.getInputFilePath())));
			ZipEntry entry = null;
			
			while ((entry = inputStream.getNextEntry()) != null) {
				File file = new File(FileHelper.buildPath(outputFile.getPath(), entry.getName()));
				if (file.isDirectory()) {
					continue;
				} else if (entry.getName().contains(File.separator)) {
					FileHelper.createParentDirectory(file, true);
				}
				
				FileOutputStream outputStream = new FileOutputStream(file);
				int byteRead = 0;
				
				while ((byteRead = inputStream.read(buffer)) != endOfInputStream) {
					outputStream.write(buffer, 0, byteRead);
				}
				
				outputStream.close();
				inputStream.closeEntry();
			}
			
			inputStream.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
		
	public void ungzip(CompressMetadata metadata) {			
		final byte[] buffer = new byte[BUFFER_SIZE];
		int byteRead = 0;
		int endOfInputStream = -1;
			
		try {
			GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(metadata.getInputFilePath()));
			FileOutputStream outputStream = new FileOutputStream(metadata.getOutputFilePath());
			
			while ((byteRead = inputStream.read(buffer)) != endOfInputStream) {
				outputStream.write(buffer, 0, byteRead);
			}
			
			outputStream.close();
			inputStream.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private void _unzip(CompressMetadata metadata) {
		File outputFile = new File(metadata.getOutputFilePath());
		if (outputFile.isDirectory()) {
			throw new DaMihiLogsException(outputFile.getPath() + " is a directory.");
		} else if (!outputFile.exists()) {
			FileHelper.createParentDirectory(outputFile, true);
		}
		
		int endOfInputStream = -1;
		final byte[] buffer = new byte[BUFFER_SIZE];
		int entries = 0;
		
		try {
			ZipInputStream inputStream = new ZipInputStream(new FileInputStream(new File(metadata.getInputFilePath())));
			ZipEntry entry = null;
			
			while ((entry = inputStream.getNextEntry()) != null) {
				if (entries > 0) {
					throw new DaMihiLogsException("There is more than one file in " + metadata.getInputFilePath());
				} else if (entry.isDirectory()) {
					continue;
				}
				
				FileOutputStream outputStream = new FileOutputStream(outputFile);
				int byteRead = 0;
				
				while ((byteRead = inputStream.read(buffer)) != endOfInputStream) {
					outputStream.write(buffer, 0, byteRead);
				}
				
				entries++;
				outputStream.close();
				inputStream.closeEntry();
			}
			
			inputStream.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}