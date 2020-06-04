package com.example.demo.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yosuk
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * Constructs an <code>BadRequestException.java</code> object.
	 * @param message
	 */
	public BadRequestException(String message) {
		super(message);
	}

	/**
	 * Constructs an <code>BadRequestException.java</code> object.
	 * @param message
	 * @param cause
	 */
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);

	}


}
