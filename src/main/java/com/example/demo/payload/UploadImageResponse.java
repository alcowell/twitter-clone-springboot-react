package com.example.demo.payload;

/**
 * @author yosuk
 *
 */
public class UploadImageResponse {

	private String fileName;

	private String fileDownloadUri;

	private String fileType;

	private Long size;

	/**
	 * Constructs an <code>UploadImageResponse.java</code> object.
	 * @param fileName
	 * @param fileDownloadUri
	 * @param fileType
	 * @param size
	 */
	public UploadImageResponse(String fileName, String fileDownloadUri, String fileType, Long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

	/**
	 * Gets the fileName
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the fileName
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the fileDownloadUri
	 * @return fileDownloadUri
	 */
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	/**
	 * Sets the fileDownloadUri
	 * @param fileDownloadUri
	 */
	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	/**
	 * Gets the fileType
	 * @return fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * Sets the fileType
	 * @param fileType
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * Gets the size
	 * @return size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * Sets the size
	 * @param size
	 */
	public void setSize(Long size) {
		this.size = size;
	}


}
