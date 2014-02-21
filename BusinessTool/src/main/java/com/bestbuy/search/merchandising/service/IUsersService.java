/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import com.bestbuy.search.merchandising.domain.Users;

/**
 * @author Kalaiselvi.Jaganathan
 * Implementation of the UsersService
 */
public interface IUsersService extends IBaseService<String,Users>  {
	
	/**
	 * Gets the user that corresponds to the given login name
	 * @param loginName The login name for the requested user
	 * @return The Users object with the user information
	 * @author Ramiro.Serrato
	 */
	public Users loadUserByLoginName(String loginName);
}