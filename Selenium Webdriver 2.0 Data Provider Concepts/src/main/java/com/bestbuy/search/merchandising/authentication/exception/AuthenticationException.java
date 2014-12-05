package com.bestbuy.search.merchandising.authentication.exception;

import com.bestbuy.search.merchandising.common.exception.BaseException;

/**
 * Class that encapsulates the general exception for authentication
 * 
 * @author Chanchal.Kumari
 * 
 */
public class AuthenticationException extends BaseException {
	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable causedBy) {
		super(causedBy);
	}
}