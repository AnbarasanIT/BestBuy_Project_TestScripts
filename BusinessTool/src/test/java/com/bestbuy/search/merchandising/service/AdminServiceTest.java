package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.dao.UsersDAO;
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.unittest.common.UserTestData;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.UserWrapper;

/**
 * UnitTest case for AdminService.
 * 
 * @author Sreedhar Patlola
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class AdminServiceTest {

  AdminService adminService;
  UsersDAO userDAO;
  BaseDAO<Long, Users> baseDAO;
  RoleService roleService;
  UserUtil userUtil;

  @Before
  public void setUp() throws Exception {

    adminService = new AdminService();
    userDAO = mock(UsersDAO.class);
    baseDAO = mock(BaseDAO.class);
    adminService.setUsersDAO(userDAO);
    adminService.setBaseDAO(baseDAO);
    roleService = mock(RoleService.class);
    userUtil = mock(UserUtil.class);
    adminService.setUserUtil(userUtil);
    adminService.setRoleService(roleService);

  }
  
  /**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		userUtil = null;
		userDAO=null; 
		adminService=null;
		baseDAO=null;
		roleService = null;
	}

  @Test
  public void testLoad() throws Exception {
    Collection<Users> expectedResultList = UserTestData.getUsers();
    when(userDAO.executeQueryWithCriteria((Mockito.any(SearchCriteria.class)))).thenReturn(expectedResultList);
    adminService.load();
  }

  @Test (expected = ServiceException.class)
  @SuppressWarnings("unchecked")
  public void testLoadForAnException() throws Exception {
    when(userDAO.executeQueryWithCriteria((Mockito.any(SearchCriteria.class)))).thenThrow(DataAcessException.class);
    adminService.load();
  }

  @Test
  public void testGetUser() throws Exception {
    Users user = UserTestData.getUser();
    when(baseDAO.retrieveById(1l)).thenReturn(user);
    adminService.getUser(1l);

  }

  @Test
  public void testCreateUser() throws Exception {
    UserWrapper userWrapper = UserTestData.getUserWrapper();
    Users user = UserTestData.getUser();
    Role role = new Role();
    when(roleService.retrieveById(1)).thenReturn(role);
    when(userUtil.getUser()).thenReturn(user);
    when(adminService.save(Mockito.any(Users.class))).thenReturn(user);

    IWrapper iWrapper = adminService.createUser(userWrapper);
    assertNotNull(iWrapper);
  }


  @Test
  public void testUpdateUser() throws Exception {
    UserWrapper userWrapper = UserTestData.getUserWrapper();
    Users user = UserTestData.getUser();
    Role role = new Role();
    when(baseDAO.retrieveById(10205l)).thenReturn(user);
    when(roleService.retrieveById(1)).thenReturn(role);
    when(userUtil.getUser()).thenReturn(user);
    when(adminService.update(Mockito.any(Users.class))).thenReturn(user);

    IWrapper iWrapper = adminService.updateUser(userWrapper);
    assertNotNull(iWrapper);
  }
  
  @Test
  public void testDeleteUser() throws Exception {
    UserWrapper userWrapper = UserTestData.getUserWrapper();
    Users user = UserTestData.getUser();
    when(baseDAO.retrieveById(10205l)).thenReturn(user);
    when(adminService.update(user)).thenReturn(user);

    IWrapper iWrapper = adminService.deleteUser(10205l);
    assertNotNull(iWrapper);
  }

}
