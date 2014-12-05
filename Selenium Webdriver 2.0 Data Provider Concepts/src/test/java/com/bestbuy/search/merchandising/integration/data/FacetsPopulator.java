/**
 * 
 */
package com.bestbuy.search.merchandising.integration.data;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.bestbuy.search.merchandising.service.BannerService;
import com.bestbuy.search.merchandising.service.FacetService;
import com.bestbuy.search.merchandising.unittest.common.UserTestData;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;

/**
 * @author Jebastin Prabaharan
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/it-applicationContext*.xml"})
@Transactional
public class FacetsPopulator {

  private static final long GLOBAL_SYNONYM_LIST = 1133827231862L;

  private static final int NO_OF_SYNONYMS = 10;
  private static final int NO_OF_TERMS_PER_SYNONYM = 2;

  
  private static final String[] pTermArr = {
	  "p1Term", "p2Term", "p3Term", "p4Term", "p5Term", 
	  "p6Term", "p7Term", "p8Term", "p9Term", "p10Term"
  };
  
  @Autowired
  FacetService facetService = null;
  
  UserUtil userUtil = null;

  /**
   * @throws java.lang.Exception
   */
//  @Before
  public void setUp() throws Exception {
	  UserUtil userUtil = mock(UserUtil.class);
	  Users user = UserTestData.getUser();
	  when(userUtil.getUser()).thenReturn(user);
	  facetService.setUserUtil(userUtil);
  }

//  @Test
//  @Rollback(false)
  public void populateFacets() {
    try {

    	for (int i = 0; i < NO_OF_SYNONYMS; i++) {
    		int index = i % pTermArr.length;
    		String pTerm = pTermArr[index];
    		String exactMatch = (i % 2 == 0) ? "Exact": "";
    		String direction = (i % 2 == 0) ? "One-way": "Bi-directional";

    		List<String> terms = new ArrayList<String>(NO_OF_TERMS_PER_SYNONYM);
    		for (int j = 0; j < NO_OF_TERMS_PER_SYNONYM; j++) {
        		terms.add(pTerm.concat(String.valueOf(j)));	
    		}
    		
    		createFacets(pTerm, exactMatch, direction, terms);
    	}
    	
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private void createFacets(String pTerm, String direction, String exactMatch, 
			List<String> terms) throws ServiceException, ParseException {
		
		FacetWrapper facetWrapper = new FacetWrapper();
//		facetWrapper.set
//		facetService.createFacet(facetWrapper);
		System.out.println(facetWrapper);
	}

}
