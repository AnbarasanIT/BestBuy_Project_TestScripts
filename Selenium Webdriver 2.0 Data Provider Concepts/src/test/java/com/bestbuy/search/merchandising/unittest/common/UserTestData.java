/**
 * 
 */
package com.bestbuy.search.merchandising.unittest.common;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.UserWrapper;

/**
 * Test Data class for Users Test
 * 
 * @author Sreedhar Patlola
 */
public class UserTestData {

  public static List<IWrapper> getUserWrappers() {
    List<IWrapper> userWrappers = new ArrayList<IWrapper>();
    UserWrapper userWrapper = new UserWrapper();
    userWrapper.setLoginName("testLogin");
    userWrappers.add(userWrapper);
    return userWrappers;
  }

  public static List<Users> getUsers() {
    List<Users> users = new ArrayList<Users>();
    Users user = new Users();
    user.setLoginName("testLogin");
    users.add(user);
    return users;
  }

  public static UserWrapper getUserWrapper() {

    UserWrapper userWrapper = new UserWrapper();
    userWrapper.setUserId(10205l);
    userWrapper.setLoginName("a1010742");
    userWrapper.setFirstName("test");
    userWrapper.setLastName("user");
    userWrapper.setRoleId(1);
    userWrapper.setEmail("test.user@bestbuy.com");
    userWrapper.setRoleName("ROLE_ADMIN");

    return userWrapper;
  }
  
  public static Users getUser() {

    Users user = new Users();
    user.setUserId(10205l);
    user.setLoginName("a1010742");
    user.setFirstName("test");
    user.setLastName("user");
    user.setRole(new Role());
    user.getRole().setId(1);
    user.getRole().setName("ROLE_ADMIN");
    user.getRole().setDescription("Administrative");
    user.setEmail("test.user@bestbuy.com");
    return user;
  }

}
