package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.FacetWrapperConverter;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.dao.FacetDAO;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.CategoryWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Unit test case for Facet Service
 * @author Kalaiselvi Jaganathan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class FacetServiceTest {

	FacetService  facetService= null;
	CategoryFacetService categoryFacetService;
	BaseDAO<Long,Facet> baseDao; 
	FacetDAO facetDAO = null;
	GeneralWorkflow generalWorkflow= null;
	StatusService statusService=null;
	UsersService usersService=null;
	FacetWrapperConverter facetWrapperConverter = null;
	AttributeService attributeService = null;
	UserUtil userUtil = null;
	AttributeValueService attrValueService=null;
	CategoryNodeService categoryNodeService= null;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		facetService = new FacetService();
		categoryFacetService = mock(CategoryFacetService.class);
		facetDAO = mock(FacetDAO.class);
		statusService = mock(StatusService.class);
		generalWorkflow = mock(GeneralWorkflow.class);
		facetWrapperConverter = mock(FacetWrapperConverter.class);
		attributeService = mock(AttributeService.class);
		userUtil = mock(UserUtil.class);
		//facetService= mock(FacetService.class);
		facetService.setDao(facetDAO);
		facetService.setFacetDAO(facetDAO);
		facetService.setBaseDAO(facetDAO);
		facetService.setGeneralWorkflow(generalWorkflow);
		facetService.setStatusService(statusService);
		facetService.setFacetWrapperConverter(facetWrapperConverter);
		facetService.setAttributeService(attributeService);
		facetService.setUserUtil(userUtil);
		facetService.setCategoryFacetService(categoryFacetService);
		attrValueService=mock(AttributeValueService.class);
		categoryNodeService=mock(CategoryNodeService.class);
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		statusService = null;
		facetDAO=null; 
		facetService=null;
		facetWrapperConverter=null;
		attributeService=null;
		userUtil=null;
	}

	@Test
	public void testLoadFacets() throws DataAcessException, InvalidStatusException, ServiceException{
		List<Facet> facets = BaseData.getFacets();
		Facet facet = BaseData.getFacets().get(0);
		Attribute attribute = BaseData.getAttribute();
		@SuppressWarnings("serial")
		Set<String> status =new HashSet<String>() {{  
			add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		when(facetService.loadEntitiesWithCriteria(Mockito.any(SearchCriteria.class))).thenReturn(facets);
		when(generalWorkflow.getActionsForStatus("Draft")).thenReturn(status);
		when(attributeService.retrieveById(facet.getAttribute().getAttributeId())).thenReturn(attribute);
		List<FacetWrapper> wrappers = facetService.loadFacets(new PaginationWrapper());
		assertNotNull(wrappers);

		//Null Check
		Facet facet1 = BaseData.getFacets().get(0);
		facet1.setAttribute(null);
		facet1.setStatus(null);
		facet1.setCategoryFacet(null);
		List<Facet> facets1 = new ArrayList<Facet>();
		facets1.add(facet1);
		when(facetService.loadEntitiesWithCriteria(Mockito.any(SearchCriteria.class))).thenReturn(facets1);
		List<FacetWrapper> wrapper = facetService.loadFacets(new PaginationWrapper());
		assertNotNull(wrapper);
		Facet facet3 = BaseData.getNullFacet();
		List<Facet> facets2 = new ArrayList<Facet>();
		facets2.add(facet3);
		when(facetService.loadEntitiesWithCriteria(Mockito.any(SearchCriteria.class))).thenReturn(facets2);
		List<FacetWrapper> wrappers1 = facetService.loadFacets(new PaginationWrapper());
		assertNotNull(wrappers1);
		facet3.setCategoryFacet(BaseData.getNullCategoryFacet());
		List<Facet> facets4 = new ArrayList<Facet>();
		facets4.add(facet3);
		when(facetService.loadEntitiesWithCriteria(Mockito.any(SearchCriteria.class))).thenReturn(facets4);
		List<FacetWrapper> wrappers2 = facetService.loadFacets(new PaginationWrapper());
		assertNotNull(wrappers2);
	}

	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testLoadFacetsDaoExcep() throws ServiceException, DataAcessException{
		when(facetService.loadEntitiesWithCriteria(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		facetService.loadFacets(new PaginationWrapper());
	}

	@Test(expected = InvalidStatusException.class)
	@SuppressWarnings("unchecked")
	public void testLoadFacetsExcep() throws ServiceException, InvalidStatusException, DataAcessException{
		when(facetService.loadEntitiesWithCriteria(Mockito.any(SearchCriteria.class))).thenThrow(InvalidStatusException.class);
		 facetService.loadFacets(new PaginationWrapper());
	}

	@Test
	public void testDeleteFacet() throws ServiceException,DataAcessException, WorkflowException{

		Facet facet = BaseData.getFacet();
		Status status = BaseData.getStatus();
		Long statusId = 2L;
		Users users = BaseData.getUsers();
		when(userUtil.getUser()).thenReturn(users);
		when(facetService.retrieveById(1l)).thenReturn(facet);
		when(statusService.getStatusId(status.getStatus())).thenReturn(statusId);
		when(statusService.retrieveById(statusId)).thenReturn(status);
		when(generalWorkflow.stepForward(facet.getStatus().getStatus(), DELETE)).thenReturn(GeneralStatus.DELETED.toString());
		doNothing().when(categoryFacetService).invalidateContextsForFacet(facet);
		facetService.deleteFacet(1l);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = ServiceException.class )
	public void testDeleteFacetWorkFlowException() throws ServiceException,WorkflowException, DataAcessException{
		Facet facet = BaseData.getFacet();
		Users users = BaseData.getUsers();
		when(userUtil.getUser()).thenReturn(users);
		when(facetService.retrieveById(1l)).thenReturn(facet);
		when(generalWorkflow.stepForward(facet.getStatus().getStatus(), DELETE)).thenThrow(WorkflowException.class);
		doNothing().when(categoryFacetService).invalidateContextsForFacet(facet);
		facetService.deleteFacet(1l);
	}

	@Test
	public void testApproveFacet() throws ServiceException,DataAcessException, WorkflowException{

		Facet facet = BaseData.getFacet();
		Users users = BaseData.getUsers();
		when(userUtil.getUser()).thenReturn(users);
		when(facetService.retrieveById(1l)).thenReturn(facet);
		when(generalWorkflow.stepForward(facet.getStatus().getStatus(), APPROVE)).thenReturn(GeneralStatus.APPROVED.toString());
		facetService.approveFacet(1l);
	}

	@Test(expected = ServiceException.class )
	@SuppressWarnings("unchecked")
	public void testApproveFacetException() throws ServiceException, DataAcessException{
		when(facetService.retrieveById(1l)).thenThrow(ServiceException.class);
		facetService.approveFacet(1l);
	}

	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testApproveFacetWorkFlowException() throws ServiceException,WorkflowException, DataAcessException{
		Facet facet = BaseData.getFacet();
		when(facetService.retrieveById(1l)).thenReturn(facet);
		when(generalWorkflow.stepForward(facet.getStatus().getStatus(), APPROVE)).thenThrow(WorkflowException.class);
		facetService.approveFacet(1l);
	}

	@Test
	public void testRejectFacet() throws ServiceException,DataAcessException, WorkflowException{
		Facet facet = BaseData.getFacet();
		when(facetService.retrieveById(1l)).thenReturn(facet);
		when(generalWorkflow.stepForward(facet.getStatus().getStatus(), REJECT)).thenReturn(GeneralStatus.REJECTED.toString());
		facetService.rejectFacet(1l);
	}

	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testRejectFacetWorkFlowException() throws ServiceException,WorkflowException, DataAcessException{
		Facet facet = BaseData.getFacet();
		when(facetService.retrieveById(1l)).thenReturn(facet);
		when(generalWorkflow.stepForward(facet.getStatus().getStatus(), REJECT)).thenThrow(WorkflowException.class);
		facetService.rejectFacet(1l);
	}

	@Test @Ignore
	public void testCreateFacet() throws ServiceException, ParseException, DataAcessException {

		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		Facet facet = BaseData.getFacet();
		Attribute attribute = BaseData.getAttribute();

		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);
		when(facetWrapperConverter.wrapperConverter(facetWrapper, new Facet())).thenReturn(facet);
		when(attributeService.retrieveById(facetWrapper.getAttributeId())).thenReturn(attribute);

		//		when(categoryFacetService.setDisplayOrder(Mockito.anyListOf(CategoryFacet.class))).thenAnswer(
		//			new Answer<List<CategoryFacet>>() {
		//				@SuppressWarnings("unchecked")
		//				@Override
		//				public List<CategoryFacet> answer(InvocationOnMock invocation) throws Throwable {
		//					Object[] args = invocation.getArguments();
		//					return (List<CategoryFacet>) args[0];
		//				}
		//			}
		//		);

		facetService.createFacet(facetWrapper);	

		when(attributeService.retrieveById(facetWrapper.getAttributeId())).thenReturn(null);
		facetService.createFacet(facetWrapper);	
	}

	@Test
	public void testFacetWrapper() throws ServiceException, ParseException, DataAcessException {
		//Get the facet wrapper
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		//create FacetWrapperConverter object
		facetWrapperConverter = new FacetWrapperConverter();
		facetWrapperConverter.setAuthenticationAuthorizationTool(userUtil);
		//Loading the user for retrieveById
		Users user = BaseData.getUsers();
		when(userUtil.getUser()).thenReturn(user);
		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);
		//Load the facet for the retrieveById
		Facet facet = BaseData.getFacet();
		facetWrapperConverter.wrapperConverter(facetWrapper, facet);

		//Check with Browse display mode and hidden display
		facetWrapper.setDisplayMode("BROWSE");
		facetWrapper.setFacetDisplay("N");
		facetWrapperConverter.wrapperConverter(facetWrapper, new Facet());

		//Check with SEARCH_BROWSE display mode
		facetWrapper.setDisplayMode("SEARCH_BROWSE");
		facetWrapperConverter.wrapperConverter(facetWrapper, new Facet());

		//Null check for facetWrapper
		facetWrapperConverter.wrapperConverter(null, null);

		//Null check for the attributes
		FacetWrapper facetWrapper1 = BaseData.getNullFacetWrappers().get(0);
		facetWrapperConverter.wrapperConverter(facetWrapper1, new Facet());

		//Null check for user id
		when(userUtil.getUser()).thenReturn(null);
		facetWrapperConverter.wrapperConverter(facetWrapper, new Facet());

		FacetWrapper facetWrappers = BaseData.getEmptyFacetWrappers().get(0);
		facetWrapperConverter.wrapperConverter(facetWrappers, new Facet());
	}

	@Test
	public void testUpdateFacet() throws ServiceException, ParseException, DataAcessException{

		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		Facet facet = BaseData.getFacet();
		Attribute attribute = BaseData.getAttribute();

		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);
		when(facetWrapperConverter.wrapperConverter(facetWrapper, facet)).thenReturn(facet);
		when(attributeService.retrieveById(facetWrapper.getAttributeId())).thenReturn(attribute);
		when(facetDAO.retrieveById(1L)).thenReturn(facet);
		facetService.updateFacet(facetWrapper);	
		
	}

	@Test
	public void testUpdateFacetOrder() throws DataAcessException, ServiceException, ParseException{
		setWrapperConverter();
		List<AttributeValueWrapper> attributeValueWrapper = BaseData.getAttributeValueWrapperLists();
		Facet facet = BaseData.getFacetsData();
		Category catgNode = BaseData.getCategoryNode();
		AttributeValue attributeValue = BaseData.getAttributeValueList().get(0);
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		Long valueId=facetWrapper.getPromoteList().get(0).getAttributeValueId();
		when(attrValueService.retrieveById(valueId)).thenReturn(attributeValue);
		facetWrapperConverter.updateFacetValueOrders(facet,attributeValueWrapper);
		facetWrapper.getPromoteList().get(0).setAttributeValueId(null);
		facetWrapperConverter.updateFacetValueOrders(facet,attributeValueWrapper);
		facetWrapperConverter.getAttributeValues(attributeValueWrapper,facet);
		CategoryWrapper catgWrapper = facetWrapper.getCategoryWrapper().get(0);
		when(categoryNodeService.retrieveById(catgWrapper.getCategoryId())).thenReturn(catgNode);
		facetWrapperConverter.getCategoryFacets(facetWrapper.getCategoryWrapper(),facet) ;
		AttributeValueWrapper attrValWrapper = facetWrapper.getPromoteList().get(0);
		attrValWrapper.setAttributeValueId(null);
		attrValWrapper.setAttrValuedisplay(null);
		facetWrapperConverter.getAttributeValue(attrValWrapper, facet, null);
	}

	@Test
	public void testUpdateCategoryFacet() throws DataAcessException, ServiceException, ParseException{
		setWrapperConverter();
		Facet facet = BaseData.getFacetsData();
		AttributeValue attributeValue = BaseData.getAttributeValueList().get(0);
		FacetWrapper facetWrapper = BaseData.getFacetWrappers().get(0);
		Long valueId=facetWrapper.getPromoteList().get(0).getAttributeValueId();
		when(attrValueService.retrieveById(valueId)).thenReturn(attributeValue);
		Category catgNode = BaseData.getCategoryNode();
		CategoryWrapper catgWrapper = facetWrapper.getCategoryWrapper().get(0);
		when(categoryNodeService.retrieveById(catgWrapper.getCategoryId())).thenReturn(catgNode);
		facetWrapperConverter.updateCategoryFacets(facetWrapper.getCategoryWrapper(), facet);
		FacetWrapper facetWrapper1 = BaseData.getFacetWrappers().get(0);
		facetWrapper1.getCategoryWrapper().get(0).setCategoryId("Pcat");
		facetWrapperConverter.updateCategoryFacets(facetWrapper1.getCategoryWrapper(), facet);
		CategoryWrapper categoryWrapper = BaseData.getCatgWrapper();
		CategoryFacet categoryFacet = BaseData.getCategoryFacet().get(0);
		facetWrapperConverter.getCategoryFacet(categoryWrapper, facet, categoryFacet);
		//Empty data check
		categoryWrapper.setCategoryId("");
		categoryWrapper.setDisplayContext("") ;
		categoryWrapper.setDisplayDepFacet("") ;
		categoryWrapper.setApplySubCategory("") ;
		facetWrapperConverter.getCategoryFacet(categoryWrapper, facet, categoryFacet);
	}

	@Test
	public void testLoadEditFacetData() throws ServiceException{
		Facet facet = BaseData.getFacet();
		Long facetId = facet.getFacetId();
		when(facetService.retrieveById(facetId)).thenReturn(facet);
		facetService.loadEditFacetData(facetId);

	}
	
	public void setWrapperConverter(){
		facetWrapperConverter = new FacetWrapperConverter();
		facetWrapperConverter.setAttrValueService(attrValueService);
		facetWrapperConverter.setCategoryFacetService(categoryFacetService);
		facetWrapperConverter.setCategoryNodeService(categoryNodeService);
		facetWrapperConverter.setFacetService(facetService);
	}
}
