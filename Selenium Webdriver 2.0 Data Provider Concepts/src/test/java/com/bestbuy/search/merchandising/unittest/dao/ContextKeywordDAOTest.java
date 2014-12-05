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
import com.bestbuy.search.merchandising.dao.ContextKeywordDAO;
import com.bestbuy.search.merchandising.domain.ContextKeyword;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 * Test Case for ContextKeywordDAO
 */
public class ContextKeywordDAOTest {
private final static Logger logger = Logger.getLogger(ContextKeywordDAOTest.class);
	
ContextKeywordDAO contextKeywordDAO = null;
	EntityManager entityManager = null;

	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		contextKeywordDAO=new ContextKeywordDAO();
		entityManager=mock(EntityManager.class);
		contextKeywordDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		contextKeywordDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
		 ContextKeyword contextKeyword = BaseData.getContextKeywordData();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getContextKeywordData();
			 	     }
			 	   }).when(entityManager).persist(contextKeyword);
			 	contextKeyword = contextKeywordDAO.save(contextKeyword);
			 	assertNotNull(contextKeyword);
				assertTrue(contextKeyword.getId().getContextId() == 1L);
	 }
}
