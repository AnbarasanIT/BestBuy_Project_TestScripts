/**
 * Nov 6, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryFacetDAO;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.unittest.common.FacetDisplayOrderTestHelper;
import com.bestbuy.search.merchandising.web.CategoryFacetController;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetOrderWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * @author Ramiro.Serrato
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:spring/it-applicationContext*.xml"})
public class FacetDisplayOrderTest {
	@Mock private CategoryFacetDAO categoryFacetDAOMock;
	@Mock private EntityManager entityManagerMock;
	@Mock private Query queryMock;
	@Mock private TypedQuery<Long> queryMock1;
	@Mock private CategoryFacetService categoryFacetServiceMock;
	@Mock private ResourceBundleHandler resourceBundleHandlerMock;
	@Mock private CategoryNodeService categoryNodeService;
	@Mock private StatusService statusService;
	
	@InjectMocks
	private CategoryFacetService categoryFacetService;
	private CategoryFacetDAO categoryFacetDAO;
	private FacetDisplayOrderTestHelper helper;
	private CategoryFacetController categoryFacetController;
	private MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;

	@Mock private GeneralWorkflow generalWorkflow;
	@Mock private UserUtil userUtil;
	@Mock private FacetService facetService;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		helper = new FacetDisplayOrderTestHelper();
		categoryFacetService.setDao(categoryFacetDAOMock);
		categoryFacetDAO = new CategoryFacetDAO();
		categoryFacetDAO.setEntityManager(entityManagerMock);
		
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandlerMock);
		
		categoryFacetController = new CategoryFacetController();
		categoryFacetController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		categoryFacetController.setCategoryFacetService(categoryFacetServiceMock);
		
		/*categoryFacetService.setCategoryNodeService(categoryNodeService);
		categoryFacetService.setStatusService(statusService);
		categoryFacetService.setGeneralWorkflow(generalWorkflow);
		categoryFacetService.setUserUtil(userUtil);
		categoryFacetService.setFacetService(facetService);*/
	}
	
	@After
	public void destroy() {
		categoryFacetDAO = null;
		categoryFacetService = null;
		categoryFacetController = null;
		statusService = null;
	}
	
	///////// CategoryFacetOrderWrapper
	
	@Test
	public void testCategoryFacetOrderWrapper() {
		CategoryFacetOrderWrapper wrapper = new CategoryFacetOrderWrapper();
		Assert.assertNotNull(wrapper);
		
		wrapper.setCategoryFacetId(1L);
		Assert.assertTrue(1L == wrapper.getCategoryFacetId());
		
		wrapper.setDisplayName("Brand");
		Assert.assertTrue("Brand".equals(wrapper.getDisplayName()));
		
		wrapper.setDisplayOrder(1L);
		Assert.assertTrue(1L == wrapper.getDisplayOrder());
		
		wrapper.setSystemName("System");
		Assert.assertTrue("System".equals(wrapper.getSystemName()));
		
		wrapper.setStatus("Draft");
		Assert.assertTrue("Draft".equals(wrapper.getStatus()));
		
		wrapper.setValues("Samsung, Sony, Apple");
		Assert.assertTrue("Samsung, Sony, Apple".equals(wrapper.getValues()));
		
		List<IWrapper> createdWrappers = CategoryFacetOrderWrapper.getWrappers(helper.getCategoryFacets());
		List<CategoryFacetOrderWrapper> testWrappers = helper.getCategoryFacetWrappers();
				
		for(int i=0; i<testWrappers.size(); i++) {
			CategoryFacetOrderWrapper createdWrapper = (CategoryFacetOrderWrapper) createdWrappers.get(i);
			CategoryFacetOrderWrapper testWrapper = testWrappers.get(i);
			
			Assert.assertTrue("Wrappers are different", createdWrapper.equals(testWrapper));
		}
	}
	
	///////// CategoryFacetDAO
	
	///////////////// Display Order List
	
//	@Test
//	public void testDAOLoadCategoryFacetsForDisplayOrder() throws DataAcessException{
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(entityManagerMock.createQuery(Mockito.anyString())).thenReturn(queryMock);
//		when(queryMock.getResultList()).thenReturn(helper.getCategoryFacets());
//		List<CategoryFacet> categoryFacets = categoryFacetDAO.loadCategoryFacetsForDisplayOrder(categoryString);
//		
//		Assert.assertNotNull(categoryFacets);		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test(expected=DataAcessException.class)
//	public void testDAOLoadCategoryFacetsForDisplayOrderDAOException() throws DataAcessException{
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(entityManagerMock.createQuery(Mockito.anyString())).thenThrow(Exception.class);
//		categoryFacetDAO.loadCategoryFacetsForDisplayOrder(categoryString);
//	}
	
	///////////////// Dependant Facet List	
//	@Test
//	public void testDAOLoadDependantFacetsForCategoryDisplayOrder() throws DataAcessException{
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(entityManagerMock.createQuery(Mockito.anyString())).thenReturn(queryMock);
//		when(queryMock.getResultList()).thenReturn(helper.getCategoryFacets());
//		List<CategoryFacet> categoryFacets = categoryFacetDAO.loadDependantFacetsForCategoryDisplayOrder(categoryString);
//		
//		Assert.assertNotNull(categoryFacets);		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test(expected=DataAcessException.class)
//	public void testDAOloadDependantFacetsForCategoryDisplayOrderDAOException() throws DataAcessException{
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(entityManagerMock.createQuery(Mockito.anyString())).thenThrow(Exception.class);
//		categoryFacetDAO.loadDependantFacetsForCategoryDisplayOrder(categoryString);
//	}	
//	
//	///////////////// Hidden Facet List	
//	@Test
//	public void testDAOLoadHiddenFacetsForCategoryDisplayOrder() throws DataAcessException{
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(entityManagerMock.createQuery(Mockito.anyString())).thenReturn(queryMock);
//		when(queryMock.getResultList()).thenReturn(helper.getCategoryFacets());
//		List<CategoryFacet> categoryFacets = categoryFacetDAO.loadHiddenFacetsForCategoryDisplayOrder(categoryString);
//		
//		Assert.assertNotNull(categoryFacets);		
//	}
	
//	@SuppressWarnings("unchecked")
//	@Test(expected=DataAcessException.class)
//	public void testDAOloadHiddenFacetsForCategoryDisplayOrderDAOException() throws DataAcessException{
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(entityManagerMock.createQuery(Mockito.anyString())).thenThrow(Exception.class);
//		categoryFacetDAO.loadHiddenFacetsForCategoryDisplayOrder(categoryString);
//	}	
	
	///////////////// Update
	
	@Test
	public void testDAOUpdateCategoryFacetsOrder() throws DataAcessException {
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		when(entityManagerMock.merge(Mockito.any(CategoryFacet.class))).thenReturn(categoryFacets.get(0));
		List<CategoryFacet> resultCategoryFacets = categoryFacetDAO.updateCategoryFacetsOrder(categoryFacets);		
		
		Assert.assertNotNull(resultCategoryFacets);
		
		for(CategoryFacet categoryFacet : resultCategoryFacets) {
			Assert.assertTrue(categoryFacet == categoryFacets.get(0));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=DataAcessException.class)
	public void testDAOUpdateCategoryFacetsOrderDataAcessException() throws DataAcessException {
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		when(entityManagerMock.merge(Mockito.any(CategoryFacet.class))).thenThrow(Exception.class);
		categoryFacetDAO.updateCategoryFacetsOrder(categoryFacets);		
	}
	
	///////////////// Create Facet
	@Test
	public void testDAOGetMaxDisplayOrder() throws DataAcessException {
		when(entityManagerMock.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenReturn(queryMock1);
		when(queryMock1.getSingleResult()).thenReturn(1L);
		
		Long maxOrder = categoryFacetDAO.getMaxDisplayOrder("cat1");		
		
		Assert.assertTrue(maxOrder == 1L);
	}	
	
	@SuppressWarnings("unchecked")
	@Test(expected=DataAcessException.class)
	public void testDAOGetMaxDisplayOrderException() throws DataAcessException {
		when(entityManagerMock.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenThrow(Exception.class);
		categoryFacetDAO.getMaxDisplayOrder("cat1");
	}		

	///////// CategoryFacetService
	
	///////////////// Display Order List
//	@Test
//	public void testServiceLoadFacetsForCategory() throws ServiceException, DataAcessException {
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadCategoryFacetsForDisplayOrder(categoryString)).thenReturn(helper.getCategoryFacets());
//		List<IWrapper> categoryFacets = categoryFacetService.loadCategoryFacetsForDisplayOrder(categoryString);
//		
//		Assert.assertNotNull(categoryFacets);
//	}
//
//	@Test(expected=ServiceException.class)
//	public void testServiceLoadFacetsForCategoryServiceExceptionCatIdNull() throws DataAcessException, ServiceException {
//		Category categoryNode = helper.getCategory1();
//		String categoryString = categoryNode.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadCategoryFacetsForDisplayOrder(categoryString)).thenReturn(helper.getCategoryFacets());
//		categoryFacetService.loadCategoryFacetsForDisplayOrder(null);
//	}	
//
//	@SuppressWarnings("unchecked")
//	@Test(expected=ServiceException.class)
//	public void testServiceLoadFacetsForCategoryServiceExceptionDAOExcep() throws DataAcessException, ServiceException {
//		Category categoryNode = helper.getCategory1();
//		String categoryString = categoryNode.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadCategoryFacetsForDisplayOrder(categoryString)).thenThrow(DataAcessException.class);
//		categoryFacetService.loadCategoryFacetsForDisplayOrder(categoryString);
//	}	
//	
	///////////////// Hidden List
//	@Test
//	public void testServiceLoadHiddenFacetsForCategoryDisplayOrder() throws ServiceException, DataAcessException {
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadHiddenFacetsForCategoryDisplayOrder(categoryString)).thenReturn(helper.getCategoryFacets());
//		List<IWrapper> categoryFacets = categoryFacetService.loadHiddenFacetsForCategoryDisplayOrder(categoryString);
//		
//		Assert.assertNotNull(categoryFacets);
//	}
//
//	@Test(expected=ServiceException.class)
//	public void testServiceLoadHiddenFacetsForCategoryDisplayOrderExceptionCatIdNull() throws DataAcessException, ServiceException {
//		Category categoryNode = helper.getCategory1();
//		String categoryString = categoryNode.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadHiddenFacetsForCategoryDisplayOrder(categoryString)).thenReturn(helper.getCategoryFacets());
//		categoryFacetService.loadHiddenFacetsForCategoryDisplayOrder(null);
//	}	
//
//	@SuppressWarnings("unchecked")
//	@Test(expected=ServiceException.class)
//	public void testServiceLoadHiddenFacetsForCategoryDisplayOrderExceptionDAOExcep() throws DataAcessException, ServiceException {
//		Category categoryNode = helper.getCategory1();
//		String categoryString = categoryNode.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadHiddenFacetsForCategoryDisplayOrder(categoryString)).thenThrow(DataAcessException.class);
//		categoryFacetService.loadHiddenFacetsForCategoryDisplayOrder(categoryString);
//	}	
//	
//	///////////////// Dependant List	
//	@Test
//	public void testServiceLoadDependantFacetsForCategoryDisplayOrder() throws ServiceException, DataAcessException {
//		Category category = helper.getCategory1();
//		String categoryString = category.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadDependantFacetsForCategoryDisplayOrder(categoryString)).thenReturn(helper.getCategoryFacets());
//		List<IWrapper> categoryFacets = categoryFacetService.loadDependantFacetsForCategoryDisplayOrder(categoryString);
//		
//		Assert.assertNotNull(categoryFacets);
//	}
//
//	@Test(expected=ServiceException.class)
//	public void testServiceLoadDependantFacetsForCategoryDisplayOrderExceptionCatIdNull() throws DataAcessException, ServiceException {
//		Category categoryNode = helper.getCategory1();
//		String categoryString = categoryNode.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadDependantFacetsForCategoryDisplayOrder(categoryString)).thenReturn(helper.getCategoryFacets());
//		categoryFacetService.loadDependantFacetsForCategoryDisplayOrder(null);
//	}	
//
//	@SuppressWarnings("unchecked")
//	@Test(expected=ServiceException.class)
//	public void testServiceLoadDependantFacetsForCategoryDisplayOrderExceptionDAOExcep() throws DataAcessException, ServiceException {
//		Category categoryNode = helper.getCategory1();
//		String categoryString = categoryNode.getCategoryNodeId();
//		when(categoryFacetDAOMock.loadDependantFacetsForCategoryDisplayOrder(categoryString)).thenThrow(DataAcessException.class);
//		categoryFacetService.loadDependantFacetsForCategoryDisplayOrder(categoryString);
//	}		
	
	///////////////// Update
	
	@SuppressWarnings("unchecked")
	@Test
	public void testServiceUpdateCategoryFacetsOrder() throws ServiceException, DataAcessException, WorkflowException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = helper.getCategoryFacetWrappers();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		
		for(int i=0; i<categoryFacetWrappers.size(); i++) {
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	eqCriteria = criteria.getSearchTerms();
			eqCriteria.put("obj.catgFacetId", categoryFacetWrappers.get(i).getCategoryFacetId());
			ArrayList<CategoryFacet> categoryFacetList = new ArrayList<CategoryFacet>();
			categoryFacetList.add(categoryFacets.get(i));
			when(categoryFacetDAOMock.executeQuery(criteria)).thenReturn(categoryFacetList);
		}
		
		when(categoryFacetDAOMock.updateCategoryFacetsOrder(Mockito.anyList())).thenReturn(categoryFacets);
		when(categoryNodeService.retrieveById(Mockito.anyString())).thenReturn(helper.getCategory1());
		when(statusService.retrieveById(APPROVE_STATUS)).thenReturn(BaseData.getApproveStatus());
		when(generalWorkflow.stepForward("Draft", EDIT)).thenReturn("Published AF");
		when(generalWorkflow.stepForward("Approved", EDIT)).thenReturn("Published AF");
		List<IWrapper> resultCategoryFacetWrappers = categoryFacetService.updateCategoryFacetsOrder(categoryFacetWrappers);
		
		Assert.assertNotNull(resultCategoryFacetWrappers);
	} 
	
	@Test(expected=ServiceException.class)
	public void testServiceUpdateCategoryFacetsOrderServiceExceptionWrappersNull() throws ServiceException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = null;
		categoryFacetService.updateCategoryFacetsOrder(categoryFacetWrappers);
	}	
	
	@Test(expected=ServiceException.class)
	public void testServiceUpdateCategoryFacetsOrderServiceExceptionWrappersEmpty() throws ServiceException {	
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = new ArrayList<CategoryFacetOrderWrapper>();
		categoryFacetService.updateCategoryFacetsOrder(categoryFacetWrappers);
	}

	@Test(expected=ServiceException.class)
	public void testServiceUpdateCategoryFacetsOrderServiceExceptionNoNullOrder() throws ServiceException, DataAcessException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = helper.getCategoryFacetWrappers();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		Long temp = categoryFacetWrappers.get(0).getDisplayOrder();
		categoryFacetWrappers.get(0).setDisplayOrder(null);  // temporary set to null for this test case purposes	
		
		for(int i=0; i<categoryFacetWrappers.size(); i++) {
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	eqCriteria = criteria.getSearchTerms();
			eqCriteria.put("obj.catgFacetId", categoryFacetWrappers.get(i).getCategoryFacetId());
			ArrayList<CategoryFacet> categoryFacetList = new ArrayList<CategoryFacet>();
			categoryFacetList.add(categoryFacets.get(i));
			when(categoryFacetDAOMock.executeQuery(criteria)).thenReturn(categoryFacetList);
		}
		
		try {
			categoryFacetService.updateCategoryFacetsOrder(categoryFacetWrappers);
		}
		
		finally {
			categoryFacetWrappers.get(0).setDisplayOrder(temp);  // set back the old value in order to use this same object in the other test cases
		}
	}

	@Test(expected=ServiceException.class) 
	public void testServiceUpdateCategoryFacetsOrderServiceExceptionNoBadStatus() throws ServiceException, DataAcessException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = helper.getCategoryFacetWrappers();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		
		for(int i=0; i<categoryFacetWrappers.size(); i++) {
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	eqCriteria = criteria.getSearchTerms();
			eqCriteria.put("obj.catgFacetId", categoryFacetWrappers.get(i).getCategoryFacetId());
			ArrayList<CategoryFacet> categoryFacetList = new ArrayList<CategoryFacet>();
			categoryFacetList.add(categoryFacets.get(i));
			when(categoryFacetDAOMock.executeQuery(criteria)).thenReturn(categoryFacetList);
		}		
		
		categoryFacets.get(0).getFacet().setStatus(helper.getStatusDeleted());
		when(categoryNodeService.retrieveById(Mockito.anyString())).thenReturn(helper.getCategory1());
		categoryFacetService.updateCategoryFacetsOrder(categoryFacetWrappers);
	}	

	@SuppressWarnings("unchecked")
	@Test(expected=ServiceException.class)
	public void testServiceUpdateCategoryFacetsOrderServiceExceptionDAOExcep() throws DataAcessException, ServiceException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = helper.getCategoryFacetWrappers();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		
		for(int i=0; i<categoryFacetWrappers.size(); i++) {
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	eqCriteria = criteria.getSearchTerms();
			eqCriteria.put("obj.catgFacetId", categoryFacetWrappers.get(i).getCategoryFacetId());
			ArrayList<CategoryFacet> categoryFacetList = new ArrayList<CategoryFacet>();
			categoryFacetList.add(categoryFacets.get(i));
			when(categoryFacetDAOMock.executeQuery(criteria)).thenReturn(categoryFacetList);
		}
		when(categoryNodeService.retrieveById(Mockito.anyString())).thenReturn(helper.getCategory1());
		when(categoryFacetDAOMock.updateCategoryFacetsOrder(Mockito.anyList())).thenThrow(DataAcessException.class);
		categoryFacetService.updateCategoryFacetsOrder(categoryFacetWrappers);
	}

	@Test
	public void testServiceFixOrder() throws ServiceException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = helper.getCategoryFacetWrappers();
		categoryFacetWrappers.get(0).setDisplayOrder(5L);
		categoryFacetWrappers.get(1).setDisplayOrder(1L);
	
		List<CategoryFacetOrderWrapper> resultCategoryFacetWrappers = categoryFacetService.fixOrder(categoryFacetWrappers);
		
		for(int i=0; i<resultCategoryFacetWrappers.size(); i++) {
			Assert.assertTrue(resultCategoryFacetWrappers.get(i).getDisplayOrder() == i);
		}
	}	
	
	@Test
	public void testServiceRetrieveAndApplyOrderToCategoryFacets() throws ServiceException,DataAcessException {
		List<CategoryFacetOrderWrapper> categoryFacetWrappers = helper.getCategoryFacetWrappers();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		
		for(int i=0; i<categoryFacetWrappers.size(); i++) {
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	eqCriteria = criteria.getSearchTerms();
			eqCriteria.put("obj.catgFacetId", categoryFacetWrappers.get(i).getCategoryFacetId());
			ArrayList<CategoryFacet> categoryFacetList = new ArrayList<CategoryFacet>();
			categoryFacetList.add(categoryFacets.get(i));
			when(categoryFacetDAOMock.executeQuery(criteria)).thenReturn(categoryFacetList);
		}
		when(categoryNodeService.retrieveById(Mockito.anyString())).thenReturn(helper.getCategory1());
		categoryFacets = categoryFacetService.retrieveAndApplyOrderToCategoryFacets(categoryFacetWrappers);
		
		for(int i=0; i<categoryFacets.size(); i++) {
			Assert.assertTrue(categoryFacetWrappers.get(i).getDisplayOrder().equals(categoryFacets.get(i).getDisplayOrder()));
		}
	}
	
	///////////////// Create Facet	
	
	@Test
	public void testServiceSetDisplayOrder() throws ServiceException, DataAcessException {
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		 
		// lets suppose we have a max order of 100
		when(categoryFacetDAOMock.getMaxDisplayOrder(categoryFacets.get(0).getCategoryNode().getCategoryNodeId())).thenAnswer(
			new Answer<Long>() {
				private long k=100;

				@Override
				public Long answer(InvocationOnMock invocation)	throws Throwable {
					return k++;
				}
			}
		);
		
		categoryFacets = categoryFacetService.setDisplayOrder(categoryFacets);
		int j = 0;
		
		for(int i=0; i<categoryFacets.size(); i++){  // checking the display order of each object to be correct or null
			if(categoryFacets.get(i).getFacet().getDisplay().equals(DisplayEnum.Y) && categoryFacets.get(i).getDepFacet() == null) {
				Assert.assertTrue(categoryFacets.get(i).getDisplayOrder() == (101 + j++));
			} 
			 
			else {
				Assert.assertNull(categoryFacets.get(i).getDisplayOrder());
			}
		}
		
		// lets suppose we have a null max order, this is this facet is the first added to this page
		when(categoryFacetDAOMock.getMaxDisplayOrder(categoryFacets.get(0).getCategoryNode().getCategoryNodeId())).thenAnswer(
				new Answer<Long>() {
					private long k=0;

					@Override
					public Long answer(InvocationOnMock invocation)	throws Throwable {
						if(k++ == 0) {
							return null;
						}
						
						else{
							return k-2;    // in order to have a secuence: null, 0, 1, ...
						}
					}
				}
		);
		
		categoryFacets = categoryFacetService.setDisplayOrder(categoryFacets);
		j=0;
		
		for(int i=0; i<categoryFacets.size(); i++){
			if(categoryFacets.get(i).getFacet().getDisplay().equals(DisplayEnum.Y) && categoryFacets.get(i).getDepFacet() == null) {			
				Assert.assertTrue(categoryFacets.get(i).getDisplayOrder() == j++);
			}
		} 
	}
	
	@Test
	public void testServiceSetDisplayOrderNullInput() throws ServiceException, DataAcessException {
		List<CategoryFacet> categoryFacets = categoryFacetService.setDisplayOrder(null);
		
		Assert.assertNull(categoryFacets);
	}	 
	
	@Test
	public void testServiceSetDisplayOrderEmptyListInput() throws ServiceException, DataAcessException {
		List<CategoryFacet> categoryFacets = new ArrayList<CategoryFacet>(); 		
		categoryFacets = categoryFacetService.setDisplayOrder(categoryFacets);
		
		Assert.assertTrue(categoryFacets.isEmpty());
	}		
	
	@SuppressWarnings("unchecked")
	@Test(expected=ServiceException.class)
	public void testServiceSetDisplayOrderDAOException() throws ServiceException, DataAcessException {
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		
		when(categoryFacetDAOMock.getMaxDisplayOrder(categoryFacets.get(0).getCategoryNode().getCategoryNodeId())).thenThrow(DataAcessException.class);
		categoryFacets = categoryFacetService.setDisplayOrder(categoryFacets);
	}	
	
	///////// Controller - CategoryFacetController
	
	///////////////// List	
	@Test
	public void testControllerLoadFacetsForCategoryDisplayOrder() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		String categoryId = "categoryId";
		
		when(categoryFacetServiceMock.loadCategoryFacetsForDisplayOrder(categoryId)).thenReturn(helper.getFacetCategoryWrappers());
		baseResponse = categoryFacetController.loadFacetsForCategoryDisplayOrder(categoryId);
		
		Assert.assertTrue(baseResponse.getSuccessCode().equals("Listing.Success"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testControllerLoadFacetsForCategoryDisplayOrderException() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		String categoryId = "categoryId";
		
		when(categoryFacetServiceMock.loadCategoryFacetsForDisplayOrder(categoryId)).thenThrow(ServiceException.class);
		baseResponse = categoryFacetController.loadFacetsForCategoryDisplayOrder(categoryId);
		
		Assert.assertTrue(baseResponse.getErrorCode().equals("Listing.Error"));
	}

	///////////////// Update

	@Test
	public void testControllerUpdateDisplayOrder() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		List<CategoryFacetOrderWrapper> wrappers = helper.getCategoryFacetWrappers();
		
		when(categoryFacetServiceMock.updateCategoryFacetsOrder(wrappers)).thenReturn(helper.getFacetCategoryWrappers());
		baseResponse = categoryFacetController.updateDisplayOrder(wrappers.toArray(new CategoryFacetOrderWrapper[wrappers.size()]));
		
		Assert.assertTrue(baseResponse.getSuccessCode().equals("Update.Success"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testControllerUpdateDisplayOrderException() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		List<CategoryFacetOrderWrapper> wrappers = helper.getCategoryFacetWrappers();
		
		when(categoryFacetServiceMock.updateCategoryFacetsOrder(wrappers)).thenThrow(ServiceException.class);
		baseResponse = categoryFacetController.updateDisplayOrder(wrappers.toArray(new CategoryFacetOrderWrapper[wrappers.size()]));
		
		Assert.assertTrue(baseResponse.getErrorCode().equals("Update.Error"));
	}
	
	///////////////// List dependant facets
	@Test
	public void testControllerLoadDependantFacetsForCategoryDisplayOrder() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		String categoryId = "categoryId";
		
		when(categoryFacetServiceMock.loadDependantFacetsForCategoryDisplayOrder(categoryId)).thenReturn(helper.getFacetCategoryWrappers());
		baseResponse = categoryFacetController.loadDependantFacetsForCategoryDisplayOrder(categoryId);
		
		Assert.assertTrue(baseResponse.getSuccessCode().equals("Listing.Success"));
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testControllerLoadDependantFacetsForCategoryDisplayOrderException() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		String categoryId = "categoryId";
		
		when(categoryFacetServiceMock.loadDependantFacetsForCategoryDisplayOrder(categoryId)).thenThrow(ServiceException.class);
		baseResponse = categoryFacetController.loadDependantFacetsForCategoryDisplayOrder(categoryId);
		Assert.assertTrue(baseResponse.getErrorCode().equals("Listing.Error"));
	}	
	
	///////////////// List hidden facets
	@Test
	public void testControllerLoadHiddenFacetsForCategoryDisplayOrder() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		String categoryId = "categoryId";
		
		when(categoryFacetServiceMock.loadHiddenFacetsForCategoryDisplayOrder(categoryId)).thenReturn(helper.getFacetCategoryWrappers());
		baseResponse = categoryFacetController.loadHiddenFacetsForCategoryDisplayOrder(categoryId);
		
		Assert.assertTrue(baseResponse.getSuccessCode().equals("Listing.Success"));
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testControllerLoadHiddenFacetsForCategoryDisplayOrderException() throws ServiceException {
		MerchandisingBaseResponse<IWrapper> baseResponse;
		String categoryId = "categoryId";
		
		when(categoryFacetServiceMock.loadHiddenFacetsForCategoryDisplayOrder(categoryId)).thenThrow(ServiceException.class);
		baseResponse = categoryFacetController.loadHiddenFacetsForCategoryDisplayOrder(categoryId);
		
		Assert.assertTrue(baseResponse.getErrorCode().equals("Listing.Error"));
	}	
}
