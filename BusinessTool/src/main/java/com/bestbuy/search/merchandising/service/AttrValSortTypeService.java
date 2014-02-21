package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.AttrValSortTypeDAO;
import com.bestbuy.search.merchandising.domain.AttrValSortType;

/**
 * @author Kalaiselvi Jaganathan
 * Implementation of the AttrValSortTypeService
 */
public class AttrValSortTypeService  extends BaseService<Long,AttrValSortType> implements IAttrValSortTypeService{
	@Autowired
	public void setDao(AttrValSortTypeDAO dao) {
		this.baseDAO = dao;
	}	

}
