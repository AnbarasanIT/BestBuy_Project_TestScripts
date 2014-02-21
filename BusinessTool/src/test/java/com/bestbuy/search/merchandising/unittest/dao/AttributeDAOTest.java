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
import com.bestbuy.search.merchandising.dao.AttributeDAO;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class AttributeDAOTest {

	AttributeDAO attributeDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		attributeDAO=new AttributeDAO();
		entityManager=mock(EntityManager.class);
		attributeDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		attributeDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
		 Attribute attribute = BaseData.getAttribute();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getAttribute();
			 	     }
			 	   }).when(entityManager).persist(attribute);
			 	attribute = attributeDAO.save(attribute);
			 	assertNotNull(attribute);
				assertTrue(attribute.getAttributeId() == 1l);
				assertTrue(attribute.getAttributeName() == "Name");
				assertTrue(attribute.getAttributeValue().get(0).getAttributeValueId() == 1L);
	 }
}
