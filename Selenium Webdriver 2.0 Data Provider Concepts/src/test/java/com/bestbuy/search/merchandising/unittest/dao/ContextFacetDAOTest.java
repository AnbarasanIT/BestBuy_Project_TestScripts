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
import com.bestbuy.search.merchandising.dao.ContextFacetDAO;
import com.bestbuy.search.merchandising.domain.ContextFacet;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for ContextFacet
 */
public class ContextFacetDAOTest {
	private final static Logger logger = Logger.getLogger(ContextFacetDAOTest.class);
	
	ContextFacetDAO contextFacetDAO = null;
	EntityManager entityManager = null;

	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		contextFacetDAO=new ContextFacetDAO();
		entityManager=mock(EntityManager.class);
		contextFacetDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		contextFacetDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
		 ContextFacet contextFacet = BaseData.getContextFacetData();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getContextFacetData();
			 	     }
			 	   }).when(entityManager).persist(contextFacet);
			 	contextFacet = contextFacetDAO.save(contextFacet);
			 	assertNotNull(contextFacet);
				assertNotNull(contextFacet.getContextFacetId().getFacet());
				assertTrue(contextFacet.getContextFacetId().getFacet().getFacetId() == 1L);
	 }
}
