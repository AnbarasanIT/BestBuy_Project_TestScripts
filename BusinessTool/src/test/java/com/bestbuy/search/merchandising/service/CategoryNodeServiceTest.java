/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryNodeDAO;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;

/**
 * @author Lakshmi.Valluripalli
 * Junit test class to test th CategoryNode Changes
 */
public class CategoryNodeServiceTest {
	
	private final static Logger logger = Logger.getLogger(CategoryNodeServiceTest.class);
	CategoryNodeService categoryNodeService;
	CategoryNodeDAO categoryNodeDAO;
	ContextService contextService;
	BannerService bannerService;
	CategoryFacetService categoryFacetService;
	/**
	 * Setup method for creating mock objects
	 */
	@Before
	public void init() {
		categoryNodeService = new CategoryNodeService();
		categoryNodeDAO = mock(CategoryNodeDAO.class);
		contextService = mock(ContextService.class);
		bannerService = mock(BannerService.class);
		categoryNodeService.setDao(categoryNodeDAO);
		categoryNodeService.setContextService(contextService);
		categoryFacetService=mock(CategoryFacetService.class);
		categoryNodeService.setCategoryFacetService(categoryFacetService);
	}
	
	/**
	 * Test the Category Nodes comparison with Existing Data in DB
	 * @throws ServiceException
	 * @throws WorkflowException 
	 */
	@Test
	public void testSaveCategoryNodes() throws ServiceException, WorkflowException{
		try{
			List<Context>  contexts = BaseData.getContextLists();
			List<CategoryFacet> catgFacets = BaseData.getCategoryFacet();
			//contexts.get(0).setBanners(BaseData.getBannerDataList());
			List<Category> nodes = loadDBNodes();
			when(categoryNodeDAO.retrieveAll()).thenReturn(nodes);
			when(contextService.executeQuery(Mockito.any(SearchCriteria.class))).thenReturn(contexts);
			when(categoryFacetService.executeQuery(Mockito.any(SearchCriteria.class))).thenReturn(catgFacets);
			categoryNodeService.saveCategoryNodes(loadParsedNodes());
		}catch(DataAcessException dae){
			logger.error("Error while unit testing category Nodes",dae);
		}
	}
	
	/**
	 * Test the Initial Load of CategoryNodes 
	 * @throws ServiceException
	 * @throws WorkflowException 
	 */
	@Test
	public void testIntialSaveCategoryNodes() throws ServiceException, WorkflowException{
		try{
			List<Context>  contexts = BaseData.getContextList();
			//contexts.get(0).setBanners(BaseData.getBannerDataList());
			List<Category> nodes = new ArrayList<Category>();
			when(categoryNodeDAO.retrieveAll()).thenReturn(nodes);
			when(contextService.executeQuery(Mockito.any(SearchCriteria.class))).thenReturn(contexts);
			categoryNodeService.saveCategoryNodes(loadParsedNodes());
		}catch(DataAcessException dae){
			logger.error("Error while unit testing category Nodes",dae);
		}
	}
	
	/**
	 *  this static data is used as DB data for comparison
	 * @return
	 */
	private List<Category> loadDBNodes(){
		List<Category> nodes = new ArrayList<Category>();
		Category node = new Category();
		node.setCategoryPath("/Home/TV");
		node.setCategoryNodeId("a1009856");
		nodes.add(node);
		Category node2 = new Category();
		node2.setCategoryPath("/Home/TV/Plasma/colr3");
		node2.setCategoryNodeId("a1009859");
		Category node3 = new Category();
		node3.setCategoryPath("/Home/TV/Plasma/colr");
		node3.setCategoryNodeId("a10098567");
		Category node4 = new Category();
		node4.setCategoryPath("/Home/TV/Plasma/LED");
		node4.setCategoryNodeId("13456");
		nodes.add(node4);
		return nodes;
	}
	
	/**
	 * this static data is used as argument
	 * @return
	 */
	private List<Category> loadParsedNodes(){
		List<Category> nodesInpu = new ArrayList<Category>();
		Category node2 = new Category();
		node2.setCategoryPath("/Home/TV/Plasma");
		node2.setCategoryNodeId("a1009856");
		Category node3 = new Category();
		node3.setCategoryPath("/Home/TV/Plasma/colr2");
		node3.setCategoryNodeId("a1009858");
		Category node = new Category();
		node.setCategoryPath("/Home/TV/Plasma/colr");
		node.setCategoryNodeId("a10098567");
		nodesInpu.add(node);
		nodesInpu.add(node2);
		nodesInpu.add(node3);
		return nodesInpu;
	}

	@Test
	public void testInvalidateBannersValid() throws ServiceException, WorkflowException, DataAcessException{
		List<Long> bannerIds = new ArrayList<Long>();
		Long bannerId = 1L;
		bannerIds.add(bannerId);
		List<Banner> banners = BaseData.getBannerDataList();
		when(bannerService.executeQuery(Mockito.any(SearchCriteria.class))).thenReturn(banners);
		categoryNodeService.invalidateBanners();
	}
	
	@Test
	public void testInvalidateBanners() throws ServiceException, WorkflowException, DataAcessException{
		List<Long> bannerIds = new ArrayList<Long>();
		Long bannerId = 1L;
		bannerIds.add(bannerId);
		List<Banner> banners = BaseData.getBannerDataLists();
		when(bannerService.executeQuery(Mockito.any(SearchCriteria.class))).thenReturn(banners);
		categoryNodeService.invalidateBanners();
	}
	
	@Test
	public void testInvalidateBannersException() throws ServiceException, WorkflowException, DataAcessException{
		List<Long> bannerIds = new ArrayList<Long>();
		Long bannerId = 1L;
		bannerIds.add(bannerId);
		when(bannerService.executeQuery(Mockito.any(SearchCriteria.class))).thenThrow(new ServiceException("Error while changing the status for the synonym", new ServiceException()));
		categoryNodeService.invalidateBanners();
	}
}
