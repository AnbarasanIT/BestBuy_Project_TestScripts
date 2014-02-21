/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;
import java.util.Map;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.Users;

/**
 * @author Kalaiselvi.Jaganathan
 * CRUD operations for UsersDAO
 */
public class UsersDAO extends BaseDAO<String,Users> implements IUsersDAO{
	public List<Users> findByLoginName(String loginName) throws DataAcessException {
		List<Users> result;
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	searchTerm = criteria.getSearchTermsIgnoreCase();
		searchTerm.put("obj.loginName", loginName.toUpperCase());
		result = (List<Users>) executeQuery(criteria);				
		return result;
	}
}
