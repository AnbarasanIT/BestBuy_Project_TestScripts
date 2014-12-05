
package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import javax.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.bestbuy.search.merchandising.common.ModelValidatorAspect;
import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.FacetService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.unittest.common.WebContextTestExecutionListener;
import com.bestbuy.search.merchandising.web.FacetController;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Chanchal.kumari
 * Modified By Kalaiselvi Jaganathan - added test cases for create and update facets
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
@TestExecutionListeners({WebContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class FacetControllerTest extends AbstractJUnit4SpringContextTests {
	
	FacetController facetController = null;
	FacetService facetService = null;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse = null;
	ResourceBundle errorMessages = null;
	ResourceBundle successMessages = null;
	ResourceBundleHandler resourceBundleHandler = null;
	UsersService usersService;
	ModelValidatorAspect modelValidatorAspect = null;
	Validator validator = null;
	GeneralWorkflow generalWorkflow = null;
	
	/*
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		facetService = mock(FacetService.class);
		facetController = new FacetController();
		facetController.setFacetService(facetService);
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setSuccessMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		facetController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		usersService = mock(UsersService.class);
		modelValidatorAspect = mock(ModelValidatorAspect.class);
		validator = mock(Validator.class);
		modelValidatorAspect.setValidator(validator);
		generalWorkflow=mock(GeneralWorkflow.class);
		facetController.setGeneralWorkflow(generalWorkflow);
	}
	
	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		
		facetController = null;
		facetService = null;
		usersService = null;
		generalWorkflow = null;
	}
 
	@Test
	public void testLoadFacets() throws Exception{
		List<FacetWrapper> facetWrappers =  BaseData.getFacetWrappers();
		when(resourceBundleHandler.getSuccessString("Listing.Success", "Facet")).thenReturn("Facet listing was successful");
		when(facetService.loadFacets(Mockito.any(PaginationWrapper.class))).thenReturn(facetWrappers);
		MerchandisingBaseResponse<IWrapper> resp = facetController.loadFacets(new PaginationWrapper());
		assertTrue(resp.getMessage().equals("Facet listing was successful"));
	}
	
	@Test
	public void testLoadFacetsException() throws Exception {
		
		when(resourceBundleHandler.getErrorString("Listing.Error", "Facet")).thenReturn("Error while retrieving the Facet list");
		when(facetService.loadFacets(Mockito.any(PaginationWrapper.class))).thenThrow(new ServiceException());
		MerchandisingBaseResponse<IWrapper> resp = facetController.loadFacets(new PaginationWrapper());
		assertTrue(resp.getMessage().equals("Error while retrieving the Facet list"));
	}
	
	@Test
	public void testDelete() throws Exception{
		doNothing().when(facetService).deleteFacet(101l);
		when(resourceBundleHandler.getSuccessString("Delete.Success", "Facet")).thenReturn("Facet deleted successfully");
		MerchandisingBaseResponse<IWrapper> resp = facetController.delete(101l);
		assertTrue(resp.getMessage().equals("Facet deleted successfully"));
	}
	
	@Test
	public void testDeleteNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = facetController.delete(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testDeleteException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(facetService).deleteFacet(101l);
		when(resourceBundleHandler.getErrorString("Delete.Error", "Facet")).thenReturn("Error while trying to delete the Facet");
		MerchandisingBaseResponse<IWrapper> response = facetController.delete(101l);
		assertTrue(response.getMessage().equals("Error while trying to delete the Facet"));
	}
	
	@Test
	public void testApprove() throws Exception{
		doNothing().when(facetService).approveFacet(109l);
		when(resourceBundleHandler.getSuccessString("Approve.Success", "Facet")).thenReturn("The Facet was approved");
		MerchandisingBaseResponse<IWrapper> resp = facetController.approve(109l);
		assertTrue(resp.getMessage().equals("The Facet was approved"));
	}
	
	@Test
	public void testApproveNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = facetController.approve(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testApproveException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(facetService).approveFacet(109l);
		when(resourceBundleHandler.getErrorString("Approve.Error", "Facet")).thenReturn("Error while trying to approve the Facet");
		MerchandisingBaseResponse<IWrapper> response = facetController.approve(109l);
		assertTrue(response.getMessage().equals("Error while trying to approve the Facet"));
	}
	
	@Test
	public void testReject() throws Exception{
		doNothing().when(facetService).rejectFacet(103l);
		when(resourceBundleHandler.getSuccessString("Reject.Success", "Facet")).thenReturn("The Facet was rejected");
		MerchandisingBaseResponse<IWrapper> resp = facetController.reject(103l);
		assertTrue(resp.getMessage().equals("The Facet was rejected"));
	}
	
	@Test
	public void testRejectNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = facetController.reject(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testRejectException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(facetService).rejectFacet(103l);
		when(resourceBundleHandler.getErrorString("Reject.Error", "Facet")).thenReturn("Error while trying to reject the Facet");
		MerchandisingBaseResponse<IWrapper> response = facetController.reject(103l);
		assertTrue(response.getMessage().equals("Error while trying to reject the Facet"));
	}

	@Test
	public void testNegCreate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = facetController.create(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testCreate() throws ServiceException, ParseException {
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get((0));
	    doNothing().when(facetService).createFacet(facetWrapper);
		when(resourceBundleHandler.getSuccessString("Create.Success", "Facet")).thenReturn("The Facet was created");
		MerchandisingBaseResponse<IWrapper> resp = facetController.create(facetWrapper);
		assertTrue(resp.getSuccessCode().equals("Create.Success"));
	}
	
	@Test
	public void testCreateExcep() throws ServiceException, ParseException {
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get((0));
		doThrow(ServiceException.class).when(facetService).createFacet(facetWrapper);
		when(resourceBundleHandler.getSuccessString("Create.Error", "Facet")).thenReturn("The Facet was not created");
		MerchandisingBaseResponse<IWrapper> resp = facetController.create(facetWrapper);
		assertTrue(resp.getErrorCode().equals("Create.Error"));
	}
	
	@Test
	public void testNegUpdate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = facetController.update(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testUpdate() throws ServiceException, ParseException, DataAcessException {
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get((0));
		doNothing().when(facetService).updateFacet(facetWrapper);
		when(resourceBundleHandler.getSuccessString("Update.Success", "Facet")).thenReturn("The Facet is updated");
		MerchandisingBaseResponse<IWrapper> resp = facetController.update(facetWrapper);
		assertTrue(resp.getSuccessCode().equals("Update.Success"));
	}
	
	@Test
	public void testUpdateExcep() throws ServiceException, ParseException, DataAcessException {
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get((0));
		doThrow(ServiceException.class).when(facetService).updateFacet(facetWrapper);
		when(resourceBundleHandler.getSuccessString("Update.Error", "Facet")).thenReturn("The Facet was not created");
		MerchandisingBaseResponse<IWrapper> resp = facetController.update(facetWrapper);
		assertTrue(resp.getErrorCode().equals("Update.Error"));
	}
	
	@Test
	public void testLoadEditFacet() {
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		Long facetId = facetWrapper.getFacetId();
		when(resourceBundleHandler.getSuccessString("Retrive.Success", "Facet")).thenReturn("The Facet is updated");
		MerchandisingBaseResponse<IWrapper> resp = facetController.loadEditFacet(facetId);
		assertTrue(resp.getSuccessCode().equals("Retrive.Success"));
	}
	
	@Test
	public void testNegLoadEditFacet() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = facetController.loadEditFacet(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testLoadEditFacetExcep() throws ServiceException, ParseException, DataAcessException {
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		Long facetId = facetWrapper.getFacetId();
		doThrow(ServiceException.class).when(facetService).loadEditFacetData(facetId);
		when(resourceBundleHandler.getSuccessString("Retrive.Error", "Facet")).thenReturn("The Facet was not created");
		MerchandisingBaseResponse<IWrapper> resp = facetController.loadEditFacet(facetId);
		assertTrue(resp.getErrorCode().equals("Retrive.Error"));
	}
}
