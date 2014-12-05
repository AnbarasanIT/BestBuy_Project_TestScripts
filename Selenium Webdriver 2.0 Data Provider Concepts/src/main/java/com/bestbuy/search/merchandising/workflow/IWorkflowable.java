/**
 * Sep 13, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow;

import java.util.Set;

import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;

/**
 * Encapsulates the actions a workflowable object should have
 * @author Ramiro.Serrato
 */
public interface IWorkflowable<Status extends Enum<Status>, Action extends Enum<Action>> {

	/**
	 * Just applies the rule for the provided status and action, returning the successor status name
	 * @param status The current status name as enum
	 * @param action The action to be performed as enum
	 * @return The successor status name
	 * @throws WorkflowException If an error occurs
	 * @author Ramiro.Serrato
	 */
	public String stepForward(Enum<GeneralStatus> status, Enum<GeneralAction> action) throws WorkflowException;	
	
	/**
	 * Just applies the rule for the provided status and action, returning the successor status name
	 * @param status The current status name as String
	 * @param action The action to be performed as enum
	 * @return The successor status name
	 * @throws WorkflowException If an error occurs
	 * @author Ramiro.Serrato
	 */
	public String stepForward(String status, Enum<Action> action) throws WorkflowException;	
	
	/**
	 * This method is used to return the list of valid actions for a specified status, the actions should be a list of String as we are sending the to the ui for display
	 * @param status A String with the status name
	 * @return
	 * @throws InvalidStatusException
	 * @author Ramiro.Serrato
	 */
	public Set<String> getActionsForStatus(String status) throws InvalidStatusException;
}
