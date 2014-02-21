/**
 * Oct 9, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.common;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.workflow.Step;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator;

import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.*;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.*;

/**
 * @author Ramiro.Serrato
 *
 */
public class WorkflowBaseData {
	private GeneralStatus status;
	private GeneralStatus status1;
	private GeneralAction action;
	private GeneralAction action1;
	private Set<GeneralStatus> statuses;
	private Set<GeneralAction> actions;
	private StatusActionMapper<GeneralStatus, GeneralAction> statusActionMapper;
	private Step<GeneralStatus, GeneralAction> step;
	private Set<Step<GeneralStatus, GeneralAction>> steps;
	private Map<Step<GeneralStatus, GeneralAction>, GeneralStatus> successors;
	private Map<GeneralStatus, Set<GeneralAction>> actionsForStatus;
	private StatusActionValidator<GeneralStatus, GeneralAction> validator;
	private List<Status> dbStatuses;
	private List<Status> dbStatuses1;
	
	public WorkflowBaseData() {		
		status = APPROVED;
		status1 = REJECTED;
		action = REJECT;
		action1 = APPROVE;
		
		step = new Step<GeneralStatus, GeneralAction>(status, action);
		steps = new HashSet<Step<GeneralStatus, GeneralAction>>();
		steps.add(step);
		
		statuses = EnumSet.of(status);
		actions = EnumSet.of(action);
		actionsForStatus = new EnumMap<GeneralStatus, Set<GeneralAction>>(GeneralStatus.class);
		
		statusActionMapper = new StatusActionMapper<GeneralStatus, GeneralAction>(statuses, actions, GeneralStatus.class);
		successors = new HashMap<Step<GeneralStatus, GeneralAction>, GeneralStatus>();
		
		validator = new StatusActionValidator<GeneralStatus, GeneralAction>(statuses, actions);
		
		dbStatuses = new ArrayList<Status>();
		Status dbStatus1 = new Status();
		dbStatus1.setStatus("Approved");
		Status dbStatus2 = new Status();
		dbStatus2.setStatus("Rejected");
		Status dbStatus3 = new Status();
		dbStatus3.setStatus("Deleted");
		Status dbStatus4 = new Status();
		dbStatus4.setStatus("Draft");
		Status dbStatus5 = new Status();
		dbStatus5.setStatus("Published");
		Status dbStatus6 = new Status();
		dbStatus6.setStatus("Not Valid");
		
		dbStatuses.add(dbStatus1);
		dbStatuses.add(dbStatus2);
		dbStatuses.add(dbStatus3);
		dbStatuses.add(dbStatus4);
		dbStatuses.add(dbStatus5);
		dbStatuses.add(dbStatus6);
		
		dbStatuses1 = new ArrayList<Status>();
		dbStatuses1.add(dbStatus1);
		dbStatuses1.add(dbStatus2);
		dbStatuses1.add(dbStatus3);
		dbStatuses1.add(dbStatus4);
	}
	
	public StatusActionValidator<GeneralStatus, GeneralAction> getStatusActionValidator() {
		return new StatusActionValidator<GeneralStatus, GeneralAction>(statuses, actions);
	}

	public GeneralStatus getStatus() {
		return status;
	}

	public void setStatus(GeneralStatus status) {
		this.status = status;
	}

	public GeneralAction getAction() {
		return action;
	}

	public void setAction(GeneralAction action) {
		this.action = action;
	}

	public Set<GeneralStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(Set<GeneralStatus> statuses) {
		this.statuses = statuses;
	}

	public Set<GeneralAction> getActions() {
		return actions;
	}

	public void setActions(Set<GeneralAction> actions) {
		this.actions = actions;
	}

	public StatusActionMapper<GeneralStatus, GeneralAction> getStatusActionMapper() {
		return statusActionMapper;
	}

	public void setStatusActionMapper(
		StatusActionMapper<GeneralStatus, GeneralAction> statusActionMapper) {
		this.statusActionMapper = statusActionMapper;
	}

	public Step<GeneralStatus, GeneralAction> getStep() {
		return step;
	}

	public void setStep(Step<GeneralStatus, GeneralAction> step) {
		this.step = step;
	}

	public Map<Step<GeneralStatus, GeneralAction>, GeneralStatus> getSuccessors() {
		return successors;
	}

	public void setSuccessors(
			Map<Step<GeneralStatus, GeneralAction>, GeneralStatus> successors) {
		this.successors = successors;
	}

	public GeneralStatus getStatus1() {
		return status1;
	}

	public void setStatus1(GeneralStatus status1) {
		this.status1 = status1;
	}

	public Map<GeneralStatus, Set<GeneralAction>> getActionsForStatus() {
		return actionsForStatus;
	}

	public void setActionsForStatus(
			Map<GeneralStatus, Set<GeneralAction>> actionsForStatus) {
		this.actionsForStatus = actionsForStatus;
	}

	public StatusActionValidator<GeneralStatus, GeneralAction> getValidator() {
		return validator;
	}

	public void setValidator(
			StatusActionValidator<GeneralStatus, GeneralAction> validator) {
		this.validator = validator;
	}

	public GeneralAction getAction1() {
		return action1;
	}

	public void setAction1(GeneralAction action1) {
		this.action1 = action1;
	}

	public Set<Step<GeneralStatus, GeneralAction>> getSteps() {
		return steps;
	}

	public void setSteps(Set<Step<GeneralStatus, GeneralAction>> steps) {
		this.steps = steps;
	}

	public List<Status> getDbStatuses() {
		return dbStatuses;
	}

	public void setDbStatuses(List<Status> dbStatuses) {
		this.dbStatuses = dbStatuses;
	}

	public List<Status> getDbStatuses1() {
		return dbStatuses1;
	}

	public void setDbStatuses1(List<Status> dbStatuses1) {
		this.dbStatuses1 = dbStatuses1;
	}	
}
