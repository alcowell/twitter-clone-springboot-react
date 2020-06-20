package com.example.demo.exception;

/**
 * @author yosuk
 *
 */
public class FileStorageException extends RuntimeException {

	/**
	 * Constructs an <code>FileStorageException.java</code> object.
	 * @param message
	 * @param cause
	 */
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an <code>FileStorageException.java</code> object.
	 * @param message
	 */
	public FileStorageException(String message) {
		super(message);
	}
}
