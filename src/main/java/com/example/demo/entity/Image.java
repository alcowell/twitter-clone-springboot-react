package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author yosuk
 *
 */
@Entity
@Table(name = "image")
public class Image {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String fileName;

	private String fileType;

	@Lob
	private byte[] data;

	/**
	 * Constructs an <code>Image.java</code> object.
	 */
	public Image() {
	}

	/**
	 * Constructs an <code>Image.java</code> object.
	 * @param fileName
	 * @param fileType
	 * @param data
	 */
	public Image(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	/**
	 * Gets the id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Gets the data
	 * @return data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data
	 * @param data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
}
