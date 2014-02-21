package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BannerTemplateDAO;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.service.BannerTemplateService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for BannerURLListService class
 */
public class BannerURLListServiceTest {
	BannerTemplateDAO bannerURLListDAO;
	BannerTemplateService bannerURLListService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerURLListService = new BannerTemplateService();
		bannerURLListDAO = mock(BannerTemplateDAO.class);
		bannerURLListService.setDao(bannerURLListDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		bannerURLListService = null;
		bannerURLListDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		BannerTemplate URLList = BaseData.getBannerUrlList();
		when(bannerURLListService.save(URLList)).thenReturn(URLList);
		URLList=bannerURLListDAO.save(URLList);
		assertNotNull(URLList);
	}
}
