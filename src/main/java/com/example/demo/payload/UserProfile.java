package com.example.demo.payload;

import java.time.Instant;

/**
 * @author yosuk
 *
 */
public class UserProfile {

	private Long id;

	private String username;

	private String userId;

	private Instant joinedAt;

	/**
	 * Constructs an <code>UserProfile.java</code> object.
	 * @param id
	 * @param username
	 * @param userId
	 * @param joinedAt
	 */
	public UserProfile(Long id, String username, String userId, Instant joinedAt) {
		this.id = id;
		this.username = username;
		this.userId = userId;
		this.joinedAt = joinedAt;
	}

	/**
	 * Gets the id
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

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
	 * Gets the joinedAt
	 * @return joinedAt
	 */
	public Instant getJoinedAt() {
		return joinedAt;
	}

	/**
	 * Sets the joinedAt
	 * @param joinedAt
	 */
	public void setJoinedAt(Instant joinedAt) {
		this.joinedAt = joinedAt;
	}


}
