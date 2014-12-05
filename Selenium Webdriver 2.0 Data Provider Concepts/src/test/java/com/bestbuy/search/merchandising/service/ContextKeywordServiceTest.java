package com.bestbuy.search.merchandising.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.ContextKeywordDAO;
import com.bestbuy.search.merchandising.domain.ContextKeyword;
import com.bestbuy.search.merchandising.service.ContextKeywordService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ContextKeywordServiceTest {


		ContextKeywordDAO contextKeywordDAO;
		ContextKeywordService contextKeywordService;

		/**
		 * Before test case to create the mocks
		 * Creating the Service Layer and its dependent Mocks
		 */
		@Before
		public void init() {
			contextKeywordService = new ContextKeywordService();
			contextKeywordDAO = mock(ContextKeywordDAO.class);
			contextKeywordService.setDao(contextKeywordDAO);

		}

		/**
		 * Clear all the Assigned Variables
		 */
		@After
		public void Destroy(){
			contextKeywordService = null;
			contextKeywordDAO = null;

		}

		/**
		 * TestCase for saveMethod
		 * @throws ServiceException 
		 * @throws DataAcessException 
		 */
		@Test
		public void testSave() throws ServiceException, DataAcessException{
			ContextKeyword ContextKeyword = BaseData.getContextKeywordData();
			when(contextKeywordService.save(ContextKeyword)).thenReturn(ContextKeyword);
			ContextKeyword=contextKeywordDAO.save(ContextKeyword);
			assertNotNull(ContextKeyword);
		}
	}
