package com.bestbuy.search.merchandising.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryDAO;
import com.bestbuy.search.merchandising.domain.CategoryTree;
import com.googlecode.ehcache.annotations.Cacheable;

/**
 * Service class to save/load the category tree 
 * @author Lakshmi.Valluripalli
 */
public class CategoryService extends BaseService<Long,CategoryTree> implements ICategoryService {
	
	/**
	 * Wires the instance of CategoryDAO
	 * @param dao
	 */
	@Autowired
	public void setDao(CategoryDAO dao) {
		this.baseDAO = dao;
	}
	
	@Autowired
	private EhCacheManagerFactoryBean ehCacheManager;
	
	public void setEhCacheManager(EhCacheManagerFactoryBean ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

	/**
	 * Saves the received category tree to DAAS
	 * Version is incremented by taking the count of rows from DB
	 * @param - String categoryTree XML
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void saveCategory(String categoryXml) throws ServiceException{
		try{
			CategoryTree category = new CategoryTree();
			category.setCategoryTree(categoryXml);
			category.setModifiedDate(new Date());
			Long count = baseDAO.getCount(null);
			category.setVersion(count+1l);
			((CategoryDAO)baseDAO).save(category);
			//clear the cache.
			ehCacheManager.getObject().getCache(CACHE_NAME).removeAll();
		}catch(DataAcessException dae){
			throw new ServiceException(dae);
		}
		
	}
	
	/**
	 * Load the CategoryTree with highest version from DB
	 */
	 @Cacheable(cacheName = CACHE_NAME) 
	public String loadCategory() throws ServiceException{
		String categoryTree = null;
		try{
			Long count = ((CategoryDAO)baseDAO).getCount(null);
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	whereCriteria = criteria.getSearchTerms();
			whereCriteria.put("obj.version", count);
			List<CategoryTree> categories = (List<CategoryTree>)((CategoryDAO)baseDAO).executeQuery(criteria);
			if(categories != null && categories.size() > 0){
				categoryTree =  categories.get(0).getCategoryTree();
			}
		}catch(DataAcessException dae){
			throw new ServiceException(dae);
		}
		return categoryTree;
	}
}
