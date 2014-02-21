package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BannerTemplateMetaDAO;
import com.bestbuy.search.merchandising.domain.BannerTemplateMeta;
import com.bestbuy.search.merchandising.service.BannerTemplateMetaService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganthan
 * Unit testcase for Banner Template service
 */
public class BannerTemplateServiceTest {
	private final static Logger logger = Logger.getLogger(BannerTemplateServiceTest.class);

	BannerTemplateMetaDAO bannerTemplateDAO;
	BannerTemplateMetaService bannerTemplateService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerTemplateService = new BannerTemplateMetaService();
		bannerTemplateDAO = mock(BannerTemplateMetaDAO.class);
		bannerTemplateService.setDao(bannerTemplateDAO);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		bannerTemplateService = null;
		bannerTemplateDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		BannerTemplateMeta template = BaseData.getBannerTemplateData();
		when(bannerTemplateService.save(template)).thenReturn(template);
		template=bannerTemplateDAO.save(template);
		assertNotNull(template);
		assertTrue(template.getBannerTemplateId() == 1l);
	}
}
