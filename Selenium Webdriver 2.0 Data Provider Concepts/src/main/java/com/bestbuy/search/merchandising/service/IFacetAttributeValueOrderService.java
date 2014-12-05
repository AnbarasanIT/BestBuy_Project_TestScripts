package com.bestbuy.search.merchandising.service;

import java.util.List;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public interface IFacetAttributeValueOrderService extends IBaseService<Long,FacetAttributeValueOrder>{

	/**
	 * To retrieve the attribute values for a facet
	 * @param facetId
	 * @return List<IWrapper>
	 * @throws ServiceException
	 */
	public List<IWrapper> loadFacetValues(Long facetId) throws ServiceException;
}
