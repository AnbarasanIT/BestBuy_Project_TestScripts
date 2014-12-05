/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Status;

/**
 * defines CRUD Structure of the Status Service
 * @author Lakshmi Valluripalli
 */
public interface IStatusService extends IBaseService<Long,Status>{
	
	/**
	 * Change the status to a synoynym
	 * @param id The Asset id of the synonym to be updateded
	 * @param status The status name for the new status to be set
	 * @author Ramiro.Serrato
	 * @throws ServiceException
	 */	
	public Long getStatusId(String name) throws ServiceException;
}
