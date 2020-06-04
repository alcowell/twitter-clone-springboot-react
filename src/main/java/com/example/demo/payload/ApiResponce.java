package com.example.demo.payload;

/**
 * @author yosuk
 *
 */
public class ApiResponce {

	private Boolean success;

	private String message;

	/**
	 * Constructs an <code>ApiResponce</code> object.
	 * @param success
	 * @param message
	 */
	public ApiResponce(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * Gets the success
	 * @return success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * Sets the success
	 * @param success
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * Gets the message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}



}
