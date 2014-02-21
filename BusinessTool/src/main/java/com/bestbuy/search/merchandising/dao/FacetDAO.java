package com.bestbuy.search.merchandising.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Facet;

/**
 * @author Kalaiselvi Jaganathan
 * DAO layer for Facets
 * Modified By Chanchal.Kumari - Added loadFacets
 */
public class FacetDAO extends BaseDAO<Long,Facet> implements IFacetDAO{
	/**
	 * Method to get display order ids for child paths
	 * 
	 * @param categoryPath
	 * @return List<Long>
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getDisplayOrderIdsForChildPaths(String categoryPath) throws DataAccessException, 
			ServiceException {
		List<Long> facetDisplayOrderIds = null;
		Query query = null;
		String jpqlQuery = "select obj.id from FacetsDisplayOrder obj where obj.category.categoryNodeId in " +
				"(select distinct c.categoryNodeId from Category c where c.categoryPath like ('"+categoryPath+"|%'))";
		query = entityManager.createQuery(jpqlQuery);
		facetDisplayOrderIds = (List<Long>) query.getResultList();
		return facetDisplayOrderIds;
	}
}
