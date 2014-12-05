
package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.PromoSkuDAO;
import com.bestbuy.search.merchandising.domain.PromoSku;

/**
 * @author Chanchal.Kumari
 *
 */
public class PromoSkuService extends BaseService<Long,PromoSku>  implements IPromoSkuService{
	
	@Autowired
	public void setDao(PromoSkuDAO dao) {
		this.baseDAO = dao;
	}
	
	/**@author chanchal kumari
	 * Method to deletes all the Sku ids for input promo id
	 * @param primaryKeyId
	 * @param entity
	 * @return E entity
	 * @throws ServiceException
	 */
	public void deleteAllById(Long id) throws ServiceException {
		try {
			baseDAO.deleteAllById(id);
		} catch (DataAcessException dae) {
			throw new ServiceException(dae);
		}
	}
	
}
