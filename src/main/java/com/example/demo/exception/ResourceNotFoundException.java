package com.example.demo.exception;

/**
 * @author yosuk
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private String resourceName;

	private String fieldName;

	private Object fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	/**
	 * Gets the resourceName
	 * @return resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Sets the resourceName
	 * @param resourceName
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Gets the fieldName
	 * @return fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the fieldName
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the fieldValue
	 * @return fieldValue
	 */
	public Object getFieldValue() {
		return fieldValue;
	}

	/**
	 * Sets the fieldValue
	 * @param fieldValue
	 */
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}



}
