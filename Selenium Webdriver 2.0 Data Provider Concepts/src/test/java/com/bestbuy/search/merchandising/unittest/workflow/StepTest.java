/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.unittest.common.WorkflowBaseData;
import com.bestbuy.search.merchandising.workflow.Step;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;

/**
 * @author Ramiro.Serrato
 *
 */
public class StepTest {
	private WorkflowBaseData workflowBaseData;
	
	@Before
	public void setUp() {
		workflowBaseData = new WorkflowBaseData();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.Step#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		Step<GeneralStatus, GeneralAction> step = workflowBaseData.getStep();
		assertTrue(step.getStatus() != null);
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.Step#getAction()}.
	 */
	@Test
	public void testGetAction() {
		Step<GeneralStatus, GeneralAction> step = workflowBaseData.getStep();
		assertTrue(step.getAction() != null);
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.Step#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Step<GeneralStatus, GeneralAction> step1 = new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction());
		Step<GeneralStatus, GeneralAction> step2 = new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction());
		assertTrue(step1.equals(step2));
	}
	
	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.Step#equals(java.lang.Object)}.
	 */
	@Test
	public void testHashCode() {
		Step<GeneralStatus, GeneralAction> step1 = new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction());
		Step<GeneralStatus, GeneralAction> step2 = new Step<GeneralStatus, GeneralAction>(workflowBaseData.getStatus(), workflowBaseData.getAction());
		assertTrue(step1.hashCode() == step2.hashCode());
	}	
}
