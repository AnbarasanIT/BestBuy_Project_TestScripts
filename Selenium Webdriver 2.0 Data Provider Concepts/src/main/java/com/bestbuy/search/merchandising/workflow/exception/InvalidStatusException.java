/**
 * Sep 16, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

/**
 * @author Ramiro.Serrato
 *
 */
public class InvalidStatusException extends WorkflowException {
	private static final long serialVersionUID = 1L;
	
	public InvalidStatusException() {
		super();
	}

	/**
	 * @param message
	 * @param causedBy
	 */
	public InvalidStatusException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	/**
	 * @param message
	 */
	public InvalidStatusException(String message) {
		super(message);
	}

	/**
	 * @param causedBy
	 */
	public InvalidStatusException(Throwable causedBy) {
		super(causedBy);
	}
}
