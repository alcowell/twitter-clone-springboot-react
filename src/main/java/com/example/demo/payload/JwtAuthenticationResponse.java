package com.example.demo.payload;

/**
 * @author yosuk
 *
 */
public class JwtAuthenticationResponse {

	private String accessToken;

	private String tokenType = "Bearer";

	/**
	 * Constructs an <code>JwtAuthenticationResponse</code> object.
	 * @param accessToken
	 */
	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the accessToken
	 * @return accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the accessToken
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the tokenType
	 * @return tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * Sets the tokenType
	 * @param tokenType
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}




}
