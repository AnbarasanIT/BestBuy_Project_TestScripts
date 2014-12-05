/**
 * 
 */
package com.bestbuy.search.merchandising.unittest.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.DynamicQueryHelper;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.domain.Synonym;

/**
 * @author Lakshmi.Valluripalli
 * Junit class to verify weather the Queries are constructed Successfully
 *
 */
public class DynamicQueryHelperTest {
	
	DynamicQueryHelper queryHelper;
	SearchCriteria criteria;
	@Before
	public void setUp() {
		criteria = new SearchCriteria();
		queryHelper = new DynamicQueryHelper(Synonym.class);
	}
	
	/**
	 * Method to test create Query with all the Criteria
	 */
	@Test
	public void testCriteria(){
		Map<String, Object> searchTerms  = criteria.getSearchTerms();
		searchTerms.put("obj.asset", "xyz");
		Map<String, Object> greaterCriteria  = criteria.getGreaterCriteria();
		greaterCriteria.put("obj.asset.id", "121");
		Map<String,Object>	lesserCriteria = criteria.getLesserCriteria();
		lesserCriteria.put("obj.asset.startDate", new Date());
		Map<String,Object>	lesserEqCriteria = criteria.getLesserEqCriteria();
		lesserEqCriteria.put("obj.asset.endDate", new Date());
		Map<String,Object>	greaterEqCriteria = criteria.getGreaterEqCriteria();
		greaterEqCriteria.put("obj.asset.endDate", new Date());
		Map<String,Object> notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("obj.asset.publishDate", new Date());
		Map<String,Object>	inCriteria = criteria.getInCriteria();
		List<Long> statusIds = new ArrayList<Long>();
		statusIds.add(1l);
		statusIds.add(3l);
		inCriteria.put("obj.asset.status.statusId",statusIds);
		queryHelper.prepareQuery(criteria);
		String sql = queryHelper.getQueryString();
		assertNotNull(sql);
		assertTrue(sql.contains("WHERE") && sql.contains("IN") && sql.contains("<="));
	}
	
	/**
	 * Method to test create Query with out any criteria
	 */
	@Test
	public void testEmptyCriteria(){
		queryHelper.prepareQuery(criteria);
		String sql = queryHelper.getQueryString();
		assertNotNull(sql);
		assertTrue(!sql.contains("WHERE"));
	}
	
	/**
	 * Method to test create Query with null Criteria
	 */
	@Test
	public void testCriteriaNull(){
		criteria.setGreaterCriteria(null);
		criteria.setGreaterEqCriteria(null);
		criteria.setInCriteria(null);
		criteria.setLesserEqCriteria(null);
		criteria.setLesserCriteria(null);
		criteria.setNotEqCriteria(null);
		criteria.setSearchTerms(null);
		queryHelper.prepareQuery(criteria);
		String sql = queryHelper.getQueryString();
		assertNotNull(sql);
		assertTrue(!sql.contains("WHERE"));
	}
	
	/**
	 * Method to test create count query with criteria
	 */
	@Test
	public void testCriteriaWithCount(){
		queryHelper = new DynamicQueryHelper(Synonym.class,true);
		Map<String,Object> notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("obj.asset.publishDate", new Date());
		queryHelper.prepareQuery(criteria);
		String sql = queryHelper.getQueryString();
		assertNotNull(sql);
	}
}
