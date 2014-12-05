/**
 * Sep 26, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow;

import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.INVALID;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.AF_PUBLISHED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.APPROVED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.CF_PUBLISHED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.DELETED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.DRAFT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.EXPIRED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.NOT_VALID;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.REJECTED;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.StatusValidationException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper;

/**
 * @author Ramiro.Serrato
 *
 */
public class GeneralWorkflow extends SimpleWorkflow<StatusService, GeneralStatus, GeneralAction> {
		
	/**
	 * @param service
	 * @throws WorkflowCreationException
	 */
	public GeneralWorkflow(StatusService service) throws WorkflowCreationException {
		super(service);
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.SimpleWorkflow#init()
	 */
	@Override 
	protected void init() throws WorkflowCreationException {
		// registering the global sets of statuses and actions
		setStatuses(APPROVED, REJECTED, DELETED, DRAFT, CF_PUBLISHED, NOT_VALID, EXPIRED, AF_PUBLISHED);
		setActions(APPROVE, EDIT, REJECT, DELETE, PUBLISH, INVALID);
		
		// initialize the Mappers
		StatusActionMapper<GeneralStatus, GeneralAction> statusActionMapper = new StatusActionMapper<GeneralStatus, GeneralAction>(getStatuses(), getActions(), GeneralStatus.class);
		setStatusActionMapper(statusActionMapper);
	}
	
	protected void defineRules() throws WorkflowCreationException{
		try {
			checkSetsNotNull();
			// validateStatusesVsDatabase();  // validating the set of statuses against those in the db  COMMENTED LINE: Dec 3, 2012 in order to fix jenkins issue for accessing the db in test running time
			
			// rules for approved
			registerRule(APPROVED, EDIT, APPROVED);
			registerRule(APPROVED, REJECT, REJECTED);
			registerRule(APPROVED, DELETE, DELETED);
			registerRule(APPROVED, PUBLISH, AF_PUBLISHED);
			registerRule(APPROVED, INVALID, NOT_VALID);
			
			// rules for rejected
			registerRule(REJECTED, APPROVE, APPROVED);
			registerRule(REJECTED, EDIT, REJECTED);
			registerRule(REJECTED, DELETE, DELETED);
			registerRule(REJECTED, INVALID, NOT_VALID);
			
			// rules for draft
			registerRule(DRAFT, APPROVE, APPROVED);
			registerRule(DRAFT, EDIT, DRAFT);
			registerRule(DRAFT, REJECT, REJECTED);
			registerRule(DRAFT, DELETE, DELETED);	
			registerRule(DRAFT, INVALID, NOT_VALID);
			
			registerRule(DELETED, INVALID, NOT_VALID);
			
			registerRule(CF_PUBLISHED, EDIT, APPROVED);
			registerRule(CF_PUBLISHED, REJECT, REJECTED);
			registerRule(CF_PUBLISHED, DELETE, DELETED);
			registerRule(CF_PUBLISHED, INVALID, NOT_VALID);
			
			registerRule(AF_PUBLISHED, EDIT, APPROVED);
			registerRule(AF_PUBLISHED, REJECT, REJECTED);
			registerRule(AF_PUBLISHED, DELETE, DELETED);
			registerRule(AF_PUBLISHED, INVALID, NOT_VALID);
			registerRule(AF_PUBLISHED, PUBLISH, CF_PUBLISHED);
			
			registerRule(NOT_VALID, EDIT, DRAFT);
			registerRule(NOT_VALID, REJECT, REJECTED);
			registerRule(NOT_VALID, DELETE, DELETED);
			
			registerRule(EXPIRED, EDIT, DRAFT);
			registerRule(EXPIRED, DELETE, DELETED);
		}
		
		catch (InvalidActionException e) {
			throw new WorkflowCreationException("An invalid action was found", e);
		} 
		
		catch (InvalidStatusException e) {
			throw new WorkflowCreationException("An invalid status was found", e);
		}
		
//		catch(StatusValidationException e) {
//			throw new WorkflowCreationException("Could not validate a status", e);
//		}
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.IWorkflowable#stepForward(java.lang.String, java.lang.String)
	 */
	@Override
	public String stepForward(String status, Enum<GeneralAction> action) throws WorkflowException {
		return getStatusActionMapper().getSuccessor(GeneralStatus.getStatusEnum(status), (GeneralAction) action).toString();
	}	
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.IWorkflowable#stepForward(java.lang.Enum, java.lang.Enum)
	 */
	@Override
	public String stepForward(Enum<GeneralStatus> status, Enum<GeneralAction> action) throws WorkflowException {
		return getStatusActionMapper().getSuccessor((GeneralStatus) status, (GeneralAction) action).toString();
	}

	/**
	 * Validates the current set of status of this workflow against those in the database
	 * @throws StatusValidationException
	 * @author Ramiro.Serrato
	 */
	private void validateStatusesVsDatabase() throws StatusValidationException {
		try {
			List<Status> dbStatuses = (List<Status>)getService().retrieveAll();
			Set<GeneralStatus> statuses = getStatuses();
			List<String> statusNames = getStatusesNames(dbStatuses);
			
			for(GeneralStatus status : statuses) {
				if(!statusNames.contains(status.getName())) {
					throw new StatusValidationException("The status ["+status.getName()+"] was not found in the set of status retrieved from the database");
				}
			}
		} 
		
		catch (ServiceException e) {
			throw new StatusValidationException("Error while retrieving the status from the DB", e);
		}
	}	
	
	/**
	 * Retrieves the name of the statuses and put them in a list of String
	 * @param statuses The list of 
	 * @return
	 * @author Ramiro.Serrato
	 */
	private List<String> getStatusesNames(List<Status> statuses) {
		List<String> statusNames = new ArrayList<String>();
		
		for(Status status : statuses) {
			statusNames.add(status.getStatus());
		}
		
		return statusNames;
	}	
	
	
	/**
	 * This method is used to return the list of valid "user" actions for a specified status, the actions should be a list of String as we are sending the to the ui for display
	 * @param status A String with the status name
	 * @return
	 * @throws InvalidStatusException
	 * @author Ramiro.Serrato
	 */
	public Set<String> getActionsForStatus(String status) throws InvalidStatusException {
		LinkedHashSet<String> actions = new LinkedHashSet<String>();
		GeneralStatus enumStatus = GeneralStatus.getStatusEnum(status);
		getStatusActionMapper().getValidator().validateStatus(enumStatus);
		Set<GeneralAction> enumActions = getStatusActionMapper().getActionsForStatus(enumStatus);
		
		for(GeneralAction action : enumActions) {
			if(! action.getType().equals("System")){
				actions.add(action.getName());
			}
		}
		
		return actions;
	}
}
