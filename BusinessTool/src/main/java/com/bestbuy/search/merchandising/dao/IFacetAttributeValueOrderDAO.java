package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;

/**
 * @author Kalaiselvi Jaganathan
 * Structure for FacetAttributeValueOrderDAO
 */
public interface IFacetAttributeValueOrderDAO extends IBaseDAO<Long,FacetAttributeValueOrder>{
	
	/**
	 * To retreive the attribute values for a facet
	 * @param facetId
	 * @return List<FacetAttributeValueOrder>
	 */
	public List<FacetAttributeValueOrder> loadFacetValues(SearchCriteria criteria) throws ServiceException;

}
