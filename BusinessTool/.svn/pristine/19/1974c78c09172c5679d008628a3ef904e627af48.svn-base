package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.FacetDAO;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class FacetDAOTest {

	
	FacetDAO facetDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		facetDAO=new FacetDAO();
		entityManager=mock(EntityManager.class);
		facetDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		facetDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
		 Facet facet = BaseData.getFacet();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getFacet();
			 	     }
			 	   }).when(entityManager).persist(facet);
			 	facet = facetDAO.save(facet);
			 	assertNotNull(facet);
				assertTrue(facet.getFacetId() == 1l);
				assertEquals("1",facet.getPosition());
				assertFalse(facet.getCreatedDate() == new Date());
	 }
}
