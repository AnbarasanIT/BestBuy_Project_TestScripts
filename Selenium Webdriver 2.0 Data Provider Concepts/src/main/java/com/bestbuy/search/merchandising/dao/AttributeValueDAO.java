package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.AttributeValue;

/**
 * @author Kalaiselvi Jaganathan
 * DAO layer for AttributeValue
 */
public class AttributeValueDAO extends BaseDAO<Long,AttributeValue> implements IAttributeValueDAO{
	
	/**
	 * Load all the attribute values
	 * @param criteria
	 * @return List<AttributeValue>
	 * @throws DataAcessException
	 */
	public List<AttributeValue> loadAttributeValues(SearchCriteria criteria) throws DataAcessException{

		return	((List<AttributeValue>) this.executeQuery(criteria)) ;
	}
}
