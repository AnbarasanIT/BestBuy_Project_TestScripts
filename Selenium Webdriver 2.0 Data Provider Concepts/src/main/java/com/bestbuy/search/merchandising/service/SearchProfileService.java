/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.SearchProfileDAO;
import com.bestbuy.search.merchandising.domain.SearchProfile;

/**
 * @author chanchal kumari
 */

public class SearchProfileService extends BaseService<Long,SearchProfile>  implements ISearchProfileService{
	
	@Autowired
	public void setDao(SearchProfileDAO dao) {
		this.baseDAO = dao;
	}

}
