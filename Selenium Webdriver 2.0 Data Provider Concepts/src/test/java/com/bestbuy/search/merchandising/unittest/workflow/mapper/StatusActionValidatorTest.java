/**
 * Oct 9, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.mapper;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.unittest.common.WorkflowBaseData;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator;

/**
 * @author Ramiro.Serrato
 *
 */
public class StatusActionValidatorTest {
	private WorkflowBaseData workflowBaseData;
	private StatusActionValidator<GeneralStatus, GeneralAction> validator;	

	/**
	 * @throws java.lang.Exception
	 * @author Ramiro.Serrato
	 */
	@Before
	public void setUp() throws Exception {
		workflowBaseData = new WorkflowBaseData();
		validator = workflowBaseData.getValidator();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateStep(com.bestbuy.search.merchandising.workflow.Step)}.
	 */
	@Test
	public void testValidateStep() {
		try {
			validator.validateStep(workflowBaseData.getStep());
		} 
	
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid Status found");
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid Action found");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateSteps(java.util.Set)}.
	 */
	@Test
	public void testValidateSteps() {
		try {
			validator.validateSteps(workflowBaseData.getSteps());
		} 
	
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid Status found");
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid Action found");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateStatus(java.lang.Enum)}.
	 */
	@Test
	public void testValidateStatus() {
		GeneralStatus status = workflowBaseData.getStatus();
		
		try {
			validator.validateStatus(status);
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Found an invalid status");
		}
	}
	
	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateStatus(java.lang.Enum)}.
	 */
	@Test
	public void testValidateStatusException() {
		GeneralStatus status = workflowBaseData.getStatus1();
		
		try {
			validator.validateStatus(status);
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateAction(java.lang.Enum)}.
	 */
	@Test
	public void testValidateAction() {
		GeneralAction action = workflowBaseData.getAction();
		
		try {
			validator.validateAction(action);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Found an invalid Action");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateAction(java.lang.Enum)}.
	 */
	@Test
	public void testValidateActionException() {
		GeneralAction action = workflowBaseData.getAction1();
		
		try {
			validator.validateAction(action);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateActions(java.util.Set)}.
	 */
	@Test
	public void testValidateActions() {
		Set<GeneralAction> actions = workflowBaseData.getActions();
		
		try {
			validator.validateActions(actions);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Found an invalid Action");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator#validateStatuses(java.util.Set)}.
	 */
	@Test
	public void testValidateStatuses() {
		Set<GeneralStatus> statuses = workflowBaseData.getStatuses();
		
		try {
			validator.validateStatuses(statuses);
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Found an invalid Status");
		}
	}

}
