package com.example.demo.payload;

/**
 * @author yosuk
 *
 */
public class UserIdentityAvailability {

	private Boolean available;

	/**
	 * Constructs an <code>UserIdentityAvailability.java</code> object.
	 * @param available
	 */
	public UserIdentityAvailability(Boolean available) {
		this.available = available;
	}

	/**
	 * Gets the available
	 * @return available
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * Sets the available
	 * @param available
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}


}
