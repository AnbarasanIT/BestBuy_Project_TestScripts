package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.SearchProfileDAO;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.service.SearchProfileService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class SearchProfileServiceTest {
	private final static Logger logger = Logger.getLogger(SearchProfileServiceTest.class);

	SearchProfileDAO searchProfileDAO;
	SearchProfileService searchProfileService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		searchProfileService = new SearchProfileService();
		searchProfileDAO = mock(SearchProfileDAO.class);
		searchProfileService.setDao(searchProfileDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		searchProfileService = null;
		searchProfileDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 */
	@Test
	public void testSave() throws ServiceException{
			SearchProfile searchProfile = BaseData.getSearchProfile();
			when(searchProfileService.save(searchProfile)).thenReturn(searchProfile);
			searchProfile = searchProfileService.save(searchProfile);
			assertNotNull(searchProfile);
			assertFalse(searchProfile.getSearchProfileId()== 1l);
	}
}
