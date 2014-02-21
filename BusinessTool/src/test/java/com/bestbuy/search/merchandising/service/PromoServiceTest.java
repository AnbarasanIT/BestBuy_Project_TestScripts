package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DRAFT_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.PromoDAO;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.domain.PromoSku;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.unittest.common.PromoTestData;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * Unit Test Case to test PromoService
 * @author Chanchal.Kumarie
 * Date - 4th Oct 2012
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class PromoServiceTest {
	
	PromoService promoService;
	PromoDAO promoDAO;
	StatusService statusService;
	GeneralWorkflow generalWorkflow;
	PromoSkuService promoSkuService;
	UserUtil userUtil;
	UsersService usersService;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		promoService = new PromoService();
		promoDAO = mock(PromoDAO.class);
		generalWorkflow = mock(GeneralWorkflow.class);
		usersService = mock(UsersService.class);
		statusService = mock(StatusService.class);
		promoSkuService = mock(PromoSkuService.class);
		userUtil = mock(UserUtil.class);
		promoService.setPromoDAO(promoDAO);
		promoService.setDao(promoDAO);
		promoService.setStatusService(statusService);
		promoService.setGeneralWorkflow(generalWorkflow);
		promoService.setUserUtil(userUtil);
		promoService.setUsersService(usersService);
		promoService.setPromoSkuService(promoSkuService);
	}

	@Test
	public void testLoadPromos() throws ServiceException,DataAcessException{
		 List<Promo> expectedResultList = PromoTestData.getPromoList();
		 when(promoDAO.loadPromos(Mockito.any(SearchCriteria.class))).thenReturn(expectedResultList);
		 promoService.loadPromos(new PaginationWrapper());
	}
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testLoadPromosDaoExcep() throws ServiceException, DataAcessException{
		when(promoDAO.loadPromos(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		try{ 
			promoService.loadPromos(new PaginationWrapper());
		}
		catch(ServiceException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testCreatePromo() throws DataAcessException,ServiceException,InvalidStatusException{
		
		Set<String> actions =new HashSet<String>() {{
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		Promo promo = PromoTestData.getPromo();
		Status status = PromoTestData.getStatus();
		Users user = PromoTestData.getUsers();
		List<PromoSku> promoSkus = PromoTestData.getPromoSkus();
		when(statusService.retrieveById(DRAFT_STATUS)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(user);
		when(promoSkuService.save(Mockito.any(PromoSku.class))).thenReturn(promoSkus.get(0));
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(actions);
		when(promoService.save(Mockito.any(Promo.class))).thenReturn(promo);
		IWrapper iWrapper = promoService.createPromo(promoWrapper);
		assertNotNull(iWrapper);
	}
	
	@Test
	public void testUpdatePromo() throws DataAcessException,ServiceException,InvalidStatusException{
		
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		PromoWrapper promoWrapper = PromoTestData.getPromoWrapper();
		Promo promo = PromoTestData.getPromo();
		Status status = PromoTestData.getStatus();
		Users user = PromoTestData.getUsers();
		List<PromoSku> promoSkus = PromoTestData.getPromoSkus();
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(statusService.retrieveById(DRAFT_STATUS)).thenReturn(status);
		when(usersService.retrieveById("A1003132")).thenReturn(user);
		when(userUtil.getUser()).thenReturn(user);
		when(promoSkuService.update(Mockito.any(PromoSku.class))).thenReturn(promoSkus.get(0));
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(actions);
		
		when(promoService.update(Mockito.any(Promo.class))).thenReturn(promo);
		IWrapper iWrapper = promoService.updatePromo(promoWrapper);
		assertNotNull(iWrapper);
	}
	
	
	 @Test
	public void testDeletePromo() throws ServiceException,DataAcessException, WorkflowException{
		
		Status status = PromoTestData.getStatus();
		String statusName  = "Draft";
		Users users = PromoTestData.getUsers();
		Promo promo = PromoTestData.getPromo();
		Long statusId = 2l;
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(statusService.getStatusId(statusName)).thenReturn(statusId);
		when(statusService.retrieveById(statusId)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(promoService.update(Mockito.any(Promo.class))).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), DELETE)).thenReturn(GeneralStatus.DELETED.toString());
		IWrapper iWrapper = promoService.deletePromo(1l);
		assertNotNull(iWrapper);
	}
	 
	@Test(expected = ServiceException.class )
	@SuppressWarnings("unchecked")
	public void testDeletePromoWorkFlowException() throws ServiceException,WorkflowException{
		Promo promo = PromoTestData.getPromo();
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), DELETE)).thenThrow(WorkflowException.class);
		promoService.deletePromo(1l);
	}

	@Test
	public void testApprovePromo() throws ServiceException,DataAcessException, WorkflowException{
		
		Status status = PromoTestData.getStatus();
		String statusName  = "Draft";
		Users users = PromoTestData.getUsers();
		Promo promo = PromoTestData.getPromo();
		Long statusId = 2l;
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(statusService.getStatusId(statusName)).thenReturn(statusId);
		when(statusService.retrieveById(statusId)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(promoService.update(Mockito.any(Promo.class))).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), APPROVE)).thenReturn(GeneralStatus.APPROVED.toString());
		IWrapper iWrapper = promoService.approvePromo(1l);;
		assertNotNull(iWrapper);
	}
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testApprovePromoWorkFlowException() throws ServiceException,WorkflowException{
		Promo promo = PromoTestData.getPromo();
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), APPROVE)).thenThrow(WorkflowException.class);
		promoService.approvePromo(1l);
	}
	
	@Test
	public void testRejectPromo() throws ServiceException,DataAcessException, WorkflowException{
		Status status = PromoTestData.getStatus();
		String statusName  = "Draft";
		Users users = PromoTestData.getUsers();
		Promo promo = PromoTestData.getPromo();
		Long statusId = 2l;
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(statusService.getStatusId(statusName)).thenReturn(statusId);
		when(statusService.retrieveById(statusId)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(promoService.update(Mockito.any(Promo.class))).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), REJECT)).thenReturn(GeneralStatus.REJECTED.toString());
		IWrapper iWrapper = promoService.rejectPromo(1l);
		assertNotNull(iWrapper);
		}
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testRejectPromoWorkFlowException() throws ServiceException,WorkflowException{
		Promo promo = PromoTestData.getPromo();
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), REJECT)).thenThrow(WorkflowException.class);
		promoService.rejectPromo(1l);
	}
	
	@Test
	public void testPublishPromo() throws ServiceException,DataAcessException, WorkflowException{
		Status status = PromoTestData.getStatus();
		String statusName  = "Draft";
		Users users = PromoTestData.getUsers();
		Promo promo = PromoTestData.getPromo();
		Long statusId = 2l;
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(statusService.getStatusId(statusName)).thenReturn(statusId);
		when(statusService.retrieveById(statusId)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(promoService.update(Mockito.any(Promo.class))).thenReturn(promo);
	}
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testPublishPromoWorkFlowException() throws ServiceException,WorkflowException{
		Promo promo = PromoTestData.getPromo();
		when(promoService.retrieveById(1l)).thenReturn(promo);
		when(generalWorkflow.stepForward(promo.getStatus().getStatus(), PUBLISH)).thenThrow(WorkflowException.class);
		promoService.publishPromo(1l, "Draft");
	}
	
	@Test
	public void  testUpdatePromoStatus() throws ServiceException,WorkflowException,DataAcessException{
		Status status = PromoTestData.getStatus();
		String statusName  = "Draft";
		Users users = PromoTestData.getUsers();
		Promo promo = PromoTestData.getPromo();
		Long statusId = 2l;
		Set<String> actions =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(statusService.getStatusId(statusName)).thenReturn(statusId);
		when(statusService.retrieveById(statusId)).thenReturn(status);
		when(userUtil.getUser()).thenReturn(users);
		when(userUtil.getUser()).thenReturn(users);
		when(promoService.update(Mockito.any(Promo.class))).thenReturn(promo);
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(actions);
		IWrapper iWrapper = promoService.updatePromoStatus(promo,"Draft");
		assertNotNull(iWrapper);
		
	} 
	
}
