package com.example.demo.auditing;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author yosuk
 *
 */
@MappedSuperclass
@JsonIgnoreProperties(
		value = {"createdBy","updateBy"},
		allowGetters = true
		)
public abstract class UserDateAudit extends DateAudit {

	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;

	@LastModifiedBy
	private Long updateBy;

	/**
	 * Gets the createdBy
	 * @return createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the createdBy
	 * @param createdBy
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the updateBy
	 * @return updateBy
	 */
	public Long getUpdateBy() {
		return updateBy;
	}

	/**
	 * Sets the updateBy
	 * @param updateBy
	 */
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}


}
