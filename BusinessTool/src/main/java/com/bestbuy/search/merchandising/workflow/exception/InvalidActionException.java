/**
 * Sep 16, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

/**
 * @author Ramiro.Serrato
 *
 */
public class InvalidActionException extends WorkflowException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidActionException() {
		super();
	}

	/**
	 * @param message
	 * @param causedBy
	 */
	public InvalidActionException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	/**
	 * @param message
	 */
	public InvalidActionException(String message) {
		super(message);
	}

	/**
	 * @param causedBy
	 */
	public InvalidActionException(Throwable causedBy) {
		super(causedBy);
	}
}
