package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.ContextDAO;
import com.bestbuy.search.merchandising.domain.Context;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ContextService extends BaseService<Long,Context> implements IContextService{
	
	@Autowired
	public void setDao(ContextDAO dao) {
		this.baseDAO = dao;
	}
	
	public void updateContextStatus(String contextIds) throws ServiceException{
		try {
			baseDAO.updateContext(contextIds);
		} catch (DataAcessException dae) {
			throw new ServiceException(dae);
		}
	}
}
