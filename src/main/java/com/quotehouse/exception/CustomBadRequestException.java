package com.quotehouse.exception;

public class CustomBadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 892482317287282890L;

	public CustomBadRequestException(String message) {
		super(message);
	}

}
