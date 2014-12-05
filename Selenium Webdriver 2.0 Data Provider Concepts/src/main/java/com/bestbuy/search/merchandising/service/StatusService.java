/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.StatusDAO;
import com.bestbuy.search.merchandising.domain.Status;

/**
 * @author a1003132
 *
 */
public class StatusService  extends BaseService<Long,Status> implements IStatusService{
	
	@Autowired
	public void setDao(StatusDAO dao) {
		this.baseDAO = dao;
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.service.IStatusService#getStatusId(java.lang.String)
	 */
	@Override
	public Long getStatusId(String name) throws ServiceException {
		try {		
			return ((StatusDAO) baseDAO).getStatusId(name);
		} 
		
		catch (DataAcessException e) {
			throw new ServiceException("The service was not able to retrieve the id for the status", e);
		}
	}	
}
