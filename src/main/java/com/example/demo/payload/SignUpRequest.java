package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author yosuk
 *
 */
public class SignUpRequest {

	@NotBlank
	@Size(min = 4, max = 20)
	private String userId;

	@NotBlank
	@Size(min = 4, max = 40)
	private String userName;

	@NotBlank
	@Size(min = 6, max = 100)
	private String password;

	/**
	 * Gets the userId
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the userId
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the userName
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the userName
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
