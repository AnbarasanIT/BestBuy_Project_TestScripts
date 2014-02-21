/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.StatusDAO;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.service.StatusService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * Unit Test Case to test StatusService
 * @author Lakshmi.Valluripalli -31 Aug 2012
 */
public class StatusServiceTest {
	
	private final static Logger logger = Logger.getLogger(StatusServiceTest.class);
	
	StatusDAO statusDAO;
	StatusService statusService;
	
	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	 @Before
	 public void init() {
		 	statusService = new StatusService();
		 	statusDAO = mock(StatusDAO.class);
		 	statusService.setDao(statusDAO);
			
	 }
	 
	 /**
	  * Clear all the Assigned Variables
	  */
	 @After
	 public void Destroy(){
		 statusService = null;
		 statusDAO = null;
		 
	 }
	 
	 /**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 	Status status = BaseData.getStatus();
				when(statusService.save(status)).thenReturn(status);
				status = statusService.save(status);
				assertNotNull(status);
				assertTrue(status.getStatusId() == 2l);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
	 
	 /**
	  * Test case for update Method
	  */
	 @Test
	 public void testUpdate(){
		 try{
			 Status status = BaseData.getStatus();
			 Status stat = status;
			 stat.setStatus("ApproveJunit");
			 when(statusService.update(status)).thenReturn(stat);
			 status = statusService.update(status);
			 assertNotNull(status);
			 assertTrue(status.getStatus().equals("ApproveJunit"));
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing update:.",se);
		 }
	 }
	 
	 /**
	  * Test case for RetrieveById Method
	  */
	 @Test
	 public void testRetrieveById(){
		 try{
			  Status status = BaseData.getStatus();
			  when(statusService.retrieveById(3l)).thenReturn(status);
			  status = statusService.retrieveById(3l);
			  assertNotNull(status);
			  assertTrue(status.getStatus().equals("Draft"));
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
			    List<Status> status = new ArrayList<Status>();
			    status.add(BaseData.getStatus());
			    when(statusService.retrieveAll()).thenReturn(status);
			 List<Status> loadedStatus = (List<Status>)statusService.retrieveAll();
				assertNotNull(loadedStatus);
				assertTrue(loadedStatus.size() > 0);
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
			 Status status = BaseData.getStatus();
			 when(statusService.delete(status)).thenReturn(null);
			 statusService.delete(status);
		 }catch(ServiceException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	 
	 /**
	  * TestCase for saveMethod Service Exeption
	  */
	 @Test(expected = ServiceException.class)
	 public void testSaveExcep()  throws ServiceException,DataAcessException {
		 Status status = BaseData.getStatus();
		 when(statusDAO.save(status)).thenThrow(new DataAcessException());
		 status = statusService.save(status);
	 }
	 
	 /**
	  * Test case for update Method Service Exeption
	  */
	 @Test(expected = ServiceException.class)
	 public void testUpdateExcep()  throws ServiceException,DataAcessException{
		 Status status = BaseData.getStatus();
		 when(statusDAO.update(status)).thenThrow(new DataAcessException());
		 status = statusService.update(status);
	 }
	 
	 /**
	  * Test case for RetrieveById Method Service Exeption
	  */
	 @Test(expected = ServiceException.class)
	 public void testRetrieveByIdExcep() throws ServiceException,DataAcessException{
		 when(statusDAO.retrieveById(3l)).thenThrow(new DataAcessException());
		 Status status = statusService.retrieveById(3l);
	 }
	 
	 /**
	  * Test case for RetrieveAll Service Exeption
	  */
	 @Test(expected = ServiceException.class)
	 public void testRetrieveAllExcep() throws ServiceException,DataAcessException{
	     List<Status> status = new ArrayList<Status>();
	     status.add(BaseData.getStatus());
	     when(statusDAO.retrieveAll()).thenThrow(new DataAcessException());
	     List<Status> loadedStatus = (List<Status>)statusService.retrieveAll();
	 }
	 
	 /**
	  * Test case for Delete Method Service Exception
	  */
	 @Test(expected = ServiceException.class)
	 public void testDeleteExcep() throws ServiceException,DataAcessException{
		 Status status = BaseData.getStatus();
		 when(statusDAO.delete(status)).thenThrow(new DataAcessException());
		 statusService.delete(status);
	 }
}
