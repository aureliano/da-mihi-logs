package com.github.aureliano.damihilogs.inout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class FileCompression {

	private final int BUFFER_SIZE = 4096;

	private static final Logger logger = Logger.getLogger(FileCompression.class);
	private static FileCompression instance;

	private FileCompression() {
		super();
	}

	public static final FileCompression instance() {
		if (instance == null) {
			instance = new FileCompression();
		}

		return instance;
	}

	public void compress(CompressMetadata metadata) {
		switch (metadata.getCompressionType()) {
		case ZIP:
			this.zip(metadata);
			break;
		case GZIP:
			this.gzip(metadata);
		}

		logger.info(metadata.getInputFilePath() + " compressed to " + metadata.getOutputFilePath() + " using "
				+ metadata.getCompressionType().name());
	}

	public void zip(CompressMetadata metadata, List<File> files) {
		this._zip(metadata, files);
	}

	public void zip(CompressMetadata metadata) {
		this._zip(metadata, Arrays.asList(new File(metadata.getInputFilePath())));
	}

	private void _zip(CompressMetadata metadata, List<File> files) {
		int endOfInputStream = -1;
		final byte[] buffer = new byte[BUFFER_SIZE];
		
		try {
			ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(metadata.getOutputFilePath()));
			
			
			for (File file : files) {
				outputStream.putNextEntry(new ZipEntry(file.getName()));
				FileInputStream inputStream = new FileInputStream(file);
				int byteRead = 0;
				
				while ((byteRead = inputStream.read(buffer)) != endOfInputStream) {
					outputStream.write(buffer, 0, byteRead);
				}
				
				outputStream.closeEntry();
				inputStream.close();
			}
			
			outputStream.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	public void gzip(CompressMetadata metadata) {
		final byte[] buffer = new byte[BUFFER_SIZE];
		int byteRead = 0;
		int endOfInputStream = -1;
			
		try {
			FileInputStream inputStream = new FileInputStream(metadata.getInputFilePath());
			GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(metadata.getOutputFilePath()));

			while ((byteRead = inputStream.read(buffer)) != endOfInputStream) {
				outputStream.write(buffer, 0, byteRead);
			}

			outputStream.close();
			inputStream.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}