package com.bestbuy.search.merchandising.service;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Context;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public interface IContextService extends IBaseService<Long,Context>{
	
	public void updateContextStatus(String contextIds) throws ServiceException;

}
