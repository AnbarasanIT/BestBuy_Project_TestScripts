package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;

/**
 * @author Kalaiselvi Jaganathan
 * DAO layer for FacetAttributeValueOrder
 */
public class FacetAttributeValueOrderDAO extends BaseDAO<Long,FacetAttributeValueOrder> implements IFacetAttributeValueOrderDAO{
	
  private final static BTLogger log = BTLogger.getBTLogger(FacetAttributeValueOrderDAO.class.getName());

	/**
	 * Method to retrieve the facets for a category
	 * @throws ServiceException 
	 * @throws DataAcessException
	 */
	public List<FacetAttributeValueOrder> loadFacetValues(SearchCriteria criteria) throws ServiceException{

		List<FacetAttributeValueOrder> facetAttributeValueOrders = null;

		try{
			facetAttributeValueOrders = (List<FacetAttributeValueOrder>)this.executeQuery(criteria);
		}
		catch(DataAcessException da){
			log.error("FacetAttributeValueOrderDao", da, ErrorType.APPLICATION, "DAO error while retrieving the facet values from DB");
			throw new ServiceException(da);
		}
		return facetAttributeValueOrders;
	}
}
