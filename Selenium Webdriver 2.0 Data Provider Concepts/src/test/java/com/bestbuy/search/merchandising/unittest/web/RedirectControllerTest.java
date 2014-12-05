package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.service.KeywordRedirectService;
import com.bestbuy.search.merchandising.service.SearchProfileService;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.RedirectTestData;
import com.bestbuy.search.merchandising.unittest.common.WebContextTestExecutionListener;
import com.bestbuy.search.merchandising.web.KeywordRedirectController;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/** Unit Test Case to test SynonymGroupController
 * @author Chanchal.kumari
 *    10th Sep 2012
 *    Modified By Kalaiselvi.Jaganathan Added testcases for edit and load
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:spring/it-applicationContext*.xml"})
@TestExecutionListeners({WebContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class RedirectControllerTest {

	KeywordRedirectController redirectController;
	KeywordRedirectService redirectService;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
	ResourceBundle errorMessages;
	ResourceBundle successMessages;
	ResourceBundleHandler resourceBundleHandler;
	SearchProfileService searchProfileService;
	UsersService usersService;
	StatusService statusService;

	@Before
	public void init() {
		redirectController = new KeywordRedirectController();
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		redirectService = mock(KeywordRedirectService.class);
		redirectController.setKeywordRedirectService(redirectService);
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		searchProfileService = mock(SearchProfileService.class);
		usersService = mock(UsersService.class);
		statusService = mock(StatusService.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setSuccessMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		redirectController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		redirectController.setKeywordRedirectService(redirectService);
	}

	@Test
	public void testCreate() throws ServiceException{
		KeywordRedirectWrapper redirectWrapper = RedirectTestData.getCreateRedirectWrapper();
		IWrapper wrapper = new KeywordRedirectWrapper();
		when(redirectService.createKeywordRedirect(redirectWrapper)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Create.Success", "KeywordRedirect")).thenReturn("KeywordRedirect created successfully");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.create(redirectWrapper);
		assertTrue(resp.getMessage().equals("KeywordRedirect created successfully"));
	}

	@Test
	public void testNegCreate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.create(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}

	@Test
	public void testCreateException() throws Exception {
		KeywordRedirectWrapper redirectWrapper = RedirectTestData.getRedirectWrapper();
		System.err.println(" UnitTest Trace testCreateException "+redirectWrapper);
		when(redirectService.createKeywordRedirect(redirectWrapper)).thenThrow(new ServiceException());
		MerchandisingBaseResponse<IWrapper> resp = redirectController.create(redirectWrapper);
		System.err.println(" UnitTest Trace resp.getErrCod "+resp.getErrorCode());
		assertTrue(resp.getErrorCode().equals("Create.Error"));
	}

	@Test
	public void testLoadRedirectInfo() throws ServiceException{
		Map<String,List> merchconstants=new HashMap<String, List>();
		List<Status> status = new ArrayList<Status>();
		when(statusService.retrieveAll()).thenReturn(status);
		List<SearchProfile> searchProfile = new ArrayList<SearchProfile>();
		when(searchProfileService.retrieveAll()).thenReturn(searchProfile);
	}

	@Test
	public void testUpdate() throws ServiceException{
		KeywordRedirectWrapper redirectWrapper = RedirectTestData.getRedirectWrapper();
		IWrapper wrapper = new KeywordRedirectWrapper();
		when(redirectService.updateRedirect(redirectWrapper)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Update.Success", "KeywordRedirect")).thenReturn("KeywordRedirect updated successfully");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.update(redirectWrapper);
		assertTrue(resp.getMessage().equals("KeywordRedirect updated successfully"));
	}

	@Test
	public void testNegUpdate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.update(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}

	@Test
	public void testUpdateException() throws Exception {
		KeywordRedirectWrapper redirectWrapper = RedirectTestData.getRedirectWrapper();
		when(redirectService.updateRedirect(redirectWrapper)).thenThrow(new ServiceException());
		MerchandisingBaseResponse<IWrapper> resp = redirectController.update(redirectWrapper);
		assertTrue(resp.getErrorCode().equals("Update.Error"));
	} 

	@Test
	public void testLoadRedirect() throws ServiceException{
		KeywordRedirect keywordRedirect = RedirectTestData.getSavedKeywordRedirect();
		Long redirectId = keywordRedirect.getKeywordId();
		when(redirectService.loadRedirect(redirectId)).thenReturn(RedirectTestData.getListOfWrappers());
		MerchandisingBaseResponse<IWrapper> resp = redirectController.loadRedirect(redirectId);
		assertTrue(resp.getSuccessCode().equals("Retrive.Success"));
	}

	@Test
	public void testNegLoadRedirect() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.loadRedirect(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testLoadRedirectException() throws ServiceException{
		KeywordRedirectWrapper redirectWrapper = RedirectTestData.getRedirectWrapper();
		Long redirectId = redirectWrapper.getRedirectId();
		when(redirectService.loadRedirect(redirectId)).thenThrow((ServiceException.class));
		MerchandisingBaseResponse<IWrapper> resp = redirectController.loadRedirect(redirectId);
		assertTrue(resp.getErrorCode().equals("Retrieve.Error"));
	}
	
	@Test
	public void testDelete() throws ServiceException{
		IWrapper wrapper = new PromoWrapper();
		when(redirectService.deleteRedirect(1L)).thenReturn(wrapper);
		when(resourceBundleHandler.getSuccessString("Delete.Success", "KeywordRedirect")).thenReturn("KeywordRedirect deleted successfully");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.delete(1l);
		assertTrue(resp.getMessage().equals("KeywordRedirect deleted successfully"));
	}
	
	@Test
	public void testNegDeleteRedirect() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = redirectController.delete(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}
	
	@Test
	public void testDeleteException() throws ServiceException, ParseException{
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(redirectService).deleteRedirect(1l);
		MerchandisingBaseResponse<IWrapper> response = redirectController.delete(1l);
		assertTrue(response.getErrorCode().equals("Delete.Error"));
	}
	
	@Test
	public void testLoadRedirects() throws ServiceException{
		when(redirectService.loadRedirects(new PaginationWrapper())).thenReturn(RedirectTestData.getListOfWrappers());
		MerchandisingBaseResponse<IWrapper> resp = redirectController.loadRedirects(new PaginationWrapper());
		assertTrue(resp.getSuccessCode().equals("Listing.Success"));
	}
	
	@Test
	public void testLoadRedirectsException() throws ServiceException{
		PaginationWrapper paginationWrapper = new PaginationWrapper();
		doThrow(ServiceException.class).when(redirectService).loadRedirects(paginationWrapper);
		MerchandisingBaseResponse<IWrapper> resp = redirectController.loadRedirects(paginationWrapper);
		assertTrue(resp.getErrorCode().equals("Listing.Error"));
	}
}
