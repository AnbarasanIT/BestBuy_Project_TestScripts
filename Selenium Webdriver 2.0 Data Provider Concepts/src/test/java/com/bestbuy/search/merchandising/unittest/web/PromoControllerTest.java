package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.PromoDAO;
import com.bestbuy.search.merchandising.service.PromoService;
import com.bestbuy.search.merchandising.unittest.common.PromoTestData;
import com.bestbuy.search.merchandising.web.PromoController;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class PromoControllerTest {
	
	PromoController promoController;
	PromoService promoService;
	PromoDAO promoDAO;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
	ResourceBundle errorMessages;
	ResourceBundle successMessages;
	ResourceBundleHandler resourceBundleHandler;
	GeneralWorkflow generalWorkflow;
	
	
	/*
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		promoController = new PromoController();
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		promoDAO = mock(PromoDAO.class);
		promoService = mock(PromoService.class);
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		generalWorkflow = mock(GeneralWorkflow.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setErrorMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		promoService.setDao(promoDAO);
		promoController.setPromoService(promoService);
		promoController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		promoController.setGeneralWorkflow(generalWorkflow);
	}

	@Test
	public void testLoadPromos() throws Exception {
		List<IWrapper> wrappers = PromoTestData.getPromoWrappers();
		@SuppressWarnings("serial")
		Set<String> status =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(promoService.loadPromos(new PaginationWrapper())).thenReturn(wrappers);
		when(resourceBundleHandler.getSuccessString("Listing.Success", "Promo")).thenReturn("Promo listing was successful");
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(status);
		MerchandisingBaseResponse<IWrapper> response = promoController.loadPromos(new PaginationWrapper());
		assertTrue(response.getMessage().equals("Promo listing was successful"));
	}
	
	@Test
	public void testLoadPromosException() throws Exception {
		when(promoService.loadPromos(Mockito.any(PaginationWrapper.class))).thenThrow(new ServiceException());
		when(resourceBundleHandler.getErrorString("Listing.Error", "Promo")).thenReturn("Error while retrieving the Promo list");
		MerchandisingBaseResponse<IWrapper> response = promoController.loadPromos(new PaginationWrapper());
		assertTrue(response.getMessage().equals("Error while retrieving the Promo list"));
	}
	
	@Test
	public void testCreate() throws Exception{
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		IWrapper wrapper = new PromoWrapper();
		when(promoService.createPromo(promoWrapper)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Create.Success", "Promo")).thenReturn("Promo created successfully");
		MerchandisingBaseResponse<IWrapper> resp = promoController.create(promoWrapper);
		assertNotNull(resp.getData());
	}
	
	@Test
	public void testCreateNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = promoController.create(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	

	@Test
	public void testCreateException() throws Exception {
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		when(promoService.createPromo(promoWrapper)).thenThrow(new ServiceException());
		when(resourceBundleHandler.getErrorString("Create.Error", "Promo")).thenReturn("Error while trying to create the Promo");
		MerchandisingBaseResponse<IWrapper> response = promoController.create(promoWrapper);
		assertTrue(response.getMessage().equals("Error while trying to create the Promo"));
	}
	
	@Test
	public void testDelete() throws Exception{
		IWrapper wrapper = new PromoWrapper();
		when(promoService.deletePromo(101l)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Delete.Success", "Promo")).thenReturn("Promo deleted successfully");
		MerchandisingBaseResponse<IWrapper> resp = promoController.delete(101l);
		assertTrue(resp.getMessage().equals("Promo deleted successfully"));
	}
	
	@Test
	public void testDeleteNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = promoController.delete(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testDeleteException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(promoService).deletePromo(101l);
		when(resourceBundleHandler.getErrorString("Delete.Error", "Promo")).thenReturn("Error while trying to delete the Promo");
		MerchandisingBaseResponse<IWrapper> response = promoController.delete(101l);
		assertTrue(response.getMessage().equals("Error while trying to delete the Promo"));
	}
	
	@Test
	public void testApprove() throws Exception{
		IWrapper wrapper = new PromoWrapper();
		when(promoService.approvePromo(109l)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Approve.Success", "Promo")).thenReturn("The Promo was approved");
		MerchandisingBaseResponse<IWrapper> resp = promoController.approve(109l);
		assertTrue(resp.getMessage().equals("The Promo was approved"));
	}
	
	@Test
	public void testApproveNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = promoController.approve(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testApproveException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(promoService).approvePromo(109l);
		when(resourceBundleHandler.getErrorString("Approve.Error", "Promo")).thenReturn("Error while trying to approve the Promo");
		MerchandisingBaseResponse<IWrapper> response = promoController.approve(109l);
		assertTrue(response.getMessage().equals("Error while trying to approve the Promo"));
	}
	
	@Test
	public void testReject() throws Exception{
		IWrapper wrapper = new PromoWrapper();
		when(promoService.rejectPromo(103l)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Reject.Success", "Promo")).thenReturn("The Promo was rejected");
		MerchandisingBaseResponse<IWrapper> resp = promoController.reject(103l);
		assertTrue(resp.getMessage().equals("The Promo was rejected"));
	}
	
	@Test
	public void testRejectNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = promoController.reject(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testRejectException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(promoService).rejectPromo(103l);
		when(resourceBundleHandler.getErrorString("Reject.Error", "Promo")).thenReturn("Error while trying to reject the Promo");
		MerchandisingBaseResponse<IWrapper> response = promoController.reject(103l);
		assertTrue(response.getMessage().equals("Error while trying to reject the Promo"));
	}
	
	@Test
	public void testUpdate() throws Exception{
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		IWrapper wrapper = new PromoWrapper();
		when(promoService.updatePromo(promoWrapper)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Update.Success", "Promo")).thenReturn("Promo updated successfully");
		MerchandisingBaseResponse<IWrapper> resp = promoController.update(promoWrapper);
		assertNotNull(resp.getData());
	}
	
	@Test
	public void testUpdateNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = promoController.update(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	

	@Test
	public void testUpdateException() throws Exception {
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		when(promoService.updatePromo(promoWrapper)).thenThrow(new ServiceException());
		when(resourceBundleHandler.getErrorString("Update.Error", "Promo")).thenReturn("Error while trying to update the Promo");
		MerchandisingBaseResponse<IWrapper> response = promoController.update(promoWrapper);
		assertTrue(response.getMessage().equals("Error while trying to update the Promo"));
	}
	
	@Test
	public void testGetPromo() throws Exception{
		
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		when(promoService.getPromo(101l)).thenReturn(promoWrapper);
		when(resourceBundleHandler.getSuccessString("Retrive.Success", "Promo")).thenReturn("Promo retrieved successfully");
		MerchandisingBaseResponse<IWrapper> resp = promoController.getPromo(101l);
		assertNotNull(resp.getData());
	}
	
	@Test
	public void testGetPromoNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = promoController.getPromo(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	

	@Test
	public void testGetPromoException() throws Exception {
		when(promoService.getPromo(101l)).thenThrow(new ServiceException());
		when(resourceBundleHandler.getErrorString("Retrive.Error", "Promo")).thenReturn("Error while trying to retrieve the Promo");
		MerchandisingBaseResponse<IWrapper> response = promoController.getPromo(101l);
		assertTrue(response.getMessage().equals("Error while trying to retrieve the Promo"));
	}
	
}
