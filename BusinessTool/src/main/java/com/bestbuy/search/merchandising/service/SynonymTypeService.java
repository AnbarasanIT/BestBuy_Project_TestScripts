/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.SynonymTypeDAO;
import com.bestbuy.search.merchandising.domain.SynonymType;

/**
 * @author Kalaiselvi.Jaganathan
 *
 */
public class SynonymTypeService extends BaseService<Long,SynonymType> implements ISynonymTypeService{
	@Autowired
	public void setDao(SynonymTypeDAO dao) {
		this.baseDAO = dao;
	}	
}

