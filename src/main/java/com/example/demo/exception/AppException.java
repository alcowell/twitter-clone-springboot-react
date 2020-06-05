package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yosuk
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

	/**
	 * Constructs an <code>AppExeption</code> object.
	 * @param message
	 */
	public AppException(String message) {
		super(message);
	}

	/**
	 * Constructs an <code>AppExeption</code> object.
	 * @param message
	 * @param cause
	 */
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}




}
