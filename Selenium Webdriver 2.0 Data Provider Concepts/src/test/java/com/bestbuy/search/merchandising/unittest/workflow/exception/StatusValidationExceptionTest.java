/**
 * Oct 10, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.unittest.workflow.exception;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.workflow.exception.StatusValidationException;

/**
 * @author Ramiro.Serrato
 *
 */
public class StatusValidationExceptionTest {

	/**
	 * @throws java.lang.Exception
	 * @author Ramiro.Serrato
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.StatusValidationException#StatusValidationException()}.
	 */
	@Test
	public void testStatusValidationException() {
		new StatusValidationException();
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.StatusValidationException#StatusValidationException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testStatusValidationExceptionStringThrowable() {
		new StatusValidationException("Testing workflow exception", new Throwable());
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.StatusValidationException#StatusValidationException(java.lang.String)}.
	 */
	@Test
	public void testStatusValidationExceptionString() {
		new StatusValidationException("Testing workflow exception");
	}

	/**
	 * Test method for {@link com.bestbuy.search.merchandising.workflow.exception.StatusValidationException#StatusValidationException(java.lang.Throwable)}.
	 */
	@Test
	public void testStatusValidationExceptionThrowable() {
		new StatusValidationException(new Throwable("Testing workflow exception"));
	}

}
