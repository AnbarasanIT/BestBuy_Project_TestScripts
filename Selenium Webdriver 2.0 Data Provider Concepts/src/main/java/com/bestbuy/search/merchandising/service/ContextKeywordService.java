package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.ContextKeywordDAO;
import com.bestbuy.search.merchandising.domain.ContextKeyword;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ContextKeywordService extends BaseService<Long,ContextKeyword> implements IContextKeywordService{
	
	@Autowired
	public void setDao(ContextKeywordDAO dao) {
		this.baseDAO = dao;
	}
}
