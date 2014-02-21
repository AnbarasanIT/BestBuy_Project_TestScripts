/**
 * Sep 15, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

/**
 * @author Ramiro.Serrato
 *
 */
public class WorkflowCreationException extends WorkflowException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public WorkflowCreationException() {
		super();
	}

	/**
	 * @param message
	 * @param causedBy
	 */
	public WorkflowCreationException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	/**
	 * @param message
	 */
	public WorkflowCreationException(String message) {
		super(message);
	}

	/**
	 * @param causedBy
	 */
	public WorkflowCreationException(Throwable causedBy) {
		super(causedBy);
	}

}
