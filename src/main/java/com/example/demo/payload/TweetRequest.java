package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author yosuk
 *
 */
public class TweetRequest {

	@NotBlank
	@Size(min = 1, max = 140)
	private String text;

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

	
}
