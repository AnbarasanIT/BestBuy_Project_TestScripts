package com.bestbuy.search.merchandising.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Facet;

/**
 * @author Kalaiselvi Jaganathan
 * DAO structure for facets
 * Modified By Chanchal.Kumari - Added loadFacets
 */
public interface IFacetDAO  extends IBaseDAO<Long,Facet>{
	/**
	 * Method to get display order ids for child paths
	 * 
	 * @param categoryPath
	 * @return List<Long>
	 */
	public List<Long> getDisplayOrderIdsForChildPaths(String categoryPath) throws DataAccessException, 
			ServiceException;
}
