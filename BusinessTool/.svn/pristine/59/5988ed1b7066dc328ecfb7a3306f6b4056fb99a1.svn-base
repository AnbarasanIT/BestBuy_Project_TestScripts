/**
 * 
 */
package com.bestbuy.search.merchandising.integration.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.BannerService;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextWrapper;

/**
 * @author Jebastin Prabaharan
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/it-applicationContext*.xml"})
@Transactional
public class BannerPopulator extends BasePopulator {

	public static final String[] contextPathArr = {"pcmcat193400050017", "pcmcat193400050017"};

	private static final String[] bannerTemplateArr = { "HTML_ONLY","1HEADER_3SKU", "3HEADER_3SKU"};
  
  @Autowired
  BannerService bannerService = null;
  
  /**
   * @throws java.lang.Exception
   */
//  @Before
  public void setUp() throws Exception {
//	  UserUtil userUtil = mock(UserUtil.class);
//	  Users user = UserTestData.getUser();
//	  when(userUtil.getUser()).thenReturn(user);
//	  bannerService.setUserUtil(userUtil);
  }

//  @Test
//  @Rollback(false)
  public void populateBanners() {
    try {

    	for (int i = 0; i < NO_OF_BANNERS; i++) {
    		Long searchProfileId = 1L;
    		Long inherit = 1L;
    		
    		String bannerName = "banner-" + i;
			Long priority = (i % 5) + 1L;
			String bannerType = "1";
			Calendar instance = GregorianCalendar.getInstance();
			Date startDate = instance.getTime();
			instance.set(Calendar.DAY_OF_MONTH, startDate.getDay() + 7);
			Date endDate = instance.getTime();
			
			Long documentId = 123456L;
			int index = i % bannerTemplateArr.length;
    		String bannerTemplate = bannerTemplateArr[index];
    		
    		index = i % contextPathArr.length;
    		String contextPathId = contextPathArr[index];

    		createBanner(bannerName, priority, bannerType, startDate, endDate,
    				documentId, bannerTemplate, contextPathId, searchProfileId, inherit);
    	}
    	
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private void createBanner(String bannerName, Long priority, String bannerType, 
			Date startDate, Date endDate, Long documentId, String bannerTemplate, 
			String contextPathId, Long searchProfileId, Long inherit) throws ServiceException {
		
		BannerWrapper bannerWrapper = new BannerWrapper();
		bannerWrapper.setBannerName(bannerName);
		bannerWrapper.setBannerPriority(priority);
		bannerWrapper.setBannerType(bannerType);
		bannerWrapper.setStartDate(startDate);
		bannerWrapper.setEndDate(endDate);
		bannerWrapper.setDocumentId(documentId);
		bannerWrapper.setBannerTemplate(bannerTemplate);
		
		//TODO:Needs to be set in case of 1Header3SKU and 3Header3SKUs
//		bannerWrapper.setTemplates(templates);
		
		List<ContextWrapper> contexts = new ArrayList<ContextWrapper>();
		String keywords = "ctx1kw1,ctx1kw2";
		
		ContextWrapper context = createContext(contextPathId, keywords, searchProfileId, inherit);
		contexts.add(context);
		bannerWrapper.setContexts(contexts);
		
		bannerService.createBanner(bannerWrapper);
		
		System.out.println(bannerWrapper);
	}

	private ContextWrapper createContext(String contextPathId, String keywords, Long searchProfileId, Long inherit) {
		ContextWrapper context = new ContextWrapper();
		context.setContextPathId(contextPathId);
		context.setKeywords(keywords);
		context.setSearchProfileId(searchProfileId);
		context.setInherit(inherit);
		return context;
	}

}