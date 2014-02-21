package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;

/**
 * @author Kalaiselvi Jaganathan
 * Interface for Banners
 */
public interface IBannerDAO extends IBaseDAO<Long,Banner>{
	
	public List<Banner> loadBanners(SearchCriteria criteria) throws ServiceException;
		
}
