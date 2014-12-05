package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.AttributeValueDAO;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.service.AttributeValueService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class AttributeValueServiceTest {
	AttributeValueDAO attributeValueDAO;
	AttributeValueService attributeValueService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		attributeValueService = new AttributeValueService();
		attributeValueDAO = mock(AttributeValueDAO.class);
		attributeValueService.setDao(attributeValueDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		attributeValueService = null;
		attributeValueDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		AttributeValue AttributeValue = BaseData.getAttributeValueList().get(0);
		when(attributeValueService.save(AttributeValue)).thenReturn(AttributeValue);
		AttributeValue=attributeValueDAO.save(AttributeValue);
		assertNotNull(AttributeValue);
	}
}
