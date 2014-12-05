package com.bestbuy.search.merchandising.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.dao.BoostAndBlockProductDao;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class BoostAndBlockProductService extends BaseService<Long,BoostAndBlockProduct> implements IBoostAndBlockProductService{
	
	@Autowired
	public void setDao(BoostAndBlockProductDao dao) {
		this.baseDAO = dao;
	}
}
