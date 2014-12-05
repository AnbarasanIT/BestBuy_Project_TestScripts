/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.List;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * Defines the Promo Functionality Structure
 *@author chanchal kumari
 * Date 4th Oct 2012 
 */
public interface IPromoService extends IBaseService<Long, Promo>{
	
	public List<IWrapper> loadPromos(PaginationWrapper paginationWrapper) throws ServiceException;
	
	public IWrapper createPromo(PromoWrapper promoWrapper) throws ServiceException;
	
	/**
	 *performs the delete action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	public IWrapper deletePromo(Long id) throws ServiceException;
	
	/**
	 * performs the reject action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	public IWrapper rejectPromo(Long id) throws ServiceException;
	
	/**
	 * performs the approve action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	public IWrapper approvePromo(Long id) throws ServiceException;
	
	public PromoWrapper getPromo(Long id) throws ServiceException;
	
	/**
	 * Change the status of the Promo and update to the DB
	 * @param id The asset id for this Promo
	 * @param statusId current status of the promo
	 * @throws ServiceException If any error occurs
	 * @author Lakshmi.Valluripalli
	 */
	public IWrapper updatePromoStatus(Promo promo , String statusString) throws ServiceException;
	
	/**
	 * Change the status of the Promo to published
	 * @param id The promoId for this Promo
	 * @param statusName current status of the promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Lakshmi.Valluripalli
	 */
	public void publishPromo(Long id,String status) throws ServiceException;
	
	public IWrapper updatePromo(PromoWrapper promoWrapper) throws ServiceException;
	
	
	/**
	 * Updates the List of Promos
	 * @param promos
	 * @throws ServiceException
	 */
	public void updatePromos(List<Promo> promos) throws ServiceException;
	
}
