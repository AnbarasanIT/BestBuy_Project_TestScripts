package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.AttributeValueDAO;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class AttributeValueDAOTest {
	
	AttributeValueDAO attributeValueDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		attributeValueDAO=new AttributeValueDAO();
		entityManager=mock(EntityManager.class);
		attributeValueDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		attributeValueDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
		 List<AttributeValue> attributeValues = BaseData.getAttributeValueList();
		 AttributeValue attributeValue=attributeValues.get(0);
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getAttribute();
			 	     }
			 	   }).when(entityManager).persist(attributeValue);
			 	attributeValue = attributeValueDAO.save(attributeValue);
			 	assertNotNull(attributeValue);
				//assertTrue(attributeValue.getAttributeValue() == "Name");
				assertTrue(attributeValue.getAttributeValueId() == 1L);
	 }

}
