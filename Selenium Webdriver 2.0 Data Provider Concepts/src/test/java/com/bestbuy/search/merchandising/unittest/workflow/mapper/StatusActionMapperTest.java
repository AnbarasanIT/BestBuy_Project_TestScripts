/**
 * Oct 9, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.mapper;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.unittest.common.WorkflowBaseData;
import com.bestbuy.search.merchandising.workflow.Step;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper;
import com.bestbuy.search.merchandising.workflow.mapper.StatusActionValidator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;

/**
 * @author Ramiro.Serrato
 *
 */
public class StatusActionMapperTest {
	private WorkflowBaseData workflowBaseData;
	private StatusActionMapper<GeneralStatus, GeneralAction> mapper;
	private StatusActionValidator<GeneralStatus, GeneralAction> validator;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		workflowBaseData = new WorkflowBaseData();
		mapper = workflowBaseData.getStatusActionMapper();
		validator = mock(StatusActionValidator.class);
		mapper.setValidator(validator);
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#StatusActionMapper(java.util.Set, java.util.Set, java.lang.Class)}.
	 */
	@Test
	public void testStatusActionMapper() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#addActionForStatus(java.lang.Enum, java.lang.Enum)}.
	 */
	@Test
	public void testAddActionForStatus() {
		try {
			mapper.addActionForStatus(workflowBaseData.getStatus(), workflowBaseData.getAction());
			assertTrue( mapper.getActionsForStatus(workflowBaseData.getStatus()).equals(workflowBaseData.getActions()) );
		} 

		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#setActionsForStatus(java.lang.Enum, java.util.Set)}.
	 */
	@Test
	public void testSetActionsForStatus() {
		try {
			mapper.setActionsForStatus(workflowBaseData.getStatus(), workflowBaseData.getActions());
			assertTrue( mapper.getActionsForStatus(workflowBaseData.getStatus()).equals(workflowBaseData.getActions()) );
		} 

		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#getActionsForStatus(java.lang.Enum)}.
	 */
	@Test
	public void testGetActionsForStatus() {
		try {
			mapper.setActionsForStatus(workflowBaseData.getStatus(), workflowBaseData.getActions());
			assertTrue( mapper.getActionsForStatus(workflowBaseData.getStatus()).equals(workflowBaseData.getActions()) );
		} 

		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#setSuccessor(java.lang.Enum, java.lang.Enum, java.lang.Enum)}.
	 */
	@Test
	public void testSetSuccessor() {
		try {		
			mapper.setSuccessor(workflowBaseData.getStatus(), workflowBaseData.getAction(), workflowBaseData.getStatus1());
			assertTrue(mapper.getSuccessor(workflowBaseData.getStatus(), workflowBaseData.getAction()) != null);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#setSuccessorForStep(com.bestbuy.search.merchandising.workflow.Step, java.lang.Enum)}.
	 */
	@Test
	public void testSetSuccessorForStep() {
		try {		
			mapper.setSuccessorForStep(workflowBaseData.getStep(), workflowBaseData.getStatus());
			assertTrue(mapper.getSuccessor(workflowBaseData.getStatus(), workflowBaseData.getAction()) != null);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#setSuccessorsForStep(java.util.Map)}.
	 */
	@Test
	public void testSetSuccessorsForStep() {
		try {
			Map<Step<GeneralStatus, GeneralAction>, GeneralStatus> successors = workflowBaseData.getSuccessors();
			successors.put(new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction()), workflowBaseData.getStatus1());
			
			doNothing().when(validator).validateStatus(workflowBaseData.getStatus());
			mapper.setSuccessorsForStep(successors);
			assertTrue(mapper.getSuccessor(workflowBaseData.getStatus(), workflowBaseData.getAction()) != null);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");			
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#getSuccessor(java.lang.Enum, java.lang.Enum)}.
	 */
	@Test
	public void testGetSuccessor() {
		try {
			Map<Step<GeneralStatus, GeneralAction>, GeneralStatus> successors = workflowBaseData.getSuccessors();
			successors.put(new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction()), workflowBaseData.getStatus1());
			doNothing().when(validator).validateStatus(workflowBaseData.getStatus());
			mapper.setSuccessorsForStep(successors);
			
			assertTrue(mapper.getSuccessor(workflowBaseData.getStatus(), workflowBaseData.getAction()) != null);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#getSuccessorForStep(com.bestbuy.search.merchandising.workflow.Step)}.
	 * @throws InvalidStatusException 
	 * @throws InvalidActionException 
	 */
	@Test
	public void testGetSuccessorForStep() {
		try {
			Map<Step<GeneralStatus, GeneralAction>, GeneralStatus> successors = workflowBaseData.getSuccessors();
			successors.put(new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction()), workflowBaseData.getStatus1());
			doNothing().when(validator).validateStatus(workflowBaseData.getStatus());
			mapper.setSuccessorsForStep(successors);
			
			assertTrue(mapper.getSuccessorForStep(workflowBaseData.getStep()) != null);
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("Invalid action");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("Invalid status");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.mapper.StatusActionMapper#getValidator()}.
	 */
	@Test
	public void testGetValidator() {
		assertTrue(mapper.getValidator() != null);
	}
}
