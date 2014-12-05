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
import com.bestbuy.search.merchandising.dao.ContextDAO;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for ContextDAO
 */
public class ContextDAOTest {
		
		private final static Logger logger = Logger.getLogger(ContextDAOTest.class);
		
		ContextDAO contextDAO = null;
		EntityManager entityManager = null;
		
		/**
		 * Before test case to create the mocks
		 * Creating the dao Layer and its dependent Mocks
		 */
		@Before
		public void init() {
			contextDAO=new ContextDAO();
			entityManager=mock(EntityManager.class);
			contextDAO.setEntityManager(entityManager);
			
		}

		/**
		 * Clear all the Assigned Variables
		 */
		@After
		public void Destroy(){
			contextDAO = null;
			entityManager = null;
		}
		
		/**
		  * TestCase for saveMethod
		 * @throws DataAcessException 
		  */
		 @Test
		 public void testSave() throws DataAcessException{
			 Context context = BaseData.getContextData();
				 	doAnswer(new Answer() {
				 	     public Object answer(InvocationOnMock invocation) {
				 	         Object[] args = invocation.getArguments();
				 	        EntityManager mock = (EntityManager) invocation.getMock();
				 	         return  BaseData.getContextData();
				 	     }
				 	   }).when(entityManager).persist(context);
				 	context = contextDAO.save(context);
				 	assertNotNull(context);
					assertTrue(context.getCategoryNode().getCategoryNodeId() == "pcmcat");
		 }
	}
