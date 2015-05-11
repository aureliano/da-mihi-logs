package com.github.aureliano.damihilogs.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class FileHelper {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private FileHelper() {
		super();
	}
	
	public static void copyFile(File sourceFile, File destFile) {
		FileChannel source = null;
		FileChannel destination = null;
		
		try {
			if(!destFile.exists()) {
				destFile.createNewFile();
			}
		
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
			
			source.close();
			destination.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public static void copyFile(File sourceFile, File destFile, boolean mkdirs) {
		if (mkdirs) {
			File parentDestFile = destFile.getParentFile();
			if ((parentDestFile != null) && (!parentDestFile.exists())) {
				if (!parentDestFile.mkdirs()) {
					throw new DaMihiLogsException("Could not create directory " + parentDestFile.getPath());
				}
			}
		}
		
		FileHelper.copyFile(sourceFile, destFile);
	}
	
	public static void delete(File file) {
		if (!file.delete()) {
			throw new DaMihiLogsException("Could not delete file " + file.getPath());
		}
	}
	
	public static void delete(File file, boolean recursively) {
		if (recursively) {
			forceDelete(file);
		} else {
			delete(file);
		}
	}
	
	public static void deleteAllFiles(File directory) {
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			if (!file.isDirectory()) {
				delete(file);
			}
		}
	}
	
	public static void deleteAllFiles(File directory, long timeSeed) {
		long currentTimeMillis = System.currentTimeMillis();
		long seed = currentTimeMillis - timeSeed;
		
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			long fileLastModificationSeed = currentTimeMillis - file.lastModified();
			if ((!file.isDirectory()) && (fileLastModificationSeed >= seed)) {
				delete(file);
			}
		}
	}
	
	private static void forceDelete(File file) {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			
			if ((children != null) && (children.length > 0)) {
				for (File child : children) {
					forceDelete(child);
				}
			}
		}
		
		if (!file.delete()) {
			throw new DaMihiLogsException("Could not delete file " + file.getPath());
		}
	}
	
	public static String getLastExecutionLogDataFileName(final String collectorId) {
		return collectorId + "_" + DATE_FORMAT.format(new Date()) + ".log";
	}
	
	public static String readFile(String path) {
		return readFile(new File(path));
	}
	
	public static String readFile(File file) {
		try {
			return readFile(new FileInputStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public static String readFile(InputStream stream) {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				if (builder.length() > 0) {
					builder.append("\n");
				}
				builder.append(line);
			}
			
			reader.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return builder.toString();
	}
	
	public static String readResource(String resourceName) {
		InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
		return FileHelper.readFile(stream);
	}
	
	public static String buildPath(String... tokens) {
		StringBuilder builder = new StringBuilder();
		
		for (String token : tokens) {
			if (builder.length() > 0) {
				builder.append(File.separator);
			}
			builder.append(token);
		}
		
		return builder.toString();
	}
	
	public static void createParentDirectory(File file, boolean chain) {
		createDirectory(file.getParentFile(), chain);
	}
	
	public static void createDirectory(File file, boolean chain) {
		if (file.exists()) {
			return;
		}
		
		DaMihiLogsException ex = new DaMihiLogsException("Could not create directory " + file.getPath());
		if (chain) {
			if (!file.mkdirs()) {
				throw ex;
			}
		} else {
			if (!file.mkdir()) {
				throw ex;
			}
		}
	}
}