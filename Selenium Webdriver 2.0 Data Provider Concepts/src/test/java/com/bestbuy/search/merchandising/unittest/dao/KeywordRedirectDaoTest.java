package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.KeywordRedirectDAO;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.unittest.common.RedirectTestData;

/**
 * @author Kalaiselvi Jaganathan
 * Modified By Chanchal Kumari added TestSave() Method
 */

public class KeywordRedirectDaoTest {
	private final static Logger logger = Logger.getLogger(KeywordRedirectDaoTest.class);

	KeywordRedirectDAO redirectDAO = null;
	EntityManager entityManager = null;

	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		redirectDAO=new KeywordRedirectDAO();
		entityManager=mock(EntityManager.class);
		redirectDAO.setEntityManager(entityManager);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		redirectDAO = null;
		entityManager = null;
	}

/*	*//**
	 * Test Case for Equals Method
	 *//*
	@Test
	public void testKeywordRedirect(){
		KeywordRedirect redirect1 = BaseData.getRedirects();
		KeywordRedirect redirect2 = BaseData.getRedirects();
		KeywordRedirect redirect3 = BaseData.getRedirects();
		assertEquals("Object Not Equals",false,redirect1.equals(redirect3));
		assertEquals("Object Equals",true,redirect1.equals(redirect1));
		assertEquals("Object Equals false",false,redirect1.equals(null));
		redirect2.setSearchProfile(new SearchProfile());
		assertFalse(redirect1.equals(redirect2));
		assertTrue( redirect1.hashCode() != redirect2.hashCode());
		assertFalse(redirect1.getClass() != redirect2.getClass());
		assertEquals("Class Not Equals",false,(this.getClass()).equals(redirect1.getClass()));
		assertEquals("Class Not Equals",true,redirect1.getClass() == redirect2.getClass());
		assertNotNull(redirect2.toString());
		assertEquals("Redirect Type Equals",true,redirect1.getRedirectType().equals(redirect3.getRedirectType()));
		assertEquals("Redirect String Equals",true,redirect1.getRedirectString().equals(redirect3.getRedirectString()));
		assertEquals("Redirect Search Profile Not Equals",false,redirect1.getSearchProfile().equals(redirect3.getSearchProfile()));
		//assertEquals("Redirect Keyword Equals",false,redirect1.getKeywordRedirect().equals(redirect3.getKeywordRedirect()));
		assertFalse(redirect1.equals(null));
		assertFalse(redirect1.equals(new Asset()));
		redirect2 = new KeywordRedirect();
		assertFalse(redirect1.equals(redirect2));	
		assertFalse(redirect2.equals(redirect1));
		redirect1 = new KeywordRedirect();
		assertTrue(redirect1.equals(redirect2));
		assertFalse(redirect1.hashCode() != redirect2.hashCode());
	} */
	
	/**
	  * TestCase for saveMethod
	  */
	
	 @Test
	 public void testSave(){
		 try{
			 
			 KeywordRedirect keywordRedirect = RedirectTestData.getKeywordRedirect();
			 doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return RedirectTestData.getSavedKeywordRedirect();
			 	     }
			 	   }).when(entityManager).persist(keywordRedirect);
			 keywordRedirect = redirectDAO.save(keywordRedirect);
				assertNotNull(keywordRedirect);
				assertTrue(keywordRedirect.getKeyword().equals("chanchal"));
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
}
