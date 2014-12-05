/**
 * 
 */
package com.bestbuy.search.merchandising.integration.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.service.KeywordRedirectService;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;

/**
 * @author Jebastin Prabaharan
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/it-applicationContext*.xml"})
@Transactional
public class KeywordsPopulator extends BasePopulator {

  private static final String REDIRECT_URL = "URL";
  private static final Long SEARCH_PROFILE_ID = 1L;
  
  @Autowired
  KeywordRedirectService keywordService = null;
  
  UserUtil userUtil = null;

  /**
   * @throws java.lang.Exception
   */
//  @Before
  public void setUp() throws Exception {
//	  UserUtil userUtil = mock(UserUtil.class);
//	  Users user = UserTestData.getUser();
//	  when(userUtil.getUser()).thenReturn(user);
//	  keywordService.setUserUtil(userUtil);
  }

//  @Test
//  @Rollback(false)
  public void populateSynonyms() {
    try {
    	for (int i = 0; i < KEYWORD_REDIRECTS; i++) {
			Calendar instance = GregorianCalendar.getInstance();
			Date startDate = instance.getTime();
			instance.set(Calendar.DAY_OF_MONTH, startDate.getDay() + 7);
			Date endDate = instance.getTime();
			
			String redirectTerm = "redirect-"+ i;
			String redirectUrl = "redirect-"+ i + "url";
			createKeywords(redirectTerm, REDIRECT_URL, redirectUrl, SEARCH_PROFILE_ID, startDate, endDate);
    	}
    	
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private void createKeywords(String redirectTerm, String redirectType, String redirectUrl, Long searchProfileId, Date startDate, Date endDate) throws ServiceException {
		KeywordRedirectWrapper keywordRedirectWrapper = new KeywordRedirectWrapper();
		keywordRedirectWrapper.setRedirectTerm(redirectTerm);
		keywordRedirectWrapper.setRedirectType(redirectType);
		keywordRedirectWrapper.setRedirectUrl(redirectUrl);
		keywordRedirectWrapper.setSearchProfileId(searchProfileId);
		keywordRedirectWrapper.setStartDate(startDate);
		keywordRedirectWrapper.setEndDate(endDate);
		
		keywordService.createKeywordRedirect(keywordRedirectWrapper);
		System.out.println(keywordRedirectWrapper);
	}

}
