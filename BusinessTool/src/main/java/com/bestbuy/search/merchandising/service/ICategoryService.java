
package com.bestbuy.search.merchandising.service;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.CategoryTree;

/**
 * Structure that defines the category operations
 * @author Lakshmi.Valluripalli
 */
public interface ICategoryService  extends IBaseService<Long,CategoryTree>{
	
	public static final String CACHE_NAME="categoryTree";
	
	/**
	 * Saves the received category tree to DAAS
	 * Version is incremented by taking the count of rows from DB
	 * @param - String categoryTree XML
	 */
	public void saveCategory(String categoryXml) throws ServiceException;
	
	/**
	 * Load the CategoryTree with highest version from DB
	 */
	public String loadCategory() throws ServiceException;

}
