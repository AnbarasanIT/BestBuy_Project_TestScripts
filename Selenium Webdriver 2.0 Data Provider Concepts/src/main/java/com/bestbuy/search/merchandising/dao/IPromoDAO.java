
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.Promo;

/**
 * Interface that defines the structure of the Promo dao layer
 * @author Chanchal.Kumari
 * Date 5th Oct 2012
 */
public interface IPromoDAO extends IBaseDAO<Long,Promo>{
	
	public List<Promo> loadPromos(SearchCriteria criteria) throws DataAcessException;
	
	public void updatePromoStatus(Long id, Long statusId) throws DataAcessException;

}
