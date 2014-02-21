package com.bestbuy.search.merchandising.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.FacetAttributeValueOrderDAO;
import com.bestbuy.search.merchandising.dao.IFacetAttributeValueOrderDAO;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Service layer for FacetAttributeValueOrder
 */
public class FacetAttributeValueOrderService extends BaseService<Long,FacetAttributeValueOrder> implements IFacetAttributeValueOrderService{

	@Autowired
	public void setDao(FacetAttributeValueOrderDAO dao) {
		this.baseDAO = dao;
	}

	@Autowired
	private IFacetAttributeValueOrderDAO facetAttributeValueOrderDAO;

	/**
	 * @param to set the facetAttributeValueOrderDAO 
	 */
	public void setFacetAttributeValueOrderDAO(
			IFacetAttributeValueOrderDAO facetAttributeValueOrderDAO) {
		this.facetAttributeValueOrderDAO = facetAttributeValueOrderDAO;
	}

	/**
	 * Retrieves all the attribute values for a facet
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public List<IWrapper> loadFacetValues(Long facetId) throws ServiceException {
		List<IWrapper> attributeValueWrappers = null;
		List<Long> facetIds = new ArrayList<Long>();
		//Set the search criteria
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	inCriteria = criteria.getInCriteria();
		facetIds.add(facetId);
		inCriteria.put("obj.facetAttributeValueOrderPK.facet.facetId", facetIds);
		//Retrieve all facets for a category
		List<FacetAttributeValueOrder> facetAttributeValueOrders = facetAttributeValueOrderDAO.loadFacetValues(criteria);
		//Map the entity to wrapper
		attributeValueWrappers = AttributeValueWrapper.getFacetAttrValues(facetAttributeValueOrders);
		return attributeValueWrappers;		
	}
}
