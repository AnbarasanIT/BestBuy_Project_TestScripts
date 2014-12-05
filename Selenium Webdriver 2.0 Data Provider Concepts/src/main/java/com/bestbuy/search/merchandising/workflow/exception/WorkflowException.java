/**
 * Sep 14, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.exception;

import com.bestbuy.search.merchandising.common.exception.BaseException;

/**
 * Class that encapsulates the general exception for the workflow
 * @author Ramiro.Serrato
 *
 */
public class WorkflowException extends BaseException {
	private static final long serialVersionUID = 1L;	
	
	public WorkflowException() {
		super();
	}

	public WorkflowException(String message, Throwable causedBy) {
		super(message, causedBy);
	}

	public WorkflowException(String message) {
		super(message);
	}

	public WorkflowException(Throwable causedBy) {
		super(causedBy);
	}
}
