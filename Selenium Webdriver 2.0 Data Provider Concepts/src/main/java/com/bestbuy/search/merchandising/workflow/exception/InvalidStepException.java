/**
 * Sep 17, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

/**
 * @author Ramiro.Serrato
 *
 */
public class InvalidStepException extends WorkflowException {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidStepException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param causedBy
	 */
	public InvalidStepException(String message, Throwable causedBy) {
		super(message, causedBy);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public InvalidStepException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param causedBy
	 */
	public InvalidStepException(Throwable causedBy) {
		super(causedBy);
		// TODO Auto-generated constructor stub
	}
}
