/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.SortOrder;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.UsersDAO;
import com.bestbuy.search.merchandising.domain.Role;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.UserWrapper;

/**
 * Service class for the Admin that calls the UserDAO to perform db operations.
 * 
 * @author Sreedhar Patlola
 */
@Service("adminService")
public class AdminService extends BaseService<Long, Users> implements HealthDiagnostics {

  @Autowired
  private UsersDAO usersDAO;

  @Autowired
  RoleService roleService;

  @Autowired
  private UserUtil userUtil;

  @Autowired
  public void setDao(UsersDAO dao) {
    this.baseDAO = dao;
  }

  /**
   * @param usersDAO
   *          the usersDAO to set
   */
  public void setUsersDAO(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  }

  /**
   * @param userUtil
   *          the userUtil to set
   */
  public void setUserUtil(UserUtil userUtil) {
    this.userUtil = userUtil;
  }

  /**
   * @param roleService
   *          the roleService to set
   */
  public void setRoleService(RoleService roleService) {
    this.roleService = roleService;
  }

  /**
   * Retrieves all users of the system.
   * 
   * @return the list of the UserWrapper objects
   * @throws ServiceException
   */
  public List<IWrapper> load() throws ServiceException {
    List<IWrapper> wrappers = null;
    SearchCriteria searchCriteria = new SearchCriteria();
    Map<String, SortOrder> orderCriteria = searchCriteria.getOrderCriteria();
    orderCriteria.put("firstName", SortOrder.ASC);

    try {
      List<Users> users = (List<Users>) usersDAO.executeQueryWithCriteria(searchCriteria);
      wrappers = UserWrapper.getUsers(users);
    }

    catch (DataAcessException da) {
      throw new ServiceException("Error while retriving the users from DB", da);
    }

    return wrappers;
  }

  /**
   * Retrieves the User by Id.
   * 
   * @param id
   * @return
   * @throws ServiceException
   * @throws DataAcessException
   */
  public UserWrapper getUser(Long id) throws ServiceException, DataAcessException {

    Users user = (Users) baseDAO.retrieveById(id);
    UserWrapper userWrapper = UserWrapper.getUser(user);
    return userWrapper;
  }

  /**
   * Creates the new User.
   * 
   * @param userWrapper
   * @return
   * @throws ServiceException
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public IWrapper createUser(UserWrapper userWrapper) throws ServiceException {
    Users user = new Users();
    Users loggedUser = userUtil.getUser();
    BeanUtils.copyProperties(userWrapper, user);
    user.setUpdatedDate(new Date());
    user.setUpdatedBy(loggedUser.getLastName() + ", " + loggedUser.getFirstName() + "(" + loggedUser.getLoginName() + ")");
    Role role = roleService.retrieveById(userWrapper.getRoleId());
    user.setRole(role);
    Users savedUser = save(user);
    return UserWrapper.getUser(savedUser);
  }

  /**
   * Deletes the User.
   * 
   * @param id
   * @return
   * @throws ServiceException
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public IWrapper deleteUser(Long id) throws ServiceException {
    try {
      Users user = this.retrieveById(id);
      user.setActive(false);
      Users deletedUser = this.update(user);
      return UserWrapper.getUser(deletedUser);
    }

    catch (ServiceException e) {
      throw new ServiceException("Error while performing the workflow step", e);
    }
  }

  /**
   * Updates the User.
   * 
   * @param userWrapper
   * @return
   * @throws ServiceException
   * @throws DataAcessException
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public IWrapper updateUser(UserWrapper userWrapper) throws ServiceException, DataAcessException {
    Users user = (Users) baseDAO.retrieveById(userWrapper.getUserId());
    Users loggedUser = userUtil.getUser();
    BeanUtils.copyProperties(userWrapper, user);
    user.setUpdatedDate(new Date());
    user.setUpdatedBy(loggedUser.getLastName() + ", " + loggedUser.getFirstName() + "(" + loggedUser.getLoginName() + ")");
    Role role = roleService.retrieveById(userWrapper.getRoleId());
    user.setRole(role);
    Users savedUser = update(user);
    return UserWrapper.getUser(savedUser);
  }

  /**
   * Method to check health check
   * 
   * @return boolean
   * @throws ServiceException
   */
  public boolean databaseHealthCheck() throws ServiceException {
	  boolean status = false;
	  try {
		  status = baseDAO.databaseHealthCheck();
	  } catch (DataAcessException e) {
		  throw new ServiceException(e);
	  }
	  return status;
  }
}
