package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.auditing.UserDateAudit;

/**
 * @author yosuk
 *
 */
@Entity
@Table(name = "tweets")
public class Tweet extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 1, max = 140)
	private String text;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Like> likes;

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
	 * Gets the likes
	 * @return likes
	 */
	public List<Like> getLikes() {
		return likes;
	}

	/**
	 * Sets the likes
	 * @param likes
	 */
	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}




}
