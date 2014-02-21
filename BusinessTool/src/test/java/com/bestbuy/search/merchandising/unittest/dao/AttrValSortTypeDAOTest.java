package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.AttrValSortTypeDAO;
import com.bestbuy.search.merchandising.domain.AttrValSortType;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class AttrValSortTypeDAOTest {
	AttrValSortTypeDAO attrValSortTypeDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		attrValSortTypeDAO=new AttrValSortTypeDAO();
		entityManager=mock(EntityManager.class);
		attrValSortTypeDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		attrValSortTypeDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
		 AttrValSortType attrValSortType = BaseData.getAttrValSortType();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getAttribute();
			 	     }
			 	   }).when(entityManager).persist(attrValSortType);
			 	attrValSortType = attrValSortTypeDAO.save(attrValSortType);
			 	assertNotNull(attrValSortType);
				//assertTrue(attributeValue.getAttributeValue() == "Name");
				assertTrue(attrValSortType.getValSortTypeId() == 1L);
	 }
}
