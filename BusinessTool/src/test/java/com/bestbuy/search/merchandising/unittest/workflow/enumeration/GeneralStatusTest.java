/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.enumeration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.unittest.common.WorkflowBaseData;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;

/**
 * @author Ramiro.Serrato
 *
 */
public class GeneralStatusTest {
	private WorkflowBaseData workflowBaseData;
	
	@Before
	public void setUp() {
		workflowBaseData = new WorkflowBaseData();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus#getName()}.
	 */
	@Test
	public void testGetName() {
		GeneralStatus status = workflowBaseData.getStatus();
		assertTrue(status.getName().equals(status.toString()));
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus#toString()}.
	 */
	@Test
	public void testToString() {
		GeneralStatus status = workflowBaseData.getStatus();
		assertTrue(status.getName().equals(status.toString()));
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus#getStatusEnum(java.lang.String)}.
	 */
	@Test
	public void testGetStatusEnum() {
		try {
			GeneralStatus.getStatusEnum("Approved");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
			fail("There is no a status with the given name");
		}
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus#getStatusEnum(java.lang.String)}.
	 */
	@Test
	public void testGetStatusEnumException() {
		try {
			GeneralStatus.getStatusEnum("Wrong Status");
		} 
		
		catch (InvalidStatusException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus#getStatusEnum(java.lang.String)}.
	 */
	@Test
	public void testValueOf() {
		GeneralStatus.valueOf("APPROVED");
	}	
}
