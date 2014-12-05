/**
 * Oct 29, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.UsersService;

/**
 * @author Ramiro.Serrato
 *
 */
public class UserUtil {
	@Autowired
	private UsersService usersService;
	private IMerchSecurityContext securityContext;
	private Users user;	
	
	/**
	 * @param securityContext the securityContext to set
	 */
	public void setSecurityContext(IMerchSecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	/**
	 * @param usersService the usersService to set
	 */
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	} 

	public String getLoggedUserName(){  
	    Object principal = securityContext.getContext().getAuthentication().getPrincipal();
	    UserDetails userDetails = null;
	    
	    if (principal instanceof UserDetails) {
	      userDetails = (UserDetails) principal;
	    }
	    
	    String userName = userDetails.getUsername();
	    return userName;
	}
	
	public Long getLoggedUserId(){
		retrieveUserFromDB();
		return user.getUserId();
	}
	
	public Users getUser() {
		retrieveUserFromDB();
		return user;
	}
	
	private void retrieveUserFromDB(){
		if(user == null){
			user = usersService.loadUserByLoginName(getLoggedUserName());
		}
	}
}
