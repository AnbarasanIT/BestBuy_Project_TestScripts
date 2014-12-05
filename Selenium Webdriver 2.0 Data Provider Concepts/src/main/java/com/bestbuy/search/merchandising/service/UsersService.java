/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.UsersDAO;
import com.bestbuy.search.merchandising.domain.Users;

/**
 * @author Kalaiselvi.Jaganathan
 *
 */
public class UsersService extends BaseService<String,Users> implements IUsersService, UserDetailsService, UserDetailsContextMapper {

	@Autowired
	public void setDao(UsersDAO dao) {
		this.baseDAO = dao;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserDetails securityUser;
		Users user = loadUserByLoginName(userName);// ((UsersDAO) baseDAO).findByLoginName(userName);
		securityUser = buildSecurityUserFromEntity(user);
		return securityUser; 
	}
	
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.service.IUsersService#loadUserByLoginName(java.lang.String)
	 */
	public Users loadUserByLoginName(String loginName) {
		List<Users> users = null;
		
		try {
			users = ((UsersDAO) baseDAO).findByLoginName(loginName);
		} 
		
		catch (DataAcessException e) {
			throw new UsernameNotFoundException("Could not find user");
		}	
		
		if(users == null){
			throw new UsernameNotFoundException("Query returned null object for the loginName:"+loginName);
		}
		
		if(users != null && users.isEmpty()){
			throw new UsernameNotFoundException("Did not find a user with the login name:"+loginName);
		}
		
		if(users != null && users.size() > 1){
			throw new UsernameNotFoundException("More than one user with the name:"+loginName);
		}	
		
		if(users != null && !users.get(0).getActive()){
      throw new UsernameNotFoundException("User is  made Inactive:"+loginName);
    }
		
		return users.get(0);
	}
	
	private User buildSecurityUserFromEntity(Users user) {
	    String username = user.getLoginName();
	   
	    String password = user.getPassword() == null ? "SECURED" : user.getPassword();
	    Boolean enabled = user.getActive();
	    Boolean accountNonExpired = user.getActive();
	    Boolean credentialsNonExpired = user.getActive();
	    Boolean accountNonLocked = user.getActive();

	    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    authorities.add(new GrantedAuthorityImpl(user.getRole().getName()));
	    User securityUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    
	    return securityUser;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ldap.userdetails.UserDetailsContextMapper#mapUserFromContext(org.springframework.ldap.core.DirContextOperations, java.lang.String, java.util.Collection)
	 */
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<GrantedAuthority> authority) {
		UserDetails securityUser;
		Users user = loadUserByLoginName(username);// ((UsersDAO) baseDAO).findByLoginName(userName);
		securityUser = buildSecurityUserFromEntity(user);
		return securityUser; 
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ldap.userdetails.UserDetailsContextMapper#mapUserToContext(org.springframework.security.core.userdetails.UserDetails, org.springframework.ldap.core.DirContextAdapter)
	 */
	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		// TODO Auto-generated method stub
		
	}
}
	
