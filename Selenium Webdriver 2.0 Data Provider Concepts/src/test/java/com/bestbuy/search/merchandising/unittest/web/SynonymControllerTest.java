package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.SynonymGroupDAO;
import com.bestbuy.search.merchandising.service.SynonymService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.web.SynonymController;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowCreationException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * Unit Test Case to test SynonymGroupController
 * @author Chanchal.kumari -06 Sep 2012
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class SynonymControllerTest {
	
	private final static Logger logger = Logger.getLogger(SynonymControllerTest.class);
	
	SynonymController synonymController;
	SynonymService synonymService;
	SynonymGroupDAO synonymGroupDAO;
	MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
	ResourceBundle errorMessages;
	ResourceBundle successMessages;
	ResourceBundleHandler resourceBundleHandler;

	//AssetsService assetsService;
	
	@Before
	public void init() {
		synonymController = new SynonymController();
		synonymService = mock(SynonymService.class);
		merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
		synonymGroupDAO = mock(SynonymGroupDAO.class);
		resourceBundleHandler = mock(ResourceBundleHandler.class);
		errorMessages = mock(ResourceBundle.class);
		successMessages = mock(ResourceBundle.class);
		resourceBundleHandler.setErrorMessagesProperties(errorMessages);
		resourceBundleHandler.setErrorMessagesProperties(successMessages);
		merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
		synonymService.setDao(synonymGroupDAO);
		//assetsService.setDao(assetsDAO);
		//synonymGroupService.setAssetsService(assetsService);
			synonymController.setSynonymGroupService(synonymService);
		//synonymGroupController.setAssetsService(assetsService);
		synonymController.setMerchandisingBaseResponse(merchandisingBaseResponse);
	 }
	
/**
 * Unit testing for Master synonym list.
 */
	
	
	@Test
	public void testList() throws Exception {
		SynonymWrapper wrapper = BaseData.getSynonymWrapper();
		List<SynonymWrapper> wrappers = new ArrayList<SynonymWrapper>();
		wrappers.add(wrapper);
		@SuppressWarnings("serial")
		Set<String> status =new HashSet<String>() {{  
			  add("Approve"); add("Reject"); add("Delete");add("Edit");
		}};
		PaginationWrapper paginationWrapper = new PaginationWrapper() ;
		when(synonymService.listSynonyms(paginationWrapper)).thenReturn(wrappers);
		MerchandisingBaseResponse<IWrapper> response = synonymController.list(new PaginationWrapper());
		assertTrue(response.getSuccessCode().equals("Listing.Success"));
	}
	
	@Test
	public void testListException() throws Exception {
		PaginationWrapper paginationWrapper = new PaginationWrapper() ;
		doThrow(ServiceException.class).when(synonymService).listSynonyms(paginationWrapper);
		synonymController.list(paginationWrapper);
	}
	
	@Test
	public void testCreate()  {
		SynonymWrapper synonymWrapper = BaseData.getSynonymWrapper();
		when(resourceBundleHandler.getSuccessString("Create.Success", "Synonym")).thenReturn("Synonym created successfully");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.create(synonymWrapper);
		assertTrue(rep.getMessage().equals("Synonym created successfully"));
	}
	
	@Test
	public void testNegCreate()  {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.create(null);
		assertTrue(rep.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testCreateException() throws Exception {
		SynonymWrapper synonymWrapper = BaseData.getSynonymWrapper();
		doThrow(new ServiceException(new DataAcessException())).when(synonymService).createSynonym(synonymWrapper);
		when(resourceBundleHandler.getErrorString("Create.Error","Synonym")).thenReturn("Error while trying to create the Synonym");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.create(synonymWrapper);
		assertTrue(rep.getMessage().equals("Error while trying to create the Synonym"));
	}
	
	@Test  
	public void testUpdate()  {
		SynonymWrapper synonymWrapper = BaseData.getSynonymWrapper();
		when(resourceBundleHandler.getSuccessString("Update.Success", "Synonym")).thenReturn("Synonym updated successfully");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.update(synonymWrapper);
		assertTrue(rep.getMessage().equals("Synonym updated successfully"));
	}
	
	@Test
	public void testNegUpdate()  {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.update(null);
		assertTrue(rep.getMessage().equals("Invalid Request"));
	}
	
	@Test
	public void testUpdateException() throws Exception {
			SynonymWrapper synonymWrapper = BaseData.getSynonymWrapper();
			doThrow(new ServiceException()).when(synonymService).update(synonymWrapper);
			when(resourceBundleHandler.getErrorString("Update.Error","Synonym")).thenReturn("Error while trying to update the Synonym");
			MerchandisingBaseResponse<IWrapper> rep = synonymController.update(synonymWrapper);
			assertTrue(rep.getMessage().equals("Error while trying to update the Synonym"));
	}
	
	@Test
	public void testDelete()  {
		//try{
			//when(assetsService.delete(2l)).thenReturn("SUCCESS");
			when(resourceBundleHandler.getSuccessString("Delete.Success", "Synonym")).thenReturn("Synonym deleted successfully");
			MerchandisingBaseResponse<IWrapper> rep = synonymController.delete(2l);  // TODO the test should not be attached to a hardcoded id
			assertTrue(rep.getMessage().equals("Synonym deleted successfully"));
		/*}catch(ParseException pe){
		
			logger.error("error at service layer while parsing dates:.",pe);
		}catch( ServiceException se)
		{
			logger.error("error at service layer while testing delete:.",se);
		}*/
	}
	
	@Test
	public void testNullDelete()  {
		when(resourceBundleHandler.getErrorString("Request.NoData")).thenReturn("Invalid Request");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.delete(null);
		assertTrue(rep.getMessage().equals("Invalid Request"));
	}	
	
	@Test  // @Ignore 
	public void testDeleteException() throws Exception {		
		doThrow(new ServiceException("Error while performing the workflow step", new WorkflowCreationException())).when(synonymService).deleteSynonym(2l);
		when(resourceBundleHandler.getErrorString("Delete.Error", "Synonym")).thenReturn("Error while trying to delete the Synonym");
		MerchandisingBaseResponse<IWrapper> rep = synonymController.delete(2l);  // TODO the test should not be attached to a hardcoded id
		assertTrue(rep.getMessage().equals("Error while trying to delete the Synonym"));
	}
	
	@Test
	public void testLoad()  {
		try{
			when(synonymService.loadSynonym()).thenReturn("synonymGroup In Json format"); // TODO we are not testing anything here
			ResponseEntity<String> Rep = synonymController.load();
			assertTrue(Rep.getBody().equalsIgnoreCase("synonymGroup In Json format"));
		}
		
		catch( ServiceException se){
			logger.error("error at service layer while testing delete:.",se);
		}
	
		catch(JSONException je){
			logger.error("Error while creating synonym",je);
		}
	}
	
	@Test
	public void testLoadException() throws Exception {
			when(synonymService.loadSynonym()).thenThrow(new ServiceException("Error during LoadSynonym"));
			synonymController.load();
	}
}
