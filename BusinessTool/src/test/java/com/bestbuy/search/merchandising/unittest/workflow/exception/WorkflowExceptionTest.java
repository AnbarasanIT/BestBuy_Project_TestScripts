/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.exception;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;

/**
 * @author Ramiro.Serrato
 *
 */
public class WorkflowExceptionTest {

	/**
	 * @throws java.lang.Exception
	 * @author Ramiro.Serrato
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.WorkflowException#WorkflowException()}.
	 */
	@Test
	public void testWorkflowException() {
		new WorkflowException();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.WorkflowException#WorkflowException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testWorkflowExceptionStringThrowable() {
		new WorkflowException("Testing workflow exception", new Throwable());
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.WorkflowException#WorkflowException(java.lang.String)}.
	 */
	@Test
	public void testWorkflowExceptionString() {
		new WorkflowException("Testing workflow exception");
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.WorkflowException#WorkflowException(java.lang.Throwable)}.
	 */
	@Test
	public void testWorkflowExceptionThrowable() {
		new WorkflowException(new Throwable("Testing workflow exception"));
	}
}
