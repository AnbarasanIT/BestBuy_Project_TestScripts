/**
 * Sep 13, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow;

import java.util.EnumSet;
import java.util.Set;

import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper;

/**
 * This abstract class encapsulates the general structure and behavior of the workflow objects
 * @author Ramiro.Serrato
 *
 */
public abstract class SimpleWorkflow<Service, Status extends Enum<Status>, Action extends Enum<Action>> implements IWorkflowable<Status, Action> {
	private Service service;
	private Set<Action> actions;
	private Set<Status> statuses;
	private StatusActionMapper<Status, Action> statusActionMapper; 

	protected SimpleWorkflow(Service service) throws WorkflowCreationException {
		this.service = service;
		init();
		defineRules(); 
	}
	
	public Set<Action> getActions() {
		return actions;
	}
	
	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}
		
	public void setActions(Action... actions) {
		if(actions.length > 0) {
			setActions(EnumSet.of(actions[0], actions));
		}
	}
	
	public Set<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(Set<Status> statuses) {
		this.statuses = statuses;
	}
	
	public void setStatuses(Status... statuses){
		if(statuses.length > 0) {
			setStatuses(EnumSet.of(statuses[0], statuses));
		}
	}
	
	public StatusActionMapper<Status, Action> getStatusActionMapper() {
		return statusActionMapper;
	}
	
	public void setStatusActionMapper(StatusActionMapper<Status, Action> statusActionMapper) {
		this.statusActionMapper = statusActionMapper;
	}

	/**
	 * The init method is the place to initialize the actions, statuses and mappers
	 * @author Ramiro.Serrato
	 */
	protected abstract void init() throws WorkflowCreationException;	
	
	public void registerRule(Status status, Action action, Status newStatus) throws InvalidActionException, InvalidStatusException {
		registerActionForStatus(status, action);
		registerSuccessorForStatusAction(status, action, newStatus);
	}

	/**
	 * Registers an individual action for an specified status, it will alter the current Status-Action mapper
	 * @author Ramiro.Serrato
	 * @throws InvalidStatusException 
	 * @throws InvalidActionException 
	 */	
	protected void registerActionForStatus(Status status, Action action) throws InvalidActionException, InvalidStatusException {
		statusActionMapper.addActionForStatus(status, action);
	}		

	protected void registerSuccessorForStatusAction(Status status, Action action, Status successor) throws InvalidStatusException, InvalidActionException{
		registerSuccessorForStep(new Step<Status, Action>(status, action), successor);
	}
			
	protected void registerSuccessorForStep(Step<Status, Action> step, Status successor) throws InvalidStatusException, InvalidActionException {
		statusActionMapper.setSuccessorForStep(step, successor);
	}
		
	protected Service getService() {
		return service;
	}
	
	protected void checkSetsNotNull() throws WorkflowCreationException {
		if(actions == null || statuses == null || statusActionMapper == null){
			throw new WorkflowCreationException("The actions, statuses and mappers must be initialized in the init method of the workflow");
		}
	}
	
	public void restartRules() throws WorkflowCreationException {
		defineRules();
	}

	protected abstract void defineRules() throws WorkflowCreationException;
}