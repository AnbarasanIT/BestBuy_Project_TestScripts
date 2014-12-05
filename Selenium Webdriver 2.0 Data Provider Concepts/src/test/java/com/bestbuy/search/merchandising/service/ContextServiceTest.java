package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.ContextDAO;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.service.ContextService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ContextServiceTest {
	ContextDAO contextDAO;
	ContextService contextService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		contextService = new ContextService();
		contextDAO = mock(ContextDAO.class);
		contextService.setDao(contextDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		contextService = null;
		contextDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		Context Context = BaseData.getContextData();
		when(contextService.save(Context)).thenReturn(Context);
		Context=contextDAO.save(Context);
		assertNotNull(Context);
	}
}
