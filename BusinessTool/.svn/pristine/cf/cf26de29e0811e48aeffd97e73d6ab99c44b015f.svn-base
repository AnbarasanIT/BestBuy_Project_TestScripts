package com.bestbuy.search.merchandising.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.AttributeValueDAO;
import com.bestbuy.search.merchandising.dao.IAttributeValueDAO;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Service for Attribute values
 */
public class AttributeValueService extends BaseService<Long,AttributeValue> implements IAttributeValueService{

	@Autowired
	public void setDao(AttributeValueDAO dao) {
		this.baseDAO = dao;
	}

	@Autowired
	private IAttributeValueDAO attributeValueDAO;

	/**
	 * @param to set the attributeValueDAO 
	 */
	public void setAttributeValueDAO(IAttributeValueDAO attributeValueDAO) {
		this.attributeValueDAO = attributeValueDAO;
	}

	/**
	 * Load the attribute value for a facet
	 * @param criteria
	 * @return
	 * @throws DataAcessException
	 */
	public List<AttributeValue> loadAttributeValues(SearchCriteria criteria) throws DataAcessException{
		List<AttributeValue> attributeValue = attributeValueDAO.loadAttributeValues(criteria);
		return attributeValue;
	}

	/**
	 * Load the attribute value for an attribute (with Pagination)
	 */
	public  List<IWrapper> loadAttrValue(PaginationWrapper paginationWrapper) throws ServiceException{

		List<IWrapper> attributeValueWrappers = null;
		SearchCriteria criteria = new SearchCriteria();
		criteria.setPaginationWrapper(paginationWrapper);
		Map<String, Object> searchTerms = criteria.getSearchTerms();
		Map<String, Object> notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("isActive", "N");
		searchTerms.put(getUiToEntityMapper().get((paginationWrapper.getSearchColumnValues().get(0).getKey())), paginationWrapper.getSearchColumnValues().get(0).getValue());
		if(paginationWrapper.getSearchColumnValues().get(1).getValue() != ""){
			List<KeyValueWrapper> andCriteriaColumnValues = criteria.getAndCriteriaColumnValues();
			andCriteriaColumnValues.add(new KeyValueWrapper(getUiToEntityMapper().get((paginationWrapper.getSearchColumnValues().get(1)).getKey()), paginationWrapper.getSearchColumnValues().get(1).getValue()));
		}
		try{ 
			List<AttributeValue> attributeValues = this.loadEntitiesWithCriteria(criteria);
			attributeValueWrappers = AttributeValueWrapper.getAttributeValueWrapper(attributeValues);
		}
		catch(DataAcessException dae){
			throw new ServiceException("Error while retriving the attribute values from DB", dae);
		}
		return attributeValueWrappers;
	}
}
