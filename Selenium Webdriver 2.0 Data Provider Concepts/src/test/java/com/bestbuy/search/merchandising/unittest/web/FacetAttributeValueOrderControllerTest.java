package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ResourceBundle;

import javax.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.ModelValidatorAspect;
import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.FacetAttributeValueOrderService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.web.FacetAttributeValueOrderController;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for the facet attribute value order controller
 */
public class FacetAttributeValueOrderControllerTest {
	
	FacetAttributeValueOrderController facetAttributeValueOrderController = null;
	FacetAttributeValueOrderService facetAttributeValueOrderService = null;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse = null;
	ResourceBundle errorMessages = null;
	ResourceBundle successMessages = null;
	ResourceBundleHandler resourceBundleHandler = null;
	UsersService usersService;
	ModelValidatorAspect modelValidatorAspect = null;
	Validator validator = null;
	
	/*
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		facetAttributeValueOrderService = mock(FacetAttributeValueOrderService.class);
		facetAttributeValueOrderController = new FacetAttributeValueOrderController();
		facetAttributeValueOrderController.setFacetAttributeValueOrderService(facetAttributeValueOrderService);
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setErrorMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		facetAttributeValueOrderController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		usersService = mock(UsersService.class);
	}
	
	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		
		facetAttributeValueOrderController = null;
		facetAttributeValueOrderService = null;
		usersService = null;
	}
 
	/**
	 * Test the load facet value scenario
	 * @throws Exception
	 */
	@Test
	public void testLoadFacetsValues() throws Exception{
		Long facetId = 1L;
		List<IWrapper> attributeValueWrappers = BaseData.getAttributeValueWrapperList();
		when(resourceBundleHandler.getSuccessString("000001", "Facet")).thenReturn("Facet value load was successful");
		when(facetAttributeValueOrderService.loadFacetValues(facetId)).thenReturn(attributeValueWrappers);
		MerchandisingBaseResponse<IWrapper> resp = facetAttributeValueOrderController.loadFacetValues(facetId);
		assertTrue(resp.getSuccessCode().equals("LoadFacetValues.Success"));
		facetAttributeValueOrderController.loadFacetValues(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}
	
	/**
	 * Test the load facet value exception scenario
	 * @throws Exception
	 */
	@Test
	public void testLoadFacetsValuesException() throws Exception {
		Long facetId = 1L;
		doThrow(ServiceException.class).when(facetAttributeValueOrderService).loadFacetValues(facetId);
		when(resourceBundleHandler.getErrorString("000003","Facet")).thenReturn("Retrive.Error");
		MerchandisingBaseResponse<IWrapper> rep = facetAttributeValueOrderController.loadFacetValues(facetId);
		assertTrue(rep.getErrorCode().equals("LoadFacetValues.Error"));
	}
	
	/**
	 * Test the null check for the load facet value method
	 * @throws Exception
	 */
	@Test
	public void testNegLoadFacetsValues() throws Exception{
		Long facetId = 1L;
		List<IWrapper> attributeValueWrappers = BaseData.getAttributeValueWrapperList();
		when(facetAttributeValueOrderService.loadFacetValues(facetId)).thenReturn(attributeValueWrappers);
		MerchandisingBaseResponse<IWrapper> resp = facetAttributeValueOrderController.loadFacetValues(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}
}
