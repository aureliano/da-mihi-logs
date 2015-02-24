package com.github.aureliano.defero.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.elasticsearch.common.Base64;

import com.github.aureliano.defero.config.input.ConnectionSchema;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.input.UrlInputConfig;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.helper.ConfigHelper;

public class UrlDataReader extends AbstractDataReader {

	private UrlInputConfig urlInputConfiguration;
	private FileDataReader fileDataReader;

	private String url;

	private URLConnection connection;
	
	private static final Logger logger = Logger.getLogger(UrlDataReader.class.getName());
	private static final int BUFFER_SIZE = 4096;
	
	public UrlDataReader() {
		super();
	}

	@Override
	public void endResources() {
		logger.info(" >>> Finalizing url data reader.");
		logger.info("Downloaded file: " + ((InputFileConfig) this.fileDataReader.getInputConfiguration()).getFile().getPath());
		this.fileDataReader.endResources();
	}

	@Override
	public Object nextData() {
		this.initialize();
		
		Object data = this.fileDataReader.nextData();
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
		
		this.urlInputConfiguration = (UrlInputConfig) super.inputConfiguration;
		
		this.url = ConfigHelper.buildUrl(this.urlInputConfiguration);
		logger.info("Fetching data from " + url);
		logger.info("Read (download) timeout: " + this.urlInputConfiguration.getReadTimeout());
		
		if (this.urlInputConfiguration.getUser() != null) {
			logger.info("Using basic authentication for " + this.urlInputConfiguration.getUser());
			logger.info("Check certificate? " + this.urlInputConfiguration.isNoCheckCertificate());
		}
		
		this.prepareDownload();
		File file = this.download();
		
		this.fileDataReader = (FileDataReader) new FileDataReader()
			.withInputConfiguration(new InputFileConfig().withFile(file))
			.withMatcher(super.matcher)
			.withParser(super.parser)
			.withFilter(super.filter)
			.withListeners(super.listeners);
	}

	private File download() {
		String fileName = new StringBuilder()
			.append(this.urlInputConfiguration.getHost().replaceAll("\\.", "_"))
			.append("_")
			.append(System.currentTimeMillis())
			.append(this.fileExtension(this.connection.getContentType()))
			.toString();
		File file = new File("tmp" + File.separator + fileName);
		
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(this.connection.getInputStream());
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			byte data[] = new byte[BUFFER_SIZE];
			int numRead;
			
			while((numRead = bufferedInputStream.read(data,0,BUFFER_SIZE)) != -1) {
				raf.write(data, 0, numRead);
			}
			
			bufferedInputStream.close();
			raf.close();
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
		
		return file;
	}

	private void prepareDownload() {
		File tmpDir = new File("tmp");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		
		if (this.urlInputConfiguration.isNoCheckCertificate()) {
			this.ignoreSslVerification();
		}
		
		int responseCode = -1;
		try {
			this.connection = this.createUrlConnection();
			this.connection.connect();		
			responseCode = ((HttpURLConnection) this.connection).getResponseCode();
		} catch (IOException ex) {
			throw new DeferoException(ex);
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
				String userPassword = "usrpsiconv:45sicX32";
				String encoding = Base64.encodeBytes(userPassword.getBytes());
				conn.setRequestProperty("Authorization", "Basic " + encoding);
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
			throw new DeferoException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new DeferoException(ex);
		}
		
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
	
	private String fileExtension(String contentType) {
		if ((contentType == null) || contentType.equals("")) {
			return "";
		} else if (contentType.contains("text/html")) {
			return ".html";
		}
		logger.warning(contentType);
		return "";
	}
	
	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> log = new HashMap<String, Object>();
		Map<String, Object> fileDataReaderLog = this.fileDataReader.executionLog();
		
		for (String key : fileDataReaderLog.keySet()) {
			log.put(key, fileDataReaderLog.get(key));
		}
		
		log.put("url.data.reader.downloaded.file", ((InputFileConfig) this.fileDataReader.getInputConfiguration()).getFile().getPath());
		
		return log;
	}
}