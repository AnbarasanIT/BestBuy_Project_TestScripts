
package com.bestbuy.search.merchandising.service;

import java.util.List;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;

/**
 * Structure that defines the category operations
 * @author Lakshmi.Valluripalli
 */
public interface ICategoryNodeService  extends IBaseService<String,Category>{
	
	public List<Long> saveCategoryNodes(List<Category> categoryNodes) throws ServiceException,WorkflowException;
	
	public void invalidateBanners() throws WorkflowException, DataAcessException;
	
	public String getCategoryPath(String categoryId) throws ServiceException;
	
	public void postCategoryTree(String categoryXml) throws ServiceException;
	
	public void invokeCategoryTreeService() throws ServiceException;
	
	public String getCategoryNodeId(String categoryPath) throws ServiceException;
}
