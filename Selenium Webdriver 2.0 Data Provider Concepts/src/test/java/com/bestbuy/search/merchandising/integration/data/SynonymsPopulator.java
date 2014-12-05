/**
 * 
 */
package com.bestbuy.search.merchandising.integration.data;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
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
import com.bestbuy.search.merchandising.service.SynonymService;
import com.bestbuy.search.merchandising.unittest.common.UserTestData;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * @author Jebastin Prabaharan
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/it-applicationContext*.xml"})
@Transactional
public class SynonymsPopulator extends BasePopulator {
  
  private static final String[] pTermArr = {
	  "p1Term", "p2Term", "p3Term", "p4Term", "p5Term", 
	  "p6Term", "p7Term", "p8Term", "p9Term", "p10Term"
  };
  
  @Autowired
  SynonymService synonymService = null;
  
  @Autowired
  BasicDataSource bizdataSource = null;
  
  /**
   * @throws java.lang.Exception
   */
//  @Before
  public void setUp() throws Exception {
	  System.out.println("Heloooo");
	  System.out.println("bizdataSource.getConnection() " + bizdataSource.getConnection());
	  System.out.println(bizdataSource);
	  
	  UserUtil userUtil = mock(UserUtil.class);
	  Users user = UserTestData.getUser();
	  when(userUtil.getUser()).thenReturn(user);
	  synonymService.setUserUtil(userUtil);
	  
  }

//  @Test
//  @Rollback(false)
  public void populateSynonyms() {
    try {

    	for (int i = 0; i < NO_OF_SYNONYMS; i++) {
    		int index = i % pTermArr.length;
    		String pTerm = pTermArr[index];
    		
    		String exactMatch = (i % 2 == 0) ? "Exact": "Broad";
    		String direction = (i % 2 == 0) ? "One-way": "Two-way";

    		List<String> terms = new ArrayList<String>(NO_OF_TERMS_PER_SYNONYM);
    		for (int j = 0; j < NO_OF_TERMS_PER_SYNONYM; j++) {
        		terms.add(pTerm.concat(String.valueOf(j)));	
    		}
    		
			createSynonym(pTerm, direction, exactMatch, terms);
			System.out.println("i " + i);
			System.out.println("NO_OF_SYNONYMS " + NO_OF_SYNONYMS);
    	}
    	
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	private void createSynonym(String pTerm, String direction, String exactMatch, List<String> terms) throws ServiceException {
		SynonymWrapper synonymWrapper = new SynonymWrapper();
		
		synonymWrapper.setPrimaryTerm(pTerm);
		synonymWrapper.setDirectionality(direction);
		synonymWrapper.setExactMatch(exactMatch);
		synonymWrapper.setListId(GLOBAL_SYNONYM_LIST);
		synonymWrapper.setTerm(terms);
		
		System.out.println("pTerm " + pTerm);
		System.out.println("direction " + direction);
		System.out.println("exactMatch " + exactMatch);
		System.out.println("terms " + terms);
		
		IWrapper syn = synonymService.createSynonym(synonymWrapper);
		System.out.println(synonymWrapper);
	}

}
