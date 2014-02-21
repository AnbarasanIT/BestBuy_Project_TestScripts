package com.bestbuy.search.merchandising.service;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public interface IAttributeValueService extends IBaseService<Long, AttributeValue>{
	
	/**
	 * Load the attribute value for a facet
	 * @param criteria
	 * @return List<AttributeValue>
	 * @throws DataAcessException
	 */
	public List<AttributeValue> loadAttributeValues(SearchCriteria criteria) throws DataAcessException;
	
	/**
	 * Load the attribute value for an attribute 
	 * @param paginationWrapper
	 * @return List<AttributeValueWrapper>
	 * @throws ServiceException
	 */
	public  List<IWrapper> loadAttrValue(PaginationWrapper paginationWrapper) throws ServiceException;

}
