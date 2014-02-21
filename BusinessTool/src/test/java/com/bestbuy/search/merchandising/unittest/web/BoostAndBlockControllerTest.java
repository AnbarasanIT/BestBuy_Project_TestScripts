package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import junit.framework.Assert;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.ModelValidatorAspect;
import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.BoostAndBlockService;
import com.bestbuy.search.merchandising.service.UsersService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.unittest.common.BoostAndBlockTestHelper;
import com.bestbuy.search.merchandising.web.BoostAndBlockController;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.ResponseStatusEnum;
/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for BoostAndBlock Controller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class BoostAndBlockControllerTest extends AbstractJUnit4SpringContextTests {
	BoostAndBlockController boostAndBlockController = null;
	BoostAndBlockService boostAndBlockService = null;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse = null;
	ResourceBundle errorMessages = null;
	ResourceBundle successMessages = null;
	ResourceBundleHandler resourceBundleHandler = null;
	UsersService usersService;
	ModelValidatorAspect modelValidatorAspect = null;
	//Validator validator = null;
	GeneralWorkflow generalWorkflow = null;
	BoostAndBlockTestHelper helper;
	
	//@Mock private Validator validator; 
	
	 
	/*
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		boostAndBlockService = mock(BoostAndBlockService.class);
		boostAndBlockController = new BoostAndBlockController();
		boostAndBlockController.setBoostAndBlockService(boostAndBlockService);
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		generalWorkflow = mock(GeneralWorkflow.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setErrorMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		boostAndBlockController.setMerchandisingBaseResponse(merchandisingBaseResponse);
		boostAndBlockController.setGeneralWorkflow(generalWorkflow);
		usersService = mock(UsersService.class);
		modelValidatorAspect = mock(ModelValidatorAspect.class);
		//validator = mock(Validator.class);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		modelValidatorAspect.setValidator(validator);
		helper = new BoostAndBlockTestHelper();
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){

		boostAndBlockController = null;
		boostAndBlockService = null;
		usersService = null;
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void testLoadBoostAndBlock() throws Exception {
		List<IWrapper> wrappers = (List<IWrapper>) (List<?>) BaseData.getBoostAndBlockWrappers();
		
		Set<String> status =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(boostAndBlockService.load(new PaginationWrapper())).thenReturn(wrappers);
		when(resourceBundleHandler.getSuccessString("Listing.Success", "BoostAndBlock")).thenReturn("BoostAndBlock listing was successful");
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(status);
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.load(new PaginationWrapper());
		assertTrue(response.getMessage().equals("BoostAndBlock listing was successful"));
	}
	
	@Test
	public void testLoadBoostAndBlocksException() throws Exception {
		when(boostAndBlockService.load(Mockito.any(PaginationWrapper.class))).thenThrow(new ServiceException());
		when(resourceBundleHandler.getErrorString("Listing.Error", "BoostAndBlock")).thenReturn("Error while retrieving the BoostAndBlock list");
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.load(new PaginationWrapper());
		assertTrue(response.getMessage().equals("Error while retrieving the BoostAndBlock list"));
	}

	@Test
	public void testNegCreate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.create(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}

	@Test
	public void testCreate() {
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get((0));
		when(resourceBundleHandler.getSuccessString("Create.Success", "BoostAndBlock")).thenReturn("The BoostAndBlock was created");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.create(boostAndBlockWrapper);
		assertTrue(resp.getSuccessCode().equals("Create.Success"));
	}
	
	class PJPE implements ProceedingJoinPoint {

		@Override
		public Object[] getArgs() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getKind() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Signature getSignature() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SourceLocation getSourceLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StaticPart getStaticPart() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getTarget() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getThis() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String toLongString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String toShortString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object proceed() throws Throwable {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object proceed(Object[] arg0) throws Throwable {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void set$AroundClosure(AroundClosure arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Test
	public void testCreateExcep() throws ServiceException, ParseException, InvalidStatusException {
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get((0));
		doThrow(ServiceException.class).when(boostAndBlockService).createBoostAndBlock(boostAndBlockWrapper);
		when(resourceBundleHandler.getSuccessString("Create.Error", "BoostAndBlock")).thenReturn("The BoostAndBlock was not created");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.create(boostAndBlockWrapper);
		assertTrue(resp.getErrorCode().equals("Create.Error"));
	}
	@Test
	public void testNegUpdate() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.update(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}

	@Test
	public void testUpdate() {
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get((0));
		when(resourceBundleHandler.getSuccessString("Update.Success", "BoostAndBlock")).thenReturn("The BoostAndBlock is updated");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.update(boostAndBlockWrapper);
		assertTrue(resp.getSuccessCode().equals("Update.Success"));
	}

	@Test
	public void testUpdateExcep() throws ServiceException, ParseException, DataAcessException, InvalidStatusException,WorkflowException {
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get((0));
		doThrow(ServiceException.class).when(boostAndBlockService).updateBoostAndBlock(boostAndBlockWrapper);
		when(resourceBundleHandler.getSuccessString("Update.Error", "BoostAndBlock")).thenReturn("The BoostAndBlock was not updated");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.update(boostAndBlockWrapper);
		assertTrue(resp.getErrorCode().equals("Update.Error"));
	}
	
	@Test
	public void testloadEditBoostBlock() throws ServiceException{
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get((0));
		Long boostBlockId = boostAndBlockWrapper.getBoostBlockId();
		when(resourceBundleHandler.getSuccessString("Retrive.Success", "BoostAndBlock")).thenReturn("The BoostAndBlock is loaded");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.loadEditBoostBlock(boostBlockId);
		assertTrue(resp.getSuccessCode().equals("Retrive.Success"));
	}
	
	@Test
	public void testNegloadEditBoostBlock() {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.loadEditBoostBlock(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testloadEditBoostBlockExcep() throws ServiceException, ParseException, DataAcessException {
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get((0));
		Long boostBlockId = boostAndBlockWrapper.getBoostBlockId();
		doThrow(ServiceException.class).when(boostAndBlockService).loadEditBoostBlockData(boostBlockId);
		when(resourceBundleHandler.getSuccessString("Retrive.Error", "BoostAndBlock")).thenReturn("The BoostAndBlock was not loaded");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.loadEditBoostBlock(boostBlockId);
		assertTrue(resp.getErrorCode().equals("Retrive.Error"));
	}
	
	@Test
	public void testDelete() throws Exception{
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockDeletedWrappers();
		when(boostAndBlockService.deleteBoostAndBlock(123456789L)).thenReturn(boostAndBlockWrapper);
		when(resourceBundleHandler.getSuccessString("Delete.Success", "BoostAndBlock")).thenReturn("BoostAndBlock deleted successfully");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.delete(123456789L);
		assertTrue(resp.getMessage().equals("BoostAndBlock deleted successfully"));
	}
	
	@Test
	public void testDeleteNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.delete(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testDeleteException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(boostAndBlockService).deleteBoostAndBlock(123456789L);
		when(resourceBundleHandler.getErrorString("Delete.Error", "BoostAndBlock")).thenReturn("Error while trying to delete the BoostAndBlock");
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.delete(123456789L);
		assertTrue(response.getErrorCode().equals("Delete.Error"));
	}
	
	
	@Test
	public void testapprove() throws Exception{
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockApprovedWrappers();
		when(boostAndBlockService.approveBoostAndBlock(123456789L)).thenReturn(boostAndBlockWrapper);
		when(resourceBundleHandler.getSuccessString("Approve.Success", "BoostAndBlock")).thenReturn("BoostAndBlock approved successfully");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.approve(123456789L);
		assertTrue(resp.getMessage().equals("BoostAndBlock approved successfully"));
	}
	
	@Test
	public void testApproveNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.approve(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testApproveException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(boostAndBlockService).approveBoostAndBlock(123456789L);
		when(resourceBundleHandler.getErrorString("Approve.Error", "BoostAndBlock")).thenReturn("Error while trying to approve the BoostAndBlock");
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.approve(123456789L);
		assertTrue(response.getErrorCode().equals("Approve.Error"));
	}
	
	
	@Test
	public void testreject() throws Exception{
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockRejectedWrappers();
		when(boostAndBlockService.rejectBoostAndBlock(123456789L)).thenReturn(boostAndBlockWrapper);
		when(resourceBundleHandler.getSuccessString("Reject.Success", "BoostAndBlock")).thenReturn("BoostAndBlock rejected successfully");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.reject(123456789L);
		assertTrue(resp.getMessage().equals("BoostAndBlock rejected successfully"));
	}
	
	@Test
	public void testRejectNeg() throws Exception{
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> resp = boostAndBlockController.reject(null);
		assertTrue(resp.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testRejectException() throws Exception {
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(boostAndBlockService).rejectBoostAndBlock(123456789L);
		when(resourceBundleHandler.getErrorString("Reject.Error", "BoostAndBlock")).thenReturn("Error while trying to approve the BoostAndBlock");
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.reject(123456789L);
		assertTrue(response.getErrorCode().equals("Reject.Error"));
	}
	
	//////// Validate new boost and block
	/**
	 * 
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	@Test
	public void testValidateNewBoostAndBlock() throws ServiceException {
		String searchTerm = helper.getSearchTerm(), 
				categoryId = helper.getCategoryId();
		Long searchProfileId = helper.getSearchProfileId();
		
		// normal scenario
		when(boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId)).thenReturn(0);
		when(resourceBundleHandler.getSuccessString(Mockito.anyString())).thenReturn("any message");
		when(resourceBundleHandler.getErrorString(Mockito.anyString())).thenReturn("any message");
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		
		Assert.assertNotNull("Response was null", response);
		Assert.assertNotNull("The data object is null", response.getData());
		
		Boolean valid = reflectValid(response);

		Assert.assertTrue("response.data.valid=true was expected", valid);
		Assert.assertEquals("SUCCESS response status was expected", ResponseStatusEnum.SUCCESS, response.getStatus());
		
		// first conditional
		/// first branch
		searchTerm = null;	
		response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		Assert.assertNotNull("Response was null", response);
		Assert.assertNull("Null response.data was expected", response.getData());
		Assert.assertEquals("ERROR response status was expected", ResponseStatusEnum.ERROR, response.getStatus());
		
		/// second branch
		searchTerm = "Term";
		categoryId = null;
		response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		Assert.assertNotNull("Response was null", response);
		Assert.assertNull("Null response.data was expected", response.getData());
		Assert.assertEquals("ERROR response status was expected", ResponseStatusEnum.ERROR, response.getStatus());
		
		/// third branch
		searchTerm = "Term";
		categoryId = "cat1";
		searchProfileId = null;
		response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		Assert.assertNotNull("Response was null", response);
		Assert.assertNull("Null response.data was expected", response.getData());
		Assert.assertEquals("ERROR response status was expected", ResponseStatusEnum.ERROR, response.getStatus());
		
		// second and third conditionals
		searchTerm = "Term";
		categoryId = "cat1";
		searchProfileId = 1L;
		when(boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId)).thenReturn(1);
		when(resourceBundleHandler.getErrorString("BoostAndBlock.NewValidationUniqueInvalid")).thenReturn("message");
		response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		
		Assert.assertNotNull("Response was null", response);
		Assert.assertEquals("Error code: BoostAndBlock.NewValidationUniqueInvalid was expected", response.getErrorCode(), "BoostAndBlock.NewValidationUniqueInvalid");
		
		when(boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId)).thenReturn(2);
		when(resourceBundleHandler.getErrorString("BoostAndBlock.NewValidationPromoInvalid")).thenReturn("message");
		response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		
		Assert.assertNotNull("Response was null", response);
		Assert.assertEquals("Error code: BoostAndBlock.NewValidationPromoInvalid was expected", response.getErrorCode(), "BoostAndBlock.NewValidationPromoInvalid");		
	}	
	
	/**
	 * 
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testValidateNewBoostAndBlockException() throws ServiceException {
		String searchTerm = helper.getSearchTerm(), 
				categoryId = helper.getCategoryId();
		Long searchProfileId = helper.getSearchProfileId();
		
		when(boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId)).thenThrow(ServiceException.class);
		MerchandisingBaseResponse<IWrapper> response = boostAndBlockController.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		
		Assert.assertNotNull("Response was null", response);
		Assert.assertNull("Null response.data was expected", response.getData());
		Assert.assertEquals("ERROR response status was expected", ResponseStatusEnum.ERROR, response.getStatus());
		Assert.assertEquals("Error code: BoostAndBlock.NewValidationError was expected", response.getErrorCode(), "BoostAndBlock.NewValidationError");		
	}
	
	/**
	 * 
	 * @param response
	 * @return
	 * @author Ramiro.Serrato
	 */
	private Boolean reflectValid(MerchandisingBaseResponse<IWrapper> response) {
		Boolean valid = null;
		
		try {
			IWrapper wrapper =  (IWrapper) boostAndBlockController.getClass().getDeclaredClasses()[0].cast(response.getData());
			valid = (Boolean) wrapper.getClass().getMethod("getValid").invoke(wrapper);
		} 
		
		catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}	
		
		return valid;
	}	
}
