package com.example.demo.payload;

import java.util.List;

/**
 * @author yosuk
 *
 */
public class ListResponse<T> {

	private List<T> contents;

	/**
	 * Constructs an <code>ListResponse.java</code> object.
	 */
	public ListResponse() {
	}

	/**
	 * Constructs an <code>ListResponse.java</code> object.
	 * @param contents
	 */
	public ListResponse(List<T> contents) {
		this.contents = contents;
	}

	/**
	 * Gets the contents
	 * @return contents
	 */
	public List<T> getContents() {
		return contents;
	}

	/**
	 * Sets the contents
	 * @param contents
	 */
	public void setContents(List<T> contents) {
		this.contents = contents;
	}

}
