/**
 * 
 */
package com.bestbuy.search.merchandising.common.exception;


/**
 * @author chanchal.kumari
 *
 */
public class InvalidPropertyException extends BaseException{
	private static final long serialVersionUID = 1L;	
	
	public InvalidPropertyException() {
		super();
	}

	public InvalidPropertyException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	public InvalidPropertyException(String message) {
		super(message);
	}

	public InvalidPropertyException(Throwable causedBy) {
		super(causedBy);
	}

}
