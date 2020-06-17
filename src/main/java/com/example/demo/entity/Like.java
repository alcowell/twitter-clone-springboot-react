package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.demo.auditing.DateAudit;

/**
 * @author yosuk
 *
 */
@Entity
@Table(name="likes",uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"tweet_id",
				"user_id"
		})
})
public class Like extends DateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tweet_id", nullable = false)
	private Tweet tweet;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

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
	 * Gets the tweet
	 * @return tweet
	 */
	public Tweet getTweet() {
		return tweet;
	}

	/**
	 * Sets the tweet
	 * @param tweet
	 */
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	/**
	 * Gets the user
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}



}
