package com.bestbuy.search.merchandising.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.BannerWrapperConverter;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BannerDAO;
import com.bestbuy.search.merchandising.dao.BannerTemplateDAO;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.dao.ContextFacetDAO;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for banner service
 */
public class BannerServiceTest {
	BaseDAO<Long,Banner> baseDao;
	StatusService statusService;
	UsersService usersService;
	BannerService bannerService;
	BannerDAO bannerDAO;
	BannerWrapperConverter bannerWrapperConverter;
	ContextService contextService;
	ContextFacetDAO contextFacetDAO;
	BannerTemplateService bannerHTMLTemplateService;
	BannerTemplateDAO bannerHTMLTemplateDAO;
	Banner banner;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerService=new BannerService();
		bannerDAO=mock(BannerDAO.class);
		contextFacetDAO=mock(ContextFacetDAO.class);
		banner=mock(Banner.class);
		bannerHTMLTemplateDAO=mock(BannerTemplateDAO.class);
		statusService = mock(StatusService.class);
		usersService = mock(UsersService.class);
		bannerWrapperConverter=mock(BannerWrapperConverter.class);
		contextService=mock(ContextService.class);
		bannerHTMLTemplateService=mock(BannerTemplateService.class);
		bannerService.setDao(bannerDAO);
		bannerService.setBannerDAO(bannerDAO);
		bannerService.setStatusService(statusService);
		bannerService.setBannerWrapperConverter(bannerWrapperConverter);
		bannerService.setContextService(contextService);
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		statusService = null;
		bannerDAO=null; 
		bannerService=null;
		bannerWrapperConverter=null;
		bannerHTMLTemplateService = null;
	}
	
	/**
	 * Unit test case to test the Banner Creation Functionality
	 * @throws ParseException 
	 * @throws DataAcessException 
	 */
	@Ignore
	@Test
	public void testCreateBanner() throws ServiceException, ParseException, DataAcessException {
		
		BannerWrapper bannerWrapper = BaseData.getBannerWrapper();

		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);

		//Loading the Users for the retrieveById
		Users user = BaseData.getUsers();
		when(usersService.retrieveById("A922998")).thenReturn(user);

		
		//when(bannerWrapperConverter.prepareAssets(bannerWrapper)).thenReturn(assets);
		Context context=BaseData.getContextData();
		when(contextService.retrieveById(1L)).thenReturn(context);
		
		List<Context> contexts = BaseData.getContextList();
		when(bannerWrapperConverter.contextWrapperConverter(bannerWrapper,contexts)).thenReturn(contexts);
		
		Banner banner = BaseData.getBannerData();
		when(bannerWrapperConverter.wrapperConverter(bannerWrapper,null)).thenReturn(banner);
		
		Banner savedbanner = BaseData.getSavedBannerData();
		
		BannerTemplate htmlTemplate = BaseData.getBannerUrlList();
		when(bannerHTMLTemplateService.retrieveById(1L)).thenReturn(htmlTemplate);
		when(bannerHTMLTemplateDAO.save(htmlTemplate)).thenReturn(htmlTemplate);
		when(bannerService.save(banner)).thenReturn(savedbanner);
		bannerService.createBanner(bannerWrapper);		
	}
	
	@Test @Ignore
	public void testUpdateBanner() throws ServiceException, ParseException, DataAcessException {
		
		BannerWrapper bannerWrapper = BaseData.getBannerWrapper();

		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);

		//Loading the Users for the retrieveById
		Users user = BaseData.getUsers();
		when(usersService.retrieveById("A922998")).thenReturn(user);

	//when(bannerWrapperConverter.prepareAssets(bannerWrapper)).thenReturn(assets);
		Context context=BaseData.getContextData();
		when(contextService.retrieveById(1L)).thenReturn(context);
		
		List<Context> contexts = BaseData.getContextList();
		when(bannerWrapperConverter.contextWrapperConverter(bannerWrapper,contexts)).thenReturn(contexts);
		
		Banner banner = BaseData.getBannerData();
		when(bannerWrapperConverter.wrapperConverter(bannerWrapper,banner)).thenReturn(banner);
		Long bannerId=banner.getBannerId();
		
		Banner savedbanner = BaseData.getSavedBannerData();
		BannerTemplate htmlTemplate = BaseData.getBannerUrlList();
		List<BannerTemplate> htmlTemplates = new ArrayList<BannerTemplate>();
		htmlTemplates.add(htmlTemplate);
		when(bannerHTMLTemplateService.retrieveById(1L)).thenReturn(htmlTemplate);
		when(bannerHTMLTemplateDAO.save(htmlTemplate)).thenReturn(htmlTemplate);
		when(bannerService.update(banner)).thenReturn(savedbanner);
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	searchTerms = criteria.getSearchTerms();
		searchTerms.put("obj.bannerURL.bannerId",bannerId );
		when(bannerDAO.retrieveById(bannerId)).thenReturn(banner);
		bannerService.updateBanner(bannerWrapper);		
	}

}
