/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.exception;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;

/**
 * @author Ramiro.Serrato
 *
 */
public class InvalidActionExceptionTest {

	/**
	 * @throws java.lang.Exception
	 * @author Ramiro.Serrato
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.InvalidActionException#InvalidActionException()}.
	 */
	@Test
	public void testInvalidActionException() {
		new InvalidActionException();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.InvalidActionException#InvalidActionException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testInvalidActionExceptionStringThrowable() {
		new InvalidActionException("Testing workflow exception", new Throwable());
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.InvalidActionException#InvalidActionException(java.lang.String)}.
	 */
	@Test
	public void testInvalidActionExceptionString() {
		new InvalidActionException("Testing workflow exception");
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.InvalidActionException#InvalidActionException(java.lang.Throwable)}.
	 */
	@Test
	public void testInvalidActionExceptionThrowable() {
		new InvalidActionException(new Throwable("Testing workflow exception"));
	}

}
