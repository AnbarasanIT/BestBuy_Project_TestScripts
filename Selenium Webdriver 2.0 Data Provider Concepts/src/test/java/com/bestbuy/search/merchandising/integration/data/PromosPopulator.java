/**
 * 
 */
package com.bestbuy.search.merchandising.integration.data;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.service.PromoService;
import com.bestbuy.search.merchandising.unittest.common.UserTestData;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * @author Jebastin Prabaharan
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/it-applicationContext*.xml"})
@Transactional
public class PromosPopulator extends BasePopulator {

  @Autowired
  PromoService promoService = null;
  
  UserUtil userUtil = null;

  /**
   * @throws java.lang.Exception
   */
//  @Before
  public void setUp() throws Exception {
	  UserUtil userUtil = mock(UserUtil.class);
	  Users user = UserTestData.getUser();
	  when(userUtil.getUser()).thenReturn(user);
	  promoService.setUserUtil(userUtil);
  }

//  @Test
//  @Rollback(false)
  public void populatePromos() {
    try {

    	for (int i = 0; i < NO_OF_PROMOS; i++) {
			Calendar instance = GregorianCalendar.getInstance();
			Date startDate = instance.getTime();
			instance.set(Calendar.DAY_OF_MONTH, startDate.getDay() + 7);
			Date endDate = instance.getTime();
			
			String promoName = "redirect-"+ i;
    		List<String> skuIds = new ArrayList<String>(2);
    		skuIds.add("123456");
			createPromos(promoName, skuIds, startDate, endDate);
    	}
    	
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private void createPromos(String promoName, List<String> skuIds, Date startDate, Date endDate) throws ServiceException {
		PromoWrapper promoWrapper = new PromoWrapper();
		promoWrapper.setPromoName(promoName);
		promoWrapper.setSkuIds(skuIds);
		promoWrapper.setStartDate(startDate);
		promoWrapper.setEndDate(endDate);
		promoService.createPromo(promoWrapper);
		System.out.println(promoWrapper);
	}

}
