/**
 * 
 */
package com.bestbuy.search.merchandising.common.exception;


/**
 * Exception class for the Service Layer
 * @author Lakshmi Valluripalli
 */
public class ServiceException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(){
		super();
	}
	/**
	 * Constructor
	 * @param causedBy underlying exception
	 */
	public ServiceException(Throwable causedBy) {
		super(causedBy);
	}

	/**
	 * Constructor
	 * @param message String error message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message String error message
	 * @param causedBy underlying exception
	 */
	public ServiceException(String message,Throwable causedBy) {
		super(message, causedBy);
	}

}
