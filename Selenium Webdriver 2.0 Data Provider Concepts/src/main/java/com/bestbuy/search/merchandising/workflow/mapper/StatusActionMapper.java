/**
 * Sep 13, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.mapper;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.bestbuy.search.merchandising.workflow.Step;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;

/**
 * This class encapsulates the mapping structure and logic for action-status in the workflow 
 * @author Ramiro.Serrato
 *
 */
public class StatusActionMapper<Status extends Enum<Status>, Action extends Enum<Action>> {
	private StatusActionValidator<Status, Action> validator;
	private Map<Status, Set<Action>> statusActions;
	private Map<Step<Status, Action>, Status> successorStatus;
	
	public StatusActionMapper(Set<Status> statuses, Set<Action> actions, Class<Status> statusEnum) {
		validator = new StatusActionValidator<Status, Action>(statuses, actions);
		statusActions = new EnumMap<Status, Set<Action>>(statusEnum);
		successorStatus = new Hashtable<Step<Status, Action>, Status>();
		
		initializeStatusActionSets(statuses);  // initializing the sets in the map of status-actions so we can add actions individually
	}
	
	public void addActionForStatus(Status status, Action action) throws InvalidStatusException, InvalidActionException{
		validator.validateAction(action);
		validator.validateStatus(status);
		statusActions.get(status).add(action);
	}
	
	public void setActionsForStatus(Status status, Set<Action> actions) throws InvalidActionException, InvalidStatusException {
		validator.validateStatus(status);
		validator.validateActions(actions);
		statusActions.put(status, actions);
	}
	
	public Set<Action> getActionsForStatus(Status status) {
		return statusActions.get(status);
	}
	
	public void setSuccessor(Status status, Action action, Status newStatus) throws InvalidStatusException, InvalidActionException {
		setSuccessorForStep(new Step<Status, Action>(status, action), newStatus);
	}
	
	public void setSuccessorForStep(Step<Status, Action> step, Status newStatus) throws InvalidStatusException, InvalidActionException {
		validator.validateStep(step);
		validator.validateStatus(newStatus);
		successorStatus.put(step, newStatus);
	}
		
	public void setSuccessorsForStep(Map<Step<Status, Action>, Status> successors) throws InvalidActionException, InvalidStatusException{
		validator.validateSteps(successors.keySet());
		validator.validateStatuses(new HashSet<Status>(successors.values()));
		successorStatus = successors;
	}
	
	public Status getSuccessor(Status status, Action action) throws InvalidActionException, InvalidStatusException {
		return getSuccessorForStep(new Step<Status, Action>(status, action));
	}
	
	public Status getSuccessorForStep(Step<Status, Action> step) throws InvalidActionException, InvalidStatusException {
		validator.validateStep(step);
		return successorStatus.get(step);
	}
	
	private void initializeStatusActionSets(Set<Status> statuses) {
		for(Status status : statuses) {
			statusActions.put(status, new LinkedHashSet<Action>());
		}
	}

	public StatusActionValidator<Status, Action> getValidator() {
		return validator;
	}
	
	public void setValidator(StatusActionValidator<Status, Action> validator) {
		this.validator = validator;
	}
}
