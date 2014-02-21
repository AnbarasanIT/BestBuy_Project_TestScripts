/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.search.merchandising.dao.RoleDAO;
import com.bestbuy.search.merchandising.domain.Role;

/**
 * @author Sreedhar Patlola
 */
@Service("roleService")
public class RoleService extends BaseService<Integer, Role> {

  @Autowired
  RoleDAO roleDAO;

  @Autowired
  public void setDao(RoleDAO dao) {
    this.baseDAO = dao;
  }

}
