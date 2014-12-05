package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DRAFT_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.BoostAndBlockWrapperConverter;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.dao.BoostAndBlockDao;
import com.bestbuy.search.merchandising.dao.PromoDAO;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.unittest.common.BoostAndBlockTestHelper;
import com.bestbuy.search.merchandising.unittest.common.PromoTestData;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.BoostBlockProductWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test cases for BoostAndBlockService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class BoostAndBlockServiceTest {

	BoostAndBlockService  boostAndBlockService= null;
	BaseDAO<Long,Facet> baseDao; 
	BoostAndBlockDao boostAndBlockDAO = null;
	GeneralWorkflow generalWorkflow= null;
	StatusService statusService=null;
	UsersService usersService=null;
	BoostAndBlockWrapperConverter boostAndBlockWrapperConverter = null;
	UserUtil userUtil = null;
	BoostAndBlockProductService boostAndBlockProductService= null;
	CategoryNodeService categoryNodeService = null;
	SearchProfileService searchProfileService = null;
	BoostAndBlockWrapper boostAndBlockWrapper = null;
	BoostAndBlockTestHelper helper;
	
    PromoDAO promoDAOMock;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		boostAndBlockService = new BoostAndBlockService();
		boostAndBlockDAO = mock(BoostAndBlockDao.class);
		promoDAOMock = mock(PromoDAO.class);
		statusService = mock(StatusService.class);
		generalWorkflow = mock(GeneralWorkflow.class);
		boostAndBlockWrapperConverter = mock(BoostAndBlockWrapperConverter.class);
		userUtil = mock(UserUtil.class);
		boostAndBlockProductService=mock(BoostAndBlockProductService.class);
		boostAndBlockService.setBaseDAO(boostAndBlockDAO);
		boostAndBlockService.setBoostAndBlockWrapperConverter(boostAndBlockWrapperConverter);
		boostAndBlockService.setBoostAndBlockDAO(boostAndBlockDAO);
		boostAndBlockService.setDao(boostAndBlockDAO);
		boostAndBlockService.setGeneralWorkflow(generalWorkflow);
		boostAndBlockService.setStatusService(statusService);
		boostAndBlockService.setUserUtil(userUtil);
		boostAndBlockService.setBoostAndBlockProductService(boostAndBlockProductService);
		categoryNodeService= mock(CategoryNodeService.class);
		searchProfileService=mock(SearchProfileService.class);
		boostAndBlockWrapper=mock(BoostAndBlockWrapper.class);
		helper = new BoostAndBlockTestHelper();
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		statusService = null;
		boostAndBlockDAO=null; 
		boostAndBlockService=null;
		boostAndBlockWrapperConverter=null;
		userUtil=null;
		categoryNodeService=null;
		searchProfileService=null;
		boostAndBlockWrapper=null;
	}

	@Test 
	public void testCreateBoostBlock() throws ServiceException, ParseException, InvalidStatusException{
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get(0);
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		@SuppressWarnings("serial")
		Set<String> actions =new HashSet<String>() {{
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		Status status = PromoTestData.getStatus();
		when(statusService.retrieveById(DRAFT_STATUS)).thenReturn(status);
		when(boostAndBlockWrapperConverter.wrapperConverter((Mockito.any(BoostAndBlockWrapper.class)), (Mockito.any(BoostAndBlock.class)))).thenReturn(boostAndBlock);
		when(boostAndBlockService.save(Mockito.any(BoostAndBlock.class))).thenReturn(boostAndBlock);
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(actions);
		boostAndBlockService.createBoostAndBlock(boostAndBlockWrapper);
		boostAndBlockWrapper.setBlockProduct(null);
		boostAndBlockWrapper.setBoostProduct(null);
		boostAndBlockService.createBoostAndBlock(boostAndBlockWrapper);
		boostAndBlockWrapper.setBlockProduct(new ArrayList<BoostBlockProductWrapper>());
		boostAndBlockWrapper.setBoostProduct(new ArrayList<BoostBlockProductWrapper>());
		boostAndBlockService.createBoostAndBlock(boostAndBlockWrapper);
	}

	@Test
	public void testBoostBlockWrapper() throws ServiceException, ParseException, DataAcessException {
		setData();
		//Get the BoostBlock wrapper
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get(0);
		//Loading the user for retrieveById
		Users user = BaseData.getUsers();
		when(userUtil.getUser()).thenReturn(user);
		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);
		boostAndBlockWrapperConverter.wrapperConverter(boostAndBlockWrapper, new BoostAndBlock());
		boostAndBlockWrapperConverter.wrapperConverter(null, new BoostAndBlock());
		boostAndBlockWrapper = BaseData.getBoostAndBlockNullWrappers().get(0);
		boostAndBlockWrapper.setStatusId(1L);
		BoostAndBlock boostAndBlock1 = BaseData.getBoostAndBlock();
		boostAndBlock1.setCreatedBy(user);
		boostAndBlockWrapperConverter.wrapperConverter(boostAndBlockWrapper, boostAndBlock1);
	}

	@Test
	public void testGetProducts() throws ServiceException{
		setData();
		List<BoostBlockProductWrapper> productWrappers =BaseData.getBoostBlockProductWrapper();
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		boostAndBlockWrapperConverter.getProducts(productWrappers, boostAndBlock);
		//NUll Check
		List<BoostBlockProductWrapper> productWrapper =BaseData.getBoostBlockProductNullWrapper();
		boostAndBlockWrapperConverter.getProducts(productWrapper, boostAndBlock);
	}

	public void setData() throws ServiceException{
		//createBoostBlockWrapperConverter object
		boostAndBlockWrapperConverter = new BoostAndBlockWrapperConverter();
		//boostAndBlockWrapperConverter.setStatusService(statusService);
		boostAndBlockWrapperConverter.setUserUtil(userUtil);
		boostAndBlockWrapperConverter.setCategoryNodeService(categoryNodeService);
		boostAndBlockWrapperConverter.setSearchProfileService(searchProfileService);
	}
	
	@Test
	public void testLoadEditBB() throws ServiceException{
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		Long boostAndBlockID = boostAndBlock.getId();
		when(boostAndBlockService.retrieveById(boostAndBlockID)).thenReturn(boostAndBlock);
		boostAndBlockService.loadEditBoostBlockData(boostAndBlockID);

	}
	
	@Test
	public void testLoadEditBlock() throws ServiceException{
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlockList().get(0);
		Long boostAndBlockID = boostAndBlock.getId();
		when(boostAndBlockService.retrieveById(boostAndBlockID)).thenReturn(boostAndBlock);
		boostAndBlockService.loadEditBoostBlockData(boostAndBlockID);

	}
	
	@Test
	public void testLoadEditNullCheck() throws ServiceException{
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlockList().get(0);
		Long boostAndBlockID = boostAndBlock.getId();
		boostAndBlock.setBoostAndBlockProducts(null);
		when(boostAndBlockService.retrieveById(boostAndBlockID)).thenReturn(boostAndBlock);
		boostAndBlockService.loadEditBoostBlockData(boostAndBlockID);

	}
	
	@Test 
	public void testUpdateBoostBlock() throws ServiceException, ParseException, InvalidStatusException, DataAcessException,WorkflowException{
		BoostAndBlockWrapper boostAndBlockWrapper = BaseData.getBoostAndBlockWrappers().get(0);
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		@SuppressWarnings("serial")
		Set<String> actions =new HashSet<String>() {{
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(boostAndBlockDAO.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(boostAndBlockDAO.retrieveById(1L)).thenReturn(boostAndBlock);
		when(boostAndBlockWrapperConverter.wrapperConverter((Mockito.any(BoostAndBlockWrapper.class)), (Mockito.any(BoostAndBlock.class)))).thenReturn(boostAndBlock);
		when(boostAndBlockService.update(Mockito.any(BoostAndBlock.class))).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward("Draft", EDIT)).thenReturn("Draft");
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(actions);
		when(statusService.getStatusId("Draft")).thenReturn(2L);
		when(statusService.retrieveById(DRAFT_STATUS)).thenReturn(BaseData.getStatus());
		boostAndBlockService.updateBoostAndBlock(boostAndBlockWrapper);
		/*boostAndBlockWrapper.setBlockProduct(null);
		boostAndBlockWrapper.setBoostProduct(null);
		boostAndBlockService.updateBoostAndBlock(boostAndBlockWrapper);
		boostAndBlockWrapper.setBlockProduct(new ArrayList<BoostBlockProductWrapper>());
		boostAndBlockWrapper.setBoostProduct(new ArrayList<BoostBlockProductWrapper>());
		boostAndBlockService.updateBoostAndBlock(boostAndBlockWrapper);
		boostAndBlock=BaseData.getNullBoostAndBlocks().get(0);
		when(boostAndBlockService.update(Mockito.any(BoostAndBlock.class))).thenReturn(boostAndBlock);
		boostAndBlockService.updateBoostAndBlock(boostAndBlockWrapper);*/
	}
	
	@Test
	public void testUpdateProducts() throws DataAcessException, ServiceException, ParseException{
		setData();
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlocks().get(0);
		List<BoostBlockProductWrapper> boostBlockProductWrappers=BaseData.getBoostBlockProductWrapper();
		boostAndBlockWrapperConverter.updateProducts(boostAndBlock, boostBlockProductWrappers);
		boostAndBlock=BaseData.getBoostAndBlockList().get(0);
		boostAndBlockWrapperConverter.updateProducts(boostAndBlock, boostBlockProductWrappers);
		boostBlockProductWrappers=BaseData.getBoostBlockProductWrappers();
		boostAndBlock=BaseData.getBoostAndBlockList().get(0);
		boostAndBlockWrapperConverter.updateProducts(boostAndBlock, boostBlockProductWrappers);
	}
	
	@Test
	public void testLoad() throws ServiceException,DataAcessException{
		 List<BoostAndBlock> expectedResultList = BaseData.getBoostAndBlocks();
		 when(boostAndBlockDAO.retrieveBoostsAndBlocks(Mockito.any(SearchCriteria.class))).thenReturn(expectedResultList);
		 List<IWrapper> wrappers = boostAndBlockService.load(new PaginationWrapper());
		 assertNotNull(wrappers);
	}
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testLoadExcep() throws ServiceException, DataAcessException{
		when(boostAndBlockDAO.retrieveBoostsAndBlocks(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		try{ 
			boostAndBlockService.load(new PaginationWrapper());
		}
		catch(ServiceException e){
			//e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testDeleteBoostBlock() throws ServiceException,WorkflowException{
		
		BoostAndBlock boostAndBlock = BaseData.getDeleteBoostAndBlock();
		Status status = BaseData.getDeleteStatus();
		Users users = BaseData.getUsers();
		when(boostAndBlockService.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), DELETE)).thenReturn(GeneralStatus.DELETED.toString());
		when(statusService.getStatusId(GeneralStatus.DELETED.toString())).thenReturn(7l);
		when(statusService.retrieveById(7l)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(boostAndBlockService.update(Mockito.any(BoostAndBlock.class))).thenReturn(boostAndBlock);
		
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
	
	
		when(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())).thenReturn(actions);
		IWrapper iWrapper = boostAndBlockService.deleteBoostAndBlock(123456789L);
		assertNotNull(iWrapper);
	}
	
	
	@Test(expected = ServiceException.class )
	@SuppressWarnings("unchecked")
	public void testDeleteBoostBlockWorkFlowException() throws ServiceException,WorkflowException{
		BoostAndBlock boostAndBlock = BaseData.getDeleteBoostAndBlock();
		when(boostAndBlockService.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), DELETE)).thenThrow(WorkflowException.class);
		boostAndBlockService.deleteBoostAndBlock(123456789L);
	}
	
	
	@Test
	public void testApproveBoostBlock() throws ServiceException,WorkflowException{
		
		BoostAndBlock boostAndBlock = BaseData.getApproveBoostAndBlock();
		Status status = BaseData.getApproveStatus();
		Users users = BaseData.getUsers();
		when(boostAndBlockService.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), APPROVE)).thenReturn(GeneralStatus.APPROVED.toString());
		when(statusService.getStatusId(GeneralStatus.APPROVED.toString())).thenReturn(3l);
		when(statusService.retrieveById(3l)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(boostAndBlockService.update(Mockito.any(BoostAndBlock.class))).thenReturn(boostAndBlock);
		
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
	
	
		when(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())).thenReturn(actions);
		IWrapper iWrapper = boostAndBlockService.approveBoostAndBlock(123456789L);
		assertNotNull(iWrapper);
	}
	
	
	@Test(expected = ServiceException.class )
	@SuppressWarnings("unchecked")
	public void testApproveBoostBlockWorkFlowException() throws ServiceException,WorkflowException{
		BoostAndBlock boostAndBlock = BaseData.getApproveBoostAndBlock();
		when(boostAndBlockService.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), APPROVE)).thenThrow(WorkflowException.class);
		boostAndBlockService.approveBoostAndBlock(123456789L);
	}
	
	@Test
	public void testRejectBoostBlock() throws ServiceException,WorkflowException{
		
		BoostAndBlock boostAndBlock = BaseData.getRejectBoostAndBlock();
		Status status = BaseData.getRejectStatus();
		Users users = BaseData.getUsers();
		when(boostAndBlockService.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), REJECT)).thenReturn(GeneralStatus.REJECTED.toString());
		when(statusService.getStatusId(GeneralStatus.REJECTED.toString())).thenReturn(3l);
		when(statusService.retrieveById(3l)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(boostAndBlockService.update(Mockito.any(BoostAndBlock.class))).thenReturn(boostAndBlock);
		
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
	
	
		when(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())).thenReturn(actions);
		IWrapper iWrapper = boostAndBlockService.rejectBoostAndBlock(123456789L);
		assertNotNull(iWrapper);
	}
	
	
	@Test(expected = ServiceException.class )
	@SuppressWarnings("unchecked")
	public void testRejectBoostBlockWorkFlowException() throws ServiceException,WorkflowException{
		BoostAndBlock boostAndBlock = BaseData.getRejectBoostAndBlock();
		when(boostAndBlockService.retrieveById(123456789L)).thenReturn(boostAndBlock);
		when(generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), REJECT)).thenThrow(WorkflowException.class);
		boostAndBlockService.rejectBoostAndBlock(123456789L);
	}
	
	@Test
	public void testUpdateBloostBlockStatus() throws ServiceException{
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
		Status status = BaseData.getDeleteStatus();
		when(statusService.getStatusId(GeneralStatus.DELETED.toString())).thenReturn(7l);
		when(statusService.retrieveById(7l)).thenReturn(status);
		boostAndBlockService.updateBloostBlockStatus(boostAndBlock,"Deleted");
		assertTrue(boostAndBlock.getStatus().getStatusId()==7l);
	}
	
	@Test
	public void testUpdateBloostBlockStatusNeg() throws ServiceException{
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
		Status status = BaseData.getStatus();
		when(statusService.getStatusId(GeneralStatus.DRAFT.toString())).thenReturn(2l);
		when(statusService.retrieveById(2l)).thenReturn(status);
		boostAndBlockService.updateBloostBlockStatus(boostAndBlock,null);
		assertTrue(boostAndBlock.getStatus().getStatusId()==2l);
	}
	
	@Test(expected = ServiceException.class )
	public void testUpdateBloostBlockStatusServiceException() throws ServiceException,WorkflowException{
		BoostAndBlock boostAndBlock = BaseData.getBoostAndBlock();
		when(statusService.getStatusId("Deleted")).thenThrow(new ServiceException("Error while changing the status for the synonym", new ServiceException()));
		boostAndBlockService.updateBloostBlockStatus(boostAndBlock,"Deleted");
		
	}
	
	@Test(expected = ServiceException.class )
	public void testDeleteBoostBlockServiceException1() throws ServiceException,WorkflowException{
		BoostAndBlock boostAndBlock = BaseData.getDeleteBoostAndBlock();
		when(statusService.getStatusId(boostAndBlock.getStatus().getStatus())).thenThrow(new ServiceException("Error while changing the status for the synonym", new ServiceException()));
		boostAndBlockService.updateBloostBlockStatus(boostAndBlock,"Deleted");
	}
	
	@Test
	public void testGetIwrapper() throws ServiceException,InvalidStatusException{
		BoostAndBlock boostAndBlock = BaseData.getDeleteBoostAndBlock();
		@SuppressWarnings("serial")
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())).thenReturn(actions);
		IWrapper wrapper = boostAndBlockService.getIwrapper(boostAndBlock);
		assertNotNull(wrapper);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = ServiceException.class )
	public void testGetIwrapperException() throws ServiceException,InvalidStatusException{
		BoostAndBlock boostAndBlock = BaseData.getDeleteBoostAndBlock();
		when(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())).thenThrow(InvalidStatusException.class);
		boostAndBlockService.getIwrapper(boostAndBlock);
	}
	
	//////// Validate new boost and block
	/**
	 * 
	 * @throws DataAcessException
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	@Test
	public void testValidateNewBoostAndBlock() throws DataAcessException, ServiceException {
		String searchTerm = helper.getSearchTerm(), 
				categoryId = helper.getCategoryId();
		Long searchProfileId = helper.getSearchProfileId();		
		
		SearchCriteria criteria = new SearchCriteria(), criteria1 = new SearchCriteria();
		
		Map<String,Object> eqCriteria = criteria.getSearchTerms();
		Map<String,Object> notEqCriteria = criteria.getNotEqCriteria(); 
		Map<String,Object> ignoreCaseCriteria = criteria.getSearchTermsIgnoreCase();
		ignoreCaseCriteria.put("obj.term", searchTerm.toUpperCase());
		eqCriteria.put("obj.searchProfile.searchProfileId", searchProfileId);
		eqCriteria.put("obj.category.categoryNodeId", categoryId);
		notEqCriteria.put("obj.status.statusId", DELETE_STATUS);
		
		Map<String,Object> ignoreCaseCriteria1 = criteria1.getSearchTermsIgnoreCase();
		Map<String,Object> notEqCriteria1 = criteria1.getNotEqCriteria(); 
		ignoreCaseCriteria1.put("obj.promoName", searchTerm.toUpperCase());
		notEqCriteria1.put("obj.status.statusId", DELETE_STATUS);
		
		// first conditional
		when(boostAndBlockDAO.getCount(criteria)).thenReturn(0L);
		Integer result = boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		Assert.assertTrue("Result = 0 expected", result==0);
		
		when(boostAndBlockDAO.getCount(criteria)).thenReturn(1L);
		result = boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		Assert.assertTrue("Result = 1 expected", result==1);
		
		// second conditional
		when(boostAndBlockDAO.getCount(criteria)).thenReturn(0L);
		when(promoDAOMock.getCount(criteria1)).thenReturn(0L);
		result = boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		Assert.assertTrue("Result = 0 expected", result==0);
	}
	
	/**
	 * 
	 * @throws DataAcessException
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	@SuppressWarnings("unchecked")
	@Test//(expected=ServiceException.class)
	public void testValidateNewBoostAndBlockDataAcessException() throws DataAcessException, ServiceException {
		String searchTerm = helper.getSearchTerm(), 
				categoryId = helper.getCategoryId();
		Long searchProfileId = helper.getSearchProfileId();			
		
		when(boostAndBlockDAO.getCount(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		try{
			boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		}
		
		catch(Exception e) {
			Assert.assertTrue("Service exception was expected but was " + e.toString(), e instanceof ServiceException);
		}
		
		Mockito.reset(boostAndBlockDAO);
		when(boostAndBlockDAO.getCount(Mockito.any(SearchCriteria.class))).thenReturn(0L);
		when(promoDAOMock.getCount(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		try {
			boostAndBlockService.validateNewBoostAndBlock(searchTerm, searchProfileId, categoryId);
		}
		
		catch(Exception e) {
			Assert.assertTrue("Service exception was expected but was " + e.toString(), e instanceof ServiceException);
		}

	}
}
