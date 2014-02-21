/**
 * Sep 15, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

/**
 * This class encapsulates the logic for Status validation exceptions
 * @author Ramiro.Serrato
 *
 */
public class StatusValidationException extends WorkflowException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public StatusValidationException() {
		super();
	}

	/**
	 * @param message
	 * @param causedBy
	 */
	public StatusValidationException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	/**
	 * @param message
	 */
	public StatusValidationException(String message) {
		super(message);
	}

	/**
	 * @param causedBy
	 */
	public StatusValidationException(Throwable causedBy) {
		super(causedBy);
	}
}
