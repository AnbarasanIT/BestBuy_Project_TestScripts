package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.ContextFacetDAO;
import com.bestbuy.search.merchandising.domain.ContextFacet;
import com.bestbuy.search.merchandising.service.ContextFacetService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ContextFacetServiceTest {
	
	ContextFacetDAO contextFacetDAO;
	ContextFacetService contextFacetService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		contextFacetService = new ContextFacetService();
		contextFacetDAO = mock(ContextFacetDAO.class);
		contextFacetService.setDao(contextFacetDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		contextFacetService = null;
		contextFacetDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		ContextFacet ContextFacet = BaseData.getContextFacetData();
		when(contextFacetService.save(ContextFacet)).thenReturn(ContextFacet);
		ContextFacet=contextFacetDAO.save(ContextFacet);
		assertNotNull(ContextFacet);
	}
}
