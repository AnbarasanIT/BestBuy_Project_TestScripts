
package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.SynonymTypeDAO;
import com.bestbuy.search.merchandising.dao.UsersDAO;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.service.SynonymTypeService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * Unit Test Class for SynonymTypeService
 * @author Lakshmi.Valluripalli
 */
public class SynonymTypeServiceTest {
	

	private final static Logger logger = Logger.getLogger(SynonymTypeServiceTest.class);
	
	SynonymTypeDAO synonymTypeDAO;
	SynonymTypeService synonymTypeService;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	 @Before
	 public void init() {
		    synonymTypeService = new SynonymTypeService();
		    synonymTypeDAO = mock(SynonymTypeDAO.class);
		    synonymTypeService.setDao(synonymTypeDAO);
			
	 }
	 
	 /**
	  * Clear all the Assigned Variables
	  */
	 @After
	 public void Destroy(){
		 synonymTypeService = null;
		 synonymTypeDAO = null;
		 
	 }
	 
	 /**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 	SynonymType synonymType = BaseData.getSynonymListType();
				when(synonymTypeDAO.save(synonymType)).thenReturn(synonymType);
				synonymType = synonymTypeService.save(synonymType);
				assertNotNull(synonymType);
				assertTrue(synonymType.getSynonymListType().equals("music"));
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing save:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
	 
	 /**
	  * TestCase for save Collection Method
	  */
	 @Test
	 public void testSaveCollection(){
		 try{
			 	List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
			    synonymTypes.add(BaseData.getSynonymListType());
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        SynonymTypeDAO mock = (SynonymTypeDAO) invocation.getMock();
			 	        return null;
			 	     }
			 	    }).when(synonymTypeDAO).save(synonymTypes);
				synonymTypeService.save(synonymTypes);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing save collection:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing save collection:.",se);
		 }
	 }
	 
	 /**
	  * Test case for update Method
	  */
	 @Test
	 public void testUpdate(){
		 try{
			    SynonymType synonymType = BaseData.getSynonymListType();
			    SynonymType synonymType1 = synonymType;
			    synonymType.setSynonymListType("Video");
				when(synonymTypeDAO.update(synonymType)).thenReturn(synonymType1);
				synonymType = synonymTypeService.update(synonymType);
				assertNotNull(synonymType);
				assertTrue(synonymType.getSynonymListType().equals("Video"));
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing update :.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing update:.",se);
		 }
	 }
	 
	 /**
	  * Test Case for update Collection Method
	  */
	 @Test
	 public void testUpdateCollection(){
		 try{
			 	List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
			    synonymTypes.add(BaseData.getSynonymListType());
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        SynonymTypeDAO mock = (SynonymTypeDAO) invocation.getMock();
			 	        return null;
			 	     }
			 	    }).when(synonymTypeDAO).update(synonymTypes);
				synonymTypeService.update(synonymTypes);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing update collection:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing update collection:.",se);
		 }
	 }
	 
	 /**
	  * Test case for RetrieveById Method
	  */
	 @Test
	 public void testRetrieveById(){
		 try{
			   SynonymType synonymType = BaseData.getSynonymListType();
				when(synonymTypeDAO.retrieveById(1147192016834L)).thenReturn(synonymType);
				synonymType = synonymTypeService.retrieveById(1147192016834L);
				assertNotNull(synonymType);
				assertTrue(synonymType.getSynonymListType().equals("music"));
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing retrieveById:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing retrieveById:.",se);
		 }
	 }
	 
	 /**
	  * Test case for RetrieveAll
	  */
	 @Test
	 public void testRetrieveAll(){
		 try{
			    List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
			    synonymTypes.add(BaseData.getSynonymListType());
			    when(synonymTypeDAO.retrieveAll()).thenReturn(synonymTypes);
			    List<SynonymType> loadedSynonyms = (List<SynonymType>)synonymTypeService.retrieveAll();
				assertNotNull(loadedSynonyms);
				assertTrue(loadedSynonyms.size() > 0);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing RetrieveAll:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing RetrieveAll:.",se);
		 }
	 }
	 
	 /**
	  * Test case for Delete Method
	  */
	 @Test
	 public void testDelete(){
		 try{
			 SynonymType synonymType = BaseData.getSynonymListType();
			 when(synonymTypeDAO.delete(synonymType)).thenReturn(null);
			 synonymTypeService.delete(synonymType);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing delete:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	 
	 /**
	  * Test case for delete method with argument entity and primary key
	  */
	 @Test
	 public void testDeleteEntity(){
		 try{
			 	SynonymType synonymType = BaseData.getSynonymListType();
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        SynonymTypeDAO mock = (SynonymTypeDAO) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(synonymTypeDAO).delete(synonymType.getSynonymListId(),synonymType);
			    synonymTypeService.delete(synonymType.getSynonymListId(),synonymType);
		 }catch(DataAcessException da){
			 logger.error("error at data layer while testing delete:.",da);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	 
	 /**
	  * TestCase for saveMethod Service Exception
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testSaveExcep() throws ServiceException,DataAcessException{
	 	SynonymType synonymType = BaseData.getSynonymListType();
		when(synonymTypeDAO.save(synonymType)).thenThrow(new DataAcessException());
		synonymTypeService.save(synonymType);
	}
	 
	 /**
	  * TestCase for saveAll Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testSaveAllExcep() throws ServiceException,DataAcessException{
		 List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
		 synonymTypes.add(BaseData.getSynonymListType());
		 doThrow(DataAcessException.class).when(synonymTypeDAO).save(synonymTypes);
		 synonymTypeService.save(synonymTypes);
	 }
	 
	 /**
	  * Test case for update Method Service Exception
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testUpdateExcep() throws ServiceException,DataAcessException{
		SynonymType synonymType = BaseData.getSynonymListType();
	    when(synonymTypeDAO.update(synonymType)).thenThrow(new DataAcessException());
		synonymTypeService.update(synonymType);
	}
	 
	 /**
	  * TestCase for updateAll Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testUpdateAllExcep() throws ServiceException,DataAcessException{
		 List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
		 synonymTypes.add(BaseData.getSynonymListType());
		 doThrow(DataAcessException.class).when(synonymTypeDAO).update(synonymTypes);
		 synonymTypeService.update(synonymTypes);
	 }
	 
	 /**
	  * Test case for RetrieveById Method Service Exception
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testRetrieveByIdExcep() throws ServiceException,DataAcessException{
		when(synonymTypeDAO.retrieveById(1147192016834L)).thenThrow(new DataAcessException("MockingException",new RuntimeException()));
		synonymTypeService.retrieveById(1147192016834L);
	 }
	 
	 /**
	  * Test case for RetrieveAll Service Exception
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testRetrieveAllExcep() throws ServiceException,DataAcessException{
	    when(synonymTypeDAO.retrieveAll()).thenThrow(new DataAcessException(new RuntimeException()));
	    synonymTypeService.retrieveAll();
	}
	 
	 /**
	  * Test case for Delete Method Service Exception
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testDeleteExcep() throws ServiceException,DataAcessException{
		 SynonymType synonymType = BaseData.getSynonymListType();
		 when(synonymTypeDAO.delete(synonymType)).thenThrow(new DataAcessException("MockingException"));
		 synonymTypeService.delete(synonymType);
	}
	 
	 
	 /**
	  * Test case for Delete Entity Method ServiceException
	  * @Throws ServiceException
	  * @Throws DataAcessException
	  */
	 @Test(expected = ServiceException.class)
	 public void testDeleteEntityExcep() throws ServiceException,DataAcessException {
		 SynonymType synonymType = BaseData.getSynonymListType();
		when(synonymTypeDAO.delete(synonymType.getSynonymListId(),synonymType)).thenThrow(new DataAcessException());
		synonymTypeService.delete(synonymType.getSynonymListId(),synonymType);
	}
}
