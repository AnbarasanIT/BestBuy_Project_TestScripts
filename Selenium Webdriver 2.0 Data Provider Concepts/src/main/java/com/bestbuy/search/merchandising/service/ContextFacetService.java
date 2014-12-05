package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.ContextFacetDAO;
import com.bestbuy.search.merchandising.domain.ContextFacet;

/**
 * @author Kalaiselvi Jaganathan
 * Service for Context Facet
 */
public class ContextFacetService extends BaseService<Long,ContextFacet> implements IContextFacetService{
	
	@Autowired
	public void setDao(ContextFacetDAO dao) {
		this.baseDAO = dao;
	}

}
