package com.example.demo.payload;

import javax.validation.constraints.NotBlank;

/**
 * Store user's information trying to login and validate.
 * @author yosuk
 *
 */
public class LoginRequest {

	@NotBlank
	private String usernameOrUserId;

	@NotBlank
	private String password;

	/**
	 * Gets the usernameOrUserId
	 * @return usernameOrUserId
	 */
	public String getUsernameOrUserId() {
		return usernameOrUserId;
	}

	/**
	 * Sets the usernameOrUserId
	 * @param usernameOrUserId
	 */
	public void setUsernameOrUserId(String usernameOrUserId) {
		this.usernameOrUserId = usernameOrUserId;
	}

	/**
	 * Gets the password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}


}
