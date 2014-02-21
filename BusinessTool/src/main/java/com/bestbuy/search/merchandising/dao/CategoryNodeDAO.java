package com.bestbuy.search.merchandising.dao;

import java.util.List;
import java.util.Map;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.Category;

/**
 * Dao layer for categoryTree
 * @author Lakshmi.Valluripalli
 */
public class CategoryNodeDAO extends BaseDAO<String,Category> implements ICategoryNodeDAO{
	/**
	 * This method retrives categories from Categories table provided a list of categoryPath in inClause.
	 * @author Chanchal.Kumari
	 * @param  categoryPaths
	 * @return List<Category>
	 * @throws DataAcessException
	 */
	public List<Category> retrieveByPath(List<String> categoryPaths) throws DataAcessException {
		
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object> inCriteria = criteria.getInCriteria();
		inCriteria.put("categoryPath", categoryPaths);
		List<Category> categories = (List<Category>)this.executeQuery(criteria);
		return categories;
	   
	  }
	
}
