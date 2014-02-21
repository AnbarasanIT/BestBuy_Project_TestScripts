/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.unittest.common.WorkflowBaseData;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

/**
 * @author Ramiro.Serrato
 *
 */
public class GeneralWorkflowTest {
	private WorkflowBaseData workflowBaseData;
	private GeneralWorkflow workflow;
	private StatusService service;
	private StatusActionMapper<GeneralStatus, GeneralAction> mapper;

	/**
	 * @throws java.lang.Exception
	 * @author Ramiro.Serrato
	 */
	@Before
	public void setUp() throws Exception {
		workflowBaseData = new WorkflowBaseData();
		service = mock(StatusService.class);
		when(service.retrieveAll()).thenReturn(workflowBaseData.getDbStatuses());
		workflow = new GeneralWorkflow(service);
	}
	@Ignore
	@Test
	public void testRestartRules(){
		try {
			workflow.restartRules();
		} catch (WorkflowCreationException e) {
			e.printStackTrace();
			fail("It was not possible to restart the rules");
		}
	}
	
	@Test
	public void testCheckSetsNotNullCombination1(){
		Set<GeneralAction> actions = null;
		workflow.setActions(actions);
		
		try {
			workflow.restartRules();
		} 
		
		catch (WorkflowCreationException e) {
		}
	}	
	
	@Test
	public void testCheckSetsNotNullCombination2(){
		Set<GeneralStatus> statuses = null; 
		workflow.setStatuses(statuses);
		
		try {
			workflow.restartRules();
		} 
		
		catch (WorkflowCreationException e) {
		}
	}
	
	@Test
	public void testCheckSetsNotNullCombination3(){
		StatusActionMapper<GeneralStatus, GeneralAction> mapper = null;
		workflow.setStatusActionMapper(mapper);
		
		try {
			workflow.restartRules();
		} 
		
		catch (WorkflowCreationException e) {
		}
	}
	
	@Test
	public void testSetActionsEmpty(){ 
		GeneralAction[] actions = {};
		
		workflow.setActions(actions);
		
		try {
			workflow.restartRules();
		} 
		
		catch (WorkflowCreationException e) {
		}
	}	
	
	@Test
	public void testSetStatusEmpty(){ 
		GeneralStatus[] statuses = {};
		
		workflow.setStatuses(statuses);
		
		try {
			workflow.restartRules();
		}  
		
		catch (WorkflowCreationException e) {
		}
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInitInvalidActionException(){
		mapper = mock(StatusActionMapper.class);
		
		try {
			doThrow(InvalidActionException.class).when(mapper).addActionForStatus(workflowBaseData.getStatus(), workflowBaseData.getAction());
		} 
		
		catch (InvalidStatusException e) {
		} 
		
		catch (InvalidActionException e) {
		}
		
		workflow.setStatusActionMapper(mapper);
		
		try {
			workflow.restartRules();
		} 
		
		catch (WorkflowCreationException e) {
		}
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInitInvalidStatusException(){
		mapper = mock(StatusActionMapper.class);
		
		try {
			doThrow(InvalidStatusException.class).when(mapper).addActionForStatus(workflowBaseData.getStatus(), workflowBaseData.getAction());
		} 
		
		catch (InvalidStatusException e) {
		} 
		
		catch (InvalidActionException e) {
		}
		
		workflow.setStatusActionMapper(mapper);
		
		try {
			workflow.restartRules();
		} 
		
		catch (WorkflowCreationException e) {
		}		
	}
	
	@Test
	public void testValidateStatusesVsDataBaseStatusValidationException(){
		try {
			when(service.retrieveAll()).thenReturn(workflowBaseData.getDbStatuses1());
			workflow = new GeneralWorkflow(service);
		} 
		
		catch (ServiceException e) {
			e.printStackTrace();
		} 
		
		catch (WorkflowCreationException e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testValidateStatusesVsDataBaseServiceException(){
		try {
			when(service.retrieveAll()).thenThrow(ServiceException.class);
			workflow = new GeneralWorkflow(service);
		} 
		
		catch (ServiceException e) {
		} 
		
		catch (WorkflowCreationException e) {
		}
	}
	
	

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.GeneralWorkflow#stepForward(java.lang.String, com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction)}.
	 */
	@Ignore
	@Test
	public void testStepForwardStringGeneralAction() {
		try {
			workflow.stepForward(workflowBaseData.getStatus().getName(), workflowBaseData.getAction());
		} 
		
		catch (WorkflowException e) {
			e.printStackTrace();
			fail("Not able to step the workflow");
		}
	}	

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.GeneralWorkflow#stepForward(com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus, com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction)}.
	 */
	@Ignore
	@Test
	public void testStepForwardGeneralStatusGeneralAction() {
		try {
			workflow.stepForward(workflowBaseData.getStatus(), workflowBaseData.getAction());
		} 
		
		catch (WorkflowException e) {
			e.printStackTrace();
			fail("Not able to step the workflow");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.GeneralWorkflow#getActionsForStatus(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetActionsForStatus() {
		Set<String> actions = null;
		
		try {
			actions = workflow.getActionsForStatus("Approved");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("The status is not valid");
		}
		
		assertTrue(actions != null);
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.SimpleWorkflow#getActions()}.
	 */
	@Test
	public void testGetActions() {
		Set<GeneralAction> actions = workflow.getActions();
		assertTrue(actions != null);
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.SimpleWorkflow#getStatuses()}.
	 */
	@Test
	public void testGetStatuses() {
		Set<GeneralStatus> statuses = workflow.getStatuses();
		assertTrue(statuses != null);
	}
}
