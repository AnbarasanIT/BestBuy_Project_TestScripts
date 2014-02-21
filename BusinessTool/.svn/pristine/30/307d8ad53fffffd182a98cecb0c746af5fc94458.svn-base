/**
 * Sep 16, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.mapper;

import java.util.Set;

import com.bestbuy.search.merchandising.workflow.Step;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;

/**
 * @author Ramiro.Serrato
 *
 */
public class StatusActionValidator<Status extends Enum<Status>, Action extends Enum<Action>> {
	private Set<Action> validActions;
	private Set<Status> validStatuses;
	
	public StatusActionValidator(Set<Status> validStatuses, Set<Action> validActions) {
		this.validActions = validActions;
		this.validStatuses = validStatuses;
	}
	
	/**
	 * Validates that the action and status for the step belong to the valid sets, it does not validate if the step is registered
	 * @param step The step to be validated
	 * @throws InvalidActionException
	 * @throws InvalidStatusException
	 * @author Ramiro.Serrato
	 */
	public void validateStep(Step<Status, Action> step) throws InvalidActionException, InvalidStatusException {
		Action action = step.getAction();
		Status status = step.getStatus();
		
		validateAction(action);
		validateStatus(status);
	}
	
	public void validateSteps(Set<Step<Status, Action>> steps) throws InvalidActionException, InvalidStatusException{
		for(Step<Status, Action> step : steps) {
			validateStep(step);
		}
	}	
	
	public void validateStatus(Status status) throws InvalidStatusException {
		if(!validStatuses.contains(status)) {
			throw new InvalidStatusException("The status ["+ status +"] does not belong to the valid status set");
		}
	}
	
	public void validateAction(Action action) throws InvalidActionException {
		if(!validActions.contains(action)) {
			throw new InvalidActionException("The action ["+ action +"] does not belong to the valid action set");
		}
	}

	/**
	 * @param actions
	 * @author Ramiro.Serrato
	 */
	public void validateActions(Set<Action> actions) throws InvalidActionException{
		for(Action action : actions) {
			validateAction(action);
		}
	}
	
	/**
	 * Validates the given set of status by comparing each of the elements with the current set of statuses in this validator
	 * @param statuses The set of status to be validated
	 * @author Ramiro.Serrato
	 */
	public void validateStatuses(Set<Status> statuses) throws InvalidStatusException{
		for(Status status : statuses) {
			validateStatus(status);
		}
	}	
}
