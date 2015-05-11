package com.github.aureliano.damihilogs.inout;

public class CompressMetadata {

	private SupportedCompressionType compressionType;
	private String inputFilePath;
	private String outputFilePath;
	
	public CompressMetadata() {
		super();
	}

	public SupportedCompressionType getCompressionType() {
		return compressionType;
	}

	public CompressMetadata withCompressionType(SupportedCompressionType compressionType) {
		this.compressionType = compressionType;
		return this;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public CompressMetadata withInputFilePath(String path) {
		this.inputFilePath = path;
		return this;
	}
	
	public String getOutputFilePath() {
		return outputFilePath;
	}
	
	public CompressMetadata withOutputFilePath(String path) {
		this.outputFilePath = path;
		return this;
	}
}