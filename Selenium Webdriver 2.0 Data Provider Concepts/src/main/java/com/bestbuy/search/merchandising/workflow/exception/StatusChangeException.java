/**
 * Sep 14, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

/**
 * @author Ramiro.Serrato
 *
 */
public class StatusChangeException extends WorkflowException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public StatusChangeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param causedBy
	 */
	public StatusChangeException(String message, Throwable causedBy) {
		super(message, causedBy);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public StatusChangeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param causedBy
	 */
	public StatusChangeException(Throwable causedBy) {
		super(causedBy);
		// TODO Auto-generated constructor stub
	}

}
