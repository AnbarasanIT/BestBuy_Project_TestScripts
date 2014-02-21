package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.CategoryFacetService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.web.CategoryFacetController;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for category Facet controller
 */
public class CategoryFacetControllerTest {
	
	CategoryFacetController categoryFacetController = null;
	CategoryFacetService categoryFacetService = null;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse = null;
	ResourceBundle errorMessages = null;
	ResourceBundle successMessages = null;
	ResourceBundleHandler resourceBundleHandler = null;
	UsersService usersService;
	
	/*
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		categoryFacetService = mock(CategoryFacetService.class);
		categoryFacetController = new CategoryFacetController();
		categoryFacetController.setCategoryFacetService(categoryFacetService);
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setErrorMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		categoryFacetController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		usersService = mock(UsersService.class);
	}
	
	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		
		categoryFacetController = null;
		categoryFacetService = null;
		usersService = null;
	}
	
	/**
	 * Test the load facet value method
	 * @throws Exception
	 */
 
	@Test
	public void testloadFacetCategory() throws Exception{
		String catgId = "pmcat";
		List<IWrapper> attributeValueWrappers = BaseData.getAttributeValueWrapperList();
		when(resourceBundleHandler.getSuccessString("000001", "Facet")).thenReturn("Facet value load was successful");
		when(categoryFacetService.loadFacetCategory(catgId)).thenReturn(attributeValueWrappers);
		MerchandisingBaseResponse<IWrapper> resp = categoryFacetController.loadFacetCategory(catgId);
		assertTrue(resp.getSuccessCode().equals("LoadFacetCategory.Success"));

	}
	
	/**
	 * Test the exception scenario of load facet value
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testLoadFacetCategoryException() throws Exception {
		String catgId = "pmcat";
		when(categoryFacetService.loadDepFacetsForCategory(catgId,null)).thenThrow(ServiceException.class);
		when(resourceBundleHandler.getErrorString("000003","Facet")).thenReturn("Retrive.Error");
		MerchandisingBaseResponse<IWrapper> rep = categoryFacetController.loadFacetCategory(catgId);
		assertTrue(rep.getErrorCode().equals("LoadFacetCategory.Error"));
	}
	
	/**
	 * Test the null check in load facet value method
	 * @throws Exception
	 */
	@Test
	public void testNegLoadFacetCategory() throws Exception {
		String catgId = "pmcat";
		List<IWrapper> attributeValueWrappers = BaseData.getAttributeValueWrapperList();
		when(resourceBundleHandler.getSuccessString("000001", "Facet")).thenReturn("Facet value load was successful");
		when(categoryFacetService.loadFacetCategory(catgId)).thenReturn(attributeValueWrappers);
		MerchandisingBaseResponse<IWrapper> resp = categoryFacetController.loadFacetCategory(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}
}
