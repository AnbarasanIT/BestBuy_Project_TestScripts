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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.BannerWrapperConverter;
import com.bestbuy.search.merchandising.common.ModelValidatorAspect;
import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplateMeta;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.BannerService;
import com.bestbuy.search.merchandising.service.BannerTemplateMetaService;
import com.bestbuy.search.merchandising.service.ContextService;
import com.bestbuy.search.merchandising.service.SearchProfileService;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.web.BannerController;
import com.bestbuy.search.merchandising.web.BannerTemplateController;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * @Modified Chanchal.Kumari @ Date-28th Sep 2012.Added test cases for load and delete of banners.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class BannerControllerTest extends AbstractJUnit4SpringContextTests {

	BannerTemplateController bannerTemplateController;
	BannerWrapperConverter bannerWrapperConverter;
	BannerController bannerController;
	BannerTemplateMetaService bannerTemplateService;
	BannerService bannerService;
	BannerWrapper bannerWrapper;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
	ResourceBundle errorMessages;
	ResourceBundle successMessages;
	ResourceBundleHandler resourceBundleHandler;
	GeneralWorkflow generalWorkflow;
	SearchProfileService searchProfileService;
	UsersService usersService;
	StatusService statusService;
	ContextService contextService;
	ModelValidatorAspect modelValidatorAspect = null;
	/*
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerWrapperConverter=new BannerWrapperConverter();
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		bannerTemplateController= new BannerTemplateController();
		bannerTemplateService=mock(BannerTemplateMetaService.class);
		bannerService=mock(BannerService.class);
		bannerTemplateController.setBannerTemplateService(bannerTemplateService);
		bannerController = new BannerController();
		bannerController.setBannerService(bannerService);
		bannerWrapper = new BannerWrapper();
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setSuccessMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		bannerController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		generalWorkflow = mock(GeneralWorkflow.class);
		bannerController.setGeneralWorkflow(generalWorkflow);
		searchProfileService = mock(SearchProfileService.class);
		usersService = mock(UsersService.class);
		statusService = mock(StatusService.class);
		contextService=mock(ContextService.class);
		modelValidatorAspect = mock(ModelValidatorAspect.class);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		//modelValidatorAspect.setValidator(validator);
		//modelValidatorAspect.setMerchandisingBaseResponse(merchandisingBaseResponse);
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		bannerTemplateController=null;
		bannerTemplateService=null;
		searchProfileService = null;
		usersService = null;
		statusService = null;
		bannerService=null;
	}

	@Test
	public void testLoad() throws Exception {
		List<BannerTemplateMeta> template = BaseData.getBannerTemplateList();
		when(bannerTemplateService.retrieveAll()).thenReturn(template);
		bannerTemplateController.loadBannerTemplate();
		assertNotNull(bannerTemplateController.loadBannerTemplate());
	}

	@Test
	public void testLoadException() throws Exception {
		when(bannerTemplateService.retrieveAll()).thenThrow(new ServiceException("Error during Load template"));
		bannerTemplateController.loadBannerTemplate();
	} 

	@Test
	public void testLoadBanners() throws Exception {
		List<Banner> banners = BaseData.getBannerDataList();
		Set<String> status =new HashSet<String>() {{  
			add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		PaginationWrapper paginationWrapper = new PaginationWrapper();
		when(bannerService.loadBanners(paginationWrapper)).thenReturn(banners);
		when(resourceBundleHandler.getSuccessString("Listing.Success", "Banner")).thenReturn("Banner listing was successful");
		when(generalWorkflow.getActionsForStatus("Approved")).thenReturn(status);
		MerchandisingBaseResponse<IWrapper> response = bannerController.loadBanners(paginationWrapper);
		assertTrue(response.getMessage().equals("Banner listing was successful"));
	}

	@Test
	public void testLoadBannersException() throws Exception {
		PaginationWrapper paginationWrapper = new PaginationWrapper();
		when(bannerService.loadBanners(paginationWrapper)).thenThrow(new ServiceException());
		bannerController.loadBanners(paginationWrapper);
	}

	@Test
	public void testDeleteRedirect() throws Exception {

		when(resourceBundleHandler.getSuccessString("Delete.Success", "Banner")).thenReturn("Banner deleted successfully");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.delete(3l);
		assertTrue(resp.getMessage().equals("Banner deleted successfully"));
	}

	@Test
	public void testNegDeleteRedirect() {
		MerchandisingBaseResponse<IWrapper> resp = bannerController.delete(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}

	@Test
	public void testDeleteException() throws Exception {
		doThrow(ServiceException.class).when(bannerService).deleteBanner(3l);
		bannerController.delete(3l);
	} 

	@Test
	public void testCreate() throws ServiceException{
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		SearchProfile searchProfile = BaseData.getSearchProfile();
		Users user = BaseData.getUsers();
		when(searchProfileService.retrieveById(1l)).thenReturn(searchProfile);
		when(usersService.retrieveById("A922998")).thenReturn(user);
		when(resourceBundleHandler.getSuccessString("Create.Success", "Banner")).thenReturn("Banner created successfully");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.create(bannerWrapper);
		assertTrue(resp.getMessage().equals("Banner created successfully"));
	}

	@Test
	public void testNegCreate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.create(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}

	@Test
	public void testCreateException() throws Exception {
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		SearchProfile searchProfile = BaseData.getSearchProfile();
		Users user = BaseData.getUsers();
		Status status = BaseData.getStatus();
		when(searchProfileService.retrieveById(1l)).thenReturn(searchProfile);
		when(usersService.retrieveById("A922998")).thenReturn(user);
		when(statusService.retrieveById(2l)).thenReturn(status);
		doThrow(ServiceException.class).when(bannerService).createBanner(bannerWrapper);
		when(resourceBundleHandler.getErrorString("Create.Error","Banner")).thenReturn("Error while creating the Banner");
		MerchandisingBaseResponse<IWrapper> rep = bannerController.create(bannerWrapper);
		assertTrue(rep.getMessage().equals("Error while creating the Banner"));
	}
	
	@Test
	public void testUpdate() throws ServiceException{
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		SearchProfile searchProfile = BaseData.getSearchProfile();
		Users user = BaseData.getUsers();
		when(searchProfileService.retrieveById(1l)).thenReturn(searchProfile);
		when(usersService.retrieveById("A922998")).thenReturn(user);
		when(resourceBundleHandler.getSuccessString("Update.Success", "Banner")).thenReturn("Banner updated successfully");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.update(bannerWrapper);
		assertTrue(resp.getMessage().equals("Banner updated successfully"));
	}
	
	@Test
	public void testNegUpdate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Error cannot process an empty request");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.update(null);
		assertTrue(resp.getMessage().equals("Error cannot process an empty request"));
	}

	@Test
	public void testUpdateException() throws Exception {
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		SearchProfile searchProfile = BaseData.getSearchProfile();
		Users user = BaseData.getUsers();
		Status status = BaseData.getStatus();
		when(searchProfileService.retrieveById(1l)).thenReturn(searchProfile);
		when(usersService.retrieveById("A922998")).thenReturn(user);
		when(statusService.retrieveById(2l)).thenReturn(status);
		doThrow(ServiceException.class).when(bannerService).updateBanner(bannerWrapper);
		when(resourceBundleHandler.getErrorString("Update.Error","Banner")).thenReturn("Error while updating the Banner");
		MerchandisingBaseResponse<IWrapper> rep = bannerController.update(bannerWrapper);
		assertTrue(rep.getMessage().equals("Error while updating the Banner"));
	}
	
	@Test
	public void testGet() throws ServiceException{
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		Long bannerId=bannerWrapper.getBannerId();
		SearchProfile searchProfile = BaseData.getSearchProfile();
		Users user = BaseData.getUsers();
		when(searchProfileService.retrieveById(1l)).thenReturn(searchProfile);
		when(usersService.retrieveById("A922998")).thenReturn(user);
		when(resourceBundleHandler.getSuccessString("000001", "Banner")).thenReturn("Banner loaded successfully");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.loadeditbanner(bannerId);
		assertTrue(resp.getSuccessCode().equals("Retrive.Success"));
	}
	
	@Test
	public void testNegGet() {
		when(resourceBundleHandler.getErrorString("000002")).thenReturn("Request.NoData");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.loadeditbanner(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}

	@Test
	public void testGetException() throws Exception {
		BannerWrapper bannerWrapper=BaseData.getBannerWrapper();
		SearchProfile searchProfile = BaseData.getSearchProfile();
		Long bannerId=bannerWrapper.getBannerId();
		Users user = BaseData.getUsers();
		Status status = BaseData.getStatus();
		when(searchProfileService.retrieveById(1l)).thenReturn(searchProfile);
		when(usersService.retrieveById("A922998")).thenReturn(user);
		when(statusService.retrieveById(2l)).thenReturn(status);
		doThrow(ServiceException.class).when(bannerService).loadeditbanner(bannerId);
		when(resourceBundleHandler.getErrorString("000003","Banner")).thenReturn("Retrive.Error");
		MerchandisingBaseResponse<IWrapper> rep = bannerController.loadeditbanner(bannerId);
		assertTrue(rep.getErrorCode().equals("Retrive.Error"));
	}
	
	@Test
	public void testDeleteContext() throws Exception {

		when(resourceBundleHandler.getSuccessString("000003", "Banner")).thenReturn("Banner deleted successfully");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.deleteContext(1L);
		assertTrue(resp.getSuccessCode().equals("Delete.Success"));
	}

	@Test
	public void testNegDeleteContext() {
		when(resourceBundleHandler.getErrorString("000002","invalid Request Data")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = bannerController.deleteContext(null);
		assertTrue(resp.getErrorCode().equals("Request.NoData"));
	}
	

	@Test
	public void testDeleteContextException() throws Exception {
		doThrow(ServiceException.class).when(bannerService).deleteBannerContext(1l);
		bannerController.deleteContext(1l);
	} 
}
