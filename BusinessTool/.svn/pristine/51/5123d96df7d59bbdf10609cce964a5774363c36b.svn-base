/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryDAO;
import com.bestbuy.search.merchandising.domain.CategoryTree;
import com.bestbuy.search.merchandising.service.CategoryService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Lakshmi.Valluripalli
 * Unit Test class to test the category Service Layer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class CategoryServiceTest {
	private final static Logger logger = Logger.getLogger(CategoryServiceTest.class);
	CategoryService categoryService;
	CategoryDAO categoryDao;
	EhCacheManagerFactoryBean ehCacheManager;
	CacheManager cacheManager;
	Cache cache;
	
	/**
	 * Setup method for creating mock objects
	 */
	@Before
	public void init() {
		categoryService = new CategoryService();
		categoryDao = mock(CategoryDAO.class);
		ehCacheManager = mock(EhCacheManagerFactoryBean.class);
		cacheManager = mock(CacheManager.class);
		cache = mock(Cache.class);
		categoryService.setDao(categoryDao);
		categoryService.setEhCacheManager(ehCacheManager);
	}
	
	/**
	 * Junit for SaveCategory Method
	 */
	@Test
	public void testSaveCategory(){
		try{
			String categoryTree = BaseData.getCategoryTree();
			when(categoryDao.getCount(null)).thenReturn(1l);
			when(categoryDao.save(Mockito.any(CategoryTree.class))).thenReturn(new CategoryTree());
			when(ehCacheManager.getObject()).thenReturn(cacheManager);
			when(cacheManager.getCache(CategoryService.CACHE_NAME)).thenReturn(cache);
			categoryService.saveCategory(categoryTree);
		}catch(DataAcessException dae){
			logger.error("while getting the count of category",dae);
		}catch(ServiceException se){
			logger.error("while testing the save category",se);
		}
	}
	
	/**
	 * Junit for loadCategory Method
	 */
	@Test
	public void testLoadCategory(){
		try{
			String categoryTree = BaseData.getCategoryTree();
			CategoryTree category = new CategoryTree();
			category.setCategoryTree(categoryTree);
			List<CategoryTree> categories = new ArrayList<CategoryTree>();
			categories.add(category);
			when(categoryDao.getCount(null)).thenReturn(1l);
			when(categoryDao.executeQuery(Mockito.any(SearchCriteria.class))).thenReturn(categories);
			String data = categoryService.loadCategory();
			assertNotNull(data);
		}catch(DataAcessException dae){
			logger.error("while getting the count of category",dae);
		}catch(ServiceException se){
			logger.error("while testing the save category",se);
		}
	}
	
	/**
	 * Junit to check the exception Handling in save category
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void testSaveCategoryExcep() throws ServiceException{
		try{
			String categoryTree = BaseData.getCategoryTree();
			when(categoryDao.getCount(null)).thenReturn(1l);
			when(categoryDao.save(Mockito.any(CategoryTree.class))).thenThrow(new DataAcessException());
			when(ehCacheManager.getObject()).thenReturn(cacheManager);
			when(cacheManager.getCache(CategoryService.CACHE_NAME)).thenReturn(cache);
			categoryService.saveCategory(categoryTree);
		}catch(DataAcessException dae){
			logger.error("while getting the count of category",dae);
		}
	}
	
	/**
	 * Junit to check the exception Handling loadCategory
	 * @throws ServiceException
	 */
	@Test(expected=ServiceException.class)
	public void testLoadCategoryExcep() throws ServiceException{
		try{
			
			when(categoryDao.getCount(null)).thenReturn(1l);
			when(categoryDao.executeQuery(Mockito.any(SearchCriteria.class))).thenThrow(new DataAcessException());
			String data = categoryService.loadCategory();
			assertNotNull(data);
		}catch(DataAcessException dae){
			logger.error("while getting the count of category",dae);
		}
	}
}
