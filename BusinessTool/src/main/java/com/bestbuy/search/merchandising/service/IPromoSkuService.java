/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.PromoSku;

/**
 * @author Chanchal.Kumari
 */
public interface IPromoSkuService extends IBaseService<Long,PromoSku>  {
	
	public void deleteAllById(Long id) throws ServiceException ;

}
