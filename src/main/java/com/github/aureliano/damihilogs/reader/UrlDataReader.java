package com.github.aureliano.damihilogs.reader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.helper.UrlHelper;
import com.google.common.io.BaseEncoding;

public class UrlDataReader extends AbstractDataReader {

	private UrlInputConfig urlInputConfiguration;
	private FileDataReader fileDataReader;

	private String url;

	private URLConnection connection;
	private int bytesRead;
	
	private static final Logger logger = Logger.getLogger(UrlDataReader.class);
	private static final int BUFFER_SIZE = 4096;
	
	public UrlDataReader() {
		super();
	}

	@Override
	public void endResources() {
		logger.debug(" >>> Finalizing url data reader.");
		logger.info("Downloaded file: " + ((InputFileConfig) this.fileDataReader.getInputConfiguration()).getFile().getPath());
		this.fileDataReader.endResources();
	}

	@Override
	public String nextData() {
		this.initialize();
		
		String data = this.fileDataReader.nextData();
		super.markedToStop = this.fileDataReader.markedToStop;
		
		return data;
	}

	@Override
	protected String readNextLine() {
		return this.fileDataReader.readNextLine();
	}
	
	private void initialize() {
		if (this.connection != null) {
			return;
		}
		
		if (this.urlInputConfiguration == null) {
			this.urlInputConfiguration = (UrlInputConfig) super.inputConfiguration;
		}
		
		this.url = UrlHelper.buildUrl(this.urlInputConfiguration);
		logger.info("Fetching data from " + url);
		logger.debug("Read (download) timeout: " + this.urlInputConfiguration.getReadTimeout());
		
		if (this.urlInputConfiguration.getUser() != null) {
			logger.debug("Using basic authentication for " + this.urlInputConfiguration.getUser());
			logger.debug("Check certificate? " + !this.urlInputConfiguration.isNoCheckCertificate());
		}
		
		this.bytesRead = this.urlInputConfiguration.getByteOffSet();
		this.prepareDownload();
		this.download();
		
		if (!this.urlInputConfiguration.isAlwaysDownloadCompleteFile() && !this.urlInputConfiguration.isAppendIfOutputFileExist()) {
			if (this.urlInputConfiguration.getFileStartPosition() != 0) {
				logger.warn("ATENTION! Defining file start position to 0 because you have decided to download only piece of file and you haven't configured to append downloaded file.");
				this.urlInputConfiguration.withFileStartPosition(0);
			}
		}
		
		this.fileDataReader = (FileDataReader) new FileDataReader()
			.withInputConfiguration(
				new InputFileConfig()
					.withFile(this.urlInputConfiguration.getOutputFile())
					.withStartPosition(this.urlInputConfiguration.getFileStartPosition())
					.withConfigurationId(this.urlInputConfiguration.getConfigurationId()))
			.withMatcher(super.matcher)
			.withListeners(super.listeners);
	}

	private void download() {
		if (this.markedToStop) {
			return;
		}
		
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(this.connection.getInputStream());
			RandomAccessFile raf = this.prepareRandomAccessFile();
			
			byte data[] = new byte[BUFFER_SIZE];
			int numRead;
			
			while((numRead = bufferedInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
				raf.write(data, 0, numRead);
				this.bytesRead += numRead;
			}
			
			bufferedInputStream.close();
			raf.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private RandomAccessFile prepareRandomAccessFile() throws IOException {
		RandomAccessFile raf = null;
		
		if (this.urlInputConfiguration.getOutputFile().exists()) {
			if (this.urlInputConfiguration.isAppendIfOutputFileExist()) {
				logger.debug("Appending data to " + this.urlInputConfiguration.getOutputFile().getPath());
				raf = new RandomAccessFile(this.urlInputConfiguration.getOutputFile(), "rw");
				raf.seek(this.urlInputConfiguration.getOutputFile().length());
			} else {
				logger.debug("Erasing data from file: " + this.urlInputConfiguration.getOutputFile().getPath());
				this.urlInputConfiguration.getOutputFile().delete();
				this.urlInputConfiguration.withOutputFile(this.urlInputConfiguration.getOutputFile().getPath());
			}
		}
		
		if (raf == null) {
			raf = new RandomAccessFile(this.urlInputConfiguration.getOutputFile(), "rw");
		}
		
		return raf;
	}

	private void prepareDownload() {		
		if (this.urlInputConfiguration.isNoCheckCertificate()) {
			this.ignoreSslVerification();
		}
		
		int responseCode = -1;
		try {
			this.connection = this.createUrlConnection();
			
			if (this.urlInputConfiguration.getByteOffSet() > 0) {
				int contentLength = this.connection.getContentLength();
				if (contentLength == this.urlInputConfiguration.getByteOffSet()) {
					this.markedToStop = true;
					return;
				}
				
				String byteRange = this.urlInputConfiguration.getByteOffSet() + "-";
				((HttpURLConnection) this.connection).disconnect();
				
				this.connection = this.createUrlConnection();
				logger.info("Download size: " + (contentLength / 1024) + " Kib");
				logger.info("Download byte range: " + byteRange);
				this.connection.addRequestProperty("Range", "bytes=" + byteRange);
			}

			this.connection.connect();
			responseCode = ((HttpURLConnection) this.connection).getResponseCode();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		if (responseCode / 100 != 2) {
			throw new RuntimeException("Response code not ok. Response code equal to " + responseCode);
		}
	}

	private URLConnection createUrlConnection() throws IOException {
		if (ConnectionSchema.HTTP.equals(this.urlInputConfiguration.getConnectionSchema())) {
			return new URL(this.url).openConnection();
		} else if (ConnectionSchema.HTTPS.equals(this.urlInputConfiguration.getConnectionSchema())) {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(this.url).openConnection();
			
			if (this.urlInputConfiguration.getUser() != null) {
				String userPassword = this.urlInputConfiguration.getUser() + ":" + this.urlInputConfiguration.getPassword();
				String encoding = BaseEncoding.base64().encode(userPassword.getBytes());
				conn.addRequestProperty("Authorization", "Basic " + encoding);
			}
			
			return conn;
		}
		
		return null;
	}
	
	private void ignoreSslVerification() {
		TrustManager[] trustAllCerts = new TrustManager[] {
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				public void checkClientTrusted(X509Certificate[] certs, String authType) { }
		
				public void checkServerTrusted(X509Certificate[] certs, String authType) { }
			}
		};
		
		try {
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (KeyManagementException ex) {
			throw new DaMihiLogsException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
	
	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> fileDataReaderLog = this.fileDataReader.executionLog();
		
		for (String key : fileDataReaderLog.keySet()) {
			super.readingProperties.put(key, fileDataReaderLog.get(key));
		}
		
		super.readingProperties.put("input.config." + this.urlInputConfiguration.getConfigurationId() + ".download.output.file",
				((InputFileConfig) this.fileDataReader.getInputConfiguration()).getFile().getPath());
		super.readingProperties.put("input.config." + this.urlInputConfiguration.getConfigurationId() + ".byte.offset", this.bytesRead);
		
		return super.readingProperties;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		this.urlInputConfiguration = (UrlInputConfig) super.inputConfiguration;
		
		String key = "input.config." + this.urlInputConfiguration.getConfigurationId() + ".last.line";
		String value = properties.getProperty(key);
		if (value != null) {
			super.readingProperties.put(key, value);
			if ((this.urlInputConfiguration.isUseLastExecutionRecords()) && 
					(this.urlInputConfiguration.getFileStartPosition() == null)) {
				this.urlInputConfiguration.withFileStartPosition(Integer.parseInt(value));
			}
		}
		
		key = "input.config." + this.urlInputConfiguration.getConfigurationId() + ".download.output.file";
		value = properties.getProperty(key);
		if (value != null) {
			super.readingProperties.put(key, value);
			if ((this.urlInputConfiguration.isUseLastExecutionRecords()) &&
					(this.urlInputConfiguration.getOutputFile() == null)) {
				this.urlInputConfiguration.withOutputFile(value);
			}
		}
		
		key = "input.config." + this.urlInputConfiguration.getConfigurationId() + ".byte.offset";
		value = properties.getProperty(key);
		if (value != null) {
			super.readingProperties.put(key, value);
			if (this.urlInputConfiguration.isUseLastExecutionRecords()) {
				this.urlInputConfiguration.withByteOffSet(Integer.parseInt(value));
			}
		}
		
		ConfigHelper.inputConfigValidation(this.urlInputConfiguration);
	}
}