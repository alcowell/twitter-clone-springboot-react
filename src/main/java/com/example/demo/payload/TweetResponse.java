package com.example.demo.payload;

import java.time.Instant;

import javax.persistence.PrePersist;

/**
 * @author yosuk
 *
 */
public class TweetResponse {

	private Long id;

	private String text;

	private UserSummary createdBy;

	private Instant creationDateTime;

	private Boolean isDeleted;

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
	 * Gets the text
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the createdBy
	 * @return createdBy
	 */
	public UserSummary getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the createdBy
	 * @param createdBy
	 */
	public void setCreatedBy(UserSummary createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the creationDateTime
	 * @return creationDateTime
	 */
	public Instant getCreationDateTime() {
		return creationDateTime;
	}

	/**
	 * Sets the creationDateTime
	 * @param creationDateTime
	 */
	public void setCreationDateTime(Instant creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	/**
	 * Gets the isDeleted
	 * @return isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the isDeleted
	 * @param isDeleted
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@PrePersist
	public void prePersist() {
		if(getIsDeleted() == null) {
			setIsDeleted(false);
		}
	}
}
