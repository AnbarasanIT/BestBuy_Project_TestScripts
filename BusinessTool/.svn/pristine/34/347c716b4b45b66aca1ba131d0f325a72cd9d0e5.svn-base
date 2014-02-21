/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.enumeration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.unittest.common.WorkflowBaseData;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;

/**
 * @author Ramiro.Serrato
 *
 */
public class GeneralActionTest {
	private WorkflowBaseData workflowBaseData;
	
	@Before
	public void setUp() {
		workflowBaseData = new WorkflowBaseData();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction#getName()}.
	 */
	@Test
	public void testGetName() {
		GeneralAction action = workflowBaseData.getAction();
		assertTrue(action.getName().equals(action.toString()));
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction#getType()}.
	 */
	@Test
	public void testGetType() {
		GeneralAction action = workflowBaseData.getAction();
		assertTrue(action.getType().equals("User"));
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction#toString()}.
	 */
	@Test
	public void testToString() {
		GeneralAction action = workflowBaseData.getAction();
		assertTrue(action.getName().equals(action.toString()));
	}
	
	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction#getActionEnum(java.lang.String)}.
	 */
	@Test
	public void testGetActionEnum() {
		try {
			GeneralAction.getActionEnum("Approve");
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
			fail("There is no action with the given name");
		}
	}	

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction#getActionEnum(java.lang.String)}.
	 */
	@Test
	public void testGetActionEnumException() {
		try {
			GeneralAction.getActionEnum("Wrong Action");
		} 
		
		catch (InvalidActionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus#getStatusEnum(java.lang.String)}.
	 */
	@Test
	public void testValueOf() {
		GeneralAction.valueOf("APPROVE");
	}	
	
	@Test
	public void testGetEnumType() {
		GeneralAction.getEnumType("USER");
	}
}
