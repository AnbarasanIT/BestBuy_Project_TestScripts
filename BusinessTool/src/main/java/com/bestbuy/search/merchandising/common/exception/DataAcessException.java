/**
 * 
 */
package com.bestbuy.search.merchandising.common.exception;


/**
 * Exception class for the DataBase
 * @author Lakshmi Valluripalli
 */
public class DataAcessException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAcessException(){
		super();
	}
	/**
	 * Constructor
	 * @param causedBy underlying exception
	 */
	public DataAcessException(Throwable causedBy) {
		super(causedBy);
	}

	/**
	 * Constructor
	 * @param message String error message
	 */
	public DataAcessException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message String error message
	 * @param causedBy underlying exception
	 */
	public DataAcessException(String message,Throwable causedBy) {
		super(message, causedBy);
	}

}
