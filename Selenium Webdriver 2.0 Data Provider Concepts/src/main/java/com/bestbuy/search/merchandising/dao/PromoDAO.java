package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.Promo;

/**
 * CRUD operations for Promo
 * @author  Chanchal.Kumari 
 * Date 5th Oct 2012
 */
public class PromoDAO extends BaseDAO<Long,Promo> implements IPromoDAO{
	
	/**
	 * Method to load the promos from the promo entity
	 * @throws DataAcessException
	 */
	public List<Promo> loadPromos(SearchCriteria criteria) throws DataAcessException{
		List<Promo> promos = null;
		promos = (List<Promo>)this.executeQueryWithCriteria(criteria);
		
		return promos;
	}
	
	/**
	 * Method to update promo status
	 * @author chanchal.KUmari
	 * Date - 10th Oct 2012
	 */
	@Override
	public void updatePromoStatus(Long id, Long statusId) throws DataAcessException {
		String query = "UPDATE Asset a SET a.assetStatus = " + statusId + " WHERE assetsId = " + id;		
		executeUpdate(query);
	}
}
