package com.example.demo.payload;

import java.util.List;

/**
 * @author yosuk
 *
 */
public class PagedResponse<T> {

	private List<T> content;

	private int page;

	private int size;

	private long totalElements;

	private int totalPages;

	private boolean last;

	/**
	 * Constructs an <code>PagedResponse.java</code> object.
	 */
	public PagedResponse() {
	}

	/**
	 * Constructs an <code>PagedResponse.java</code> object.
	 * @param content
	 * @param page
	 * @param size
	 * @param totalElements
	 * @param totalPages
	 * @param last
	 */
	public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
		this.content = content;
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
	}

	/**
	 * Gets the content
	 * @return content
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * Sets the content
	 * @param content
	 */
	public void setContent(List<T> content) {
		this.content = content;
	}

	/**
	 * Gets the page
	 * @return page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets the page
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Gets the size
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Gets the totalElements
	 * @return totalElements
	 */
	public long getTotalElements() {
		return totalElements;
	}

	/**
	 * Sets the totalElements
	 * @param totalElements
	 */
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	/**
	 * Gets the totalPages
	 * @return totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * Sets the totalPages
	 * @param totalPages
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Gets the last
	 * @return last
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * Sets the last
	 * @param last
	 */
	public void setLast(boolean last) {
		this.last = last;
	}


}
