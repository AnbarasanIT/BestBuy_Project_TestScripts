/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BaseDAO;
import com.bestbuy.search.merchandising.dao.SynonymGroupDAO;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * Unit Test Case to test SynonymGroupService
 * @author Lakshmi.Valluripalli -31 Aug 2012
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class SynonymGroupServiceTest{

	private final static Logger logger = Logger.getLogger(SynonymGroupServiceTest.class);
	SynonymService synonymService;
	SynonymGroupDAO synonymGroupDAO;
	BaseDAO<Long,Synonym> baseDao;
	SynonymTypeService synonymTypeService;
	StatusService statusService;
	UsersService usersService;
	GeneralWorkflow generalWorkflow;
	UserUtil userUtil;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		synonymService = new SynonymService();
		synonymTypeService = mock(SynonymTypeService.class);
		synonymGroupDAO = mock(SynonymGroupDAO.class);
		statusService = mock(StatusService.class);
		usersService = mock(UsersService.class);
		generalWorkflow = mock(GeneralWorkflow.class);
		synonymService.setDao(synonymGroupDAO);
		synonymService.setSynonymTypeService(synonymTypeService);
		synonymService.setStatusService(statusService);
		//synonymGroupService.setAssetsService(assetsService);
		synonymService.setGeneralWorkflow(generalWorkflow);
		userUtil = mock(UserUtil.class);
		synonymService.setUserUtil(userUtil);
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		synonymService = null;
		synonymTypeService = null;
		synonymGroupDAO = null;
		statusService = null;
		usersService = null;
	}

	/**
	 * Unit test case to test the Synonym Creation Functionality
	 * @throws DataAcessException 
	 */
	@Test
	public void testCreateSynonym() throws ServiceException {
		
		SynonymType synonymType = BaseData.getSynonymListType();
		when(synonymTypeService.retrieveById(1147192016834L)).thenReturn(synonymType);

		Synonym synonym = BaseData.getSynonymGroup(synonymType);
		Synonym savedSynonym = BaseData.getSavedSynonymGroup(synonymType);
		when(synonymService.save(Mockito.any(Synonym.class))).thenReturn(savedSynonym);
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);

		Users user = BaseData.getUsers();
		when(usersService.retrieveById("a1234567")).thenReturn(user);
		SynonymWrapper synonymWrapper =  BaseData.getSynonymWrapper();
		
		when(userUtil.getUser()).thenReturn(user);
		
		synonymService.createSynonym(synonymWrapper);

	}

	/**
	 * Unit test case to test the Synonym Creation Functionality
	 * @throws DataAcessException 
	 */
	@Test
	public void testPrepareSynonymGroups() throws ServiceException,DataAcessException{
		
		SynonymType synonymType = BaseData.getSynonymListType();
		when(synonymTypeService.retrieveById(1147192016834L)).thenReturn(null);
		Synonym savedSynonym = BaseData.getSavedSynonymGroup(synonymType);
		savedSynonym.setId(null);
		when(synonymGroupDAO.save(Mockito.any(Synonym.class))).thenReturn(savedSynonym);
		SynonymWrapper synonymWrapper =  BaseData.getSynonymWrapper();
		IWrapper wrapper = synonymService.createSynonym(synonymWrapper);
		assertNotNull(wrapper);
	}



	/**
	 * Unit test case to test the Synonym Creation Functionality
	 * @throws DataAcessException 
	 * @throws WorkflowException 
	 */
	@Test
	public void testEditSynonym()  throws ServiceException,DataAcessException, WorkflowException{

		//loading the SynonymList for RetrieveBy Id
		SynonymType synonymType = BaseData.getSynonymListType();
		when(synonymTypeService.retrieveById(1147192016834L)).thenReturn(synonymType);

		Synonym editSynonym = BaseData.getDBSynonym(synonymType);
		when(synonymService.retrieveById(2l)).thenReturn(editSynonym);

		//Loading the Status for the retrieveById
		Status status = BaseData.getStatus();
		when(statusService.retrieveById(2L)).thenReturn(status);

		//Loading the Users for the retrieveById
		Users user = BaseData.getUsers();
		when(usersService.retrieveById("a1234567")).thenReturn(user); 
		when(userUtil.getUser()).thenReturn(user);
		Synonym savedSynonym = BaseData.getSavedSynonymGroup(synonymType);
		when(synonymService.update(Mockito.any(Synonym.class))).thenReturn(savedSynonym);
		
		SynonymWrapper synonymWrapper =  BaseData.getEditSynonymWrapper();
		
		// mocking the workflow
		when(generalWorkflow.stepForward(synonymWrapper.getStatus(), EDIT)).thenReturn(synonymWrapper.getStatus());
		
		synonymService.update(synonymWrapper);

		when(synonymGroupDAO.update(editSynonym)).thenThrow(new DataAcessException());
	}



	/**
	 * Test case for LoadSynonym
	 */
	@Test
	public void testLoadSynonym() throws ServiceException,JSONException{
		List<SynonymType> synonymTypes= new ArrayList<SynonymType>();
		SynonymType synonymType = BaseData.getSynonymListType();
		synonymTypes.add(synonymType);
		when(synonymTypeService.retrieveAll()).thenReturn(synonymTypes);
		synonymService.loadSynonym();
		//check for the retrieve all returning null
		when(synonymTypeService.retrieveAll()).thenReturn(null);
		synonymService.loadSynonym();
		//check for the retrieve all returning empty list
		when(synonymTypeService.retrieveAll()).thenReturn(new ArrayList<SynonymType>());
		synonymService.loadSynonym();
	}


	/**
	 * Test case for load Synonym JSONException
	 * @throws JSONException
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = JSONException.class)
	public void testLoadSynonymJsonExcep() throws JSONException,ServiceException {
		//check for the ServiceException
		when(synonymTypeService.retrieveAll()).thenThrow(JSONException.class);
		synonymService.loadSynonym();
	}



	/**
	 * Test case for LoadSynonym
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = ServiceException.class)
	public void testLoadSynonymExcep() throws ServiceException,JSONException{
		List<SynonymType> synonymTypes= new ArrayList<SynonymType>();
		SynonymType synonymType = BaseData.getSynonymListType();
		synonymTypes.add(synonymType);
		when(synonymTypeService.retrieveAll()).thenReturn(synonymTypes);
		synonymService.loadSynonym();
		//check for the retrieve all returning null
		when(synonymTypeService.retrieveAll()).thenReturn(null);
		synonymService.loadSynonym();
		//check for the retrieve all returning empty list
		when(synonymTypeService.retrieveAll()).thenReturn(new ArrayList<SynonymType>());
		synonymService.loadSynonym();
		//check for the ServiceException
		when(synonymTypeService.retrieveAll()).thenThrow(ServiceException.class);
		synonymService.loadSynonym();

	}

	/**
	 * Test case for deleteAllById
	 */
	@Test
	public void testDeleteAllById(){
		try{
			SynonymType synonymType = BaseData.getSynonymListType();
			Synonym synonym = BaseData.getSynonymGroup(synonymType);
			doAnswer(new Answer() {
				public Object answer(InvocationOnMock invocation) {
					Object[] args = invocation.getArguments();
					SynonymGroupDAO mock = (SynonymGroupDAO) invocation.getMock();
					return null;
				}
			}).when(synonymGroupDAO).deleteAllById(synonym.getId());
			synonymService.deleteAllById(synonym.getId());
			synonymService.getBaseDAO();
		}catch(DataAcessException da){
			logger.error("error at service layer while testing DeleteAllById:.",da);
		}catch(ServiceException se){
			logger.error("error at service layer while testing DeleteAllById:.",se);
		}
	}
}
