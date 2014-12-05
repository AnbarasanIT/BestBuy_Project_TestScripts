/**
 * 
 */
package com.bestbuy.search.merchandising.common.exception;

/**
 * @author a1007483
 *
 */
public class BaseException  extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException(){
		super();
	}
	/**
	 * Constructor
	 * @param causedBy underlying exception
	 */
	public BaseException(final Throwable causedBy) {
		super(causedBy);
	}

	/**
	 * Constructor
	 * @param message String error message
	 */
	public BaseException(final String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message String error message
	 * @param causedBy underlying exception
	 */
	public BaseException(final String message, final Throwable causedBy) {
		super(message, causedBy);
	}

}