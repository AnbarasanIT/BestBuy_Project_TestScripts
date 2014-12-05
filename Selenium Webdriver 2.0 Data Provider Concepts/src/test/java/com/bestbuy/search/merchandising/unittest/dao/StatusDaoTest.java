
package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.StatusDAO;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * Junit cases for Status DAO class
 * @author Lakshmi Valluripalli - 06 Sep 2012
 */
public class StatusDaoTest {

	private final static Logger logger = Logger.getLogger(StatusDaoTest.class);
	
	StatusDAO statusDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		statusDAO=new StatusDAO();
		entityManager=mock(EntityManager.class);
		statusDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		statusDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 	Status status = BaseData.getStatus();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getStatus();
			 	     }
			 	   }).when(entityManager).persist(status);
			 	status = statusDAO.save(status);
			 	assertNotNull(status);
				assertTrue(status.getStatusId() == 2l);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
	 
	 /**
	  * TestCase for save Collection Method
	  */
	 @Test
	 public void testSaveCollection(){
		 try{
			 	List<Status> status = new ArrayList<Status>();
			    status.add(BaseData.getStatus());
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	        return null;
			 	     }
			 	    }).when(entityManager).persist(status);
			 	statusDAO.save(status);
		}catch(DataAcessException se){
			 logger.error("error at service layer while testing save:.",se);
		 }
	 }
	 
	 /**
	  * Test case for update  Method
	  */
	 @Test
	 public void testUpdate(){
		 try{
			 	Status status = BaseData.getStatus();
			 	 Status stat = status;
				 stat.setStatus("ApproveJunit");
				when(entityManager.merge(status)).thenReturn(stat);
				 status = statusDAO.update(status);
				 assertNotNull(status);
				 assertTrue(status.getStatus().equals("ApproveJunit"));
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing update:.",se);
		 }
	 }
	 
	 /**
	  * Test Case for update Collection Method
	  */
	 @Test
	 public void testUpdateCollection(){
		 try{
			 	List<Status> status = new ArrayList<Status>();
			    status.add(BaseData.getStatus());
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 		    return null;
			 	     }
			 	    }).when(entityManager).persist(status);
				statusDAO.update(status);
		 }catch(DataAcessException se){
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
				when(entityManager.find(Status.class,3l)).thenReturn(status);
				status = statusDAO.retrieveById(3l);
				assertNotNull(status);
				assertTrue(status.getStatus().equals("Draft"));
		 }catch(DataAcessException se){
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
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.getResultList()).thenReturn(status);
				status = (List<Status>)statusDAO.retrieveAll();
				assertNotNull(status);
				assertTrue(status.size() == 1);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing retrieveAll:.",se);
		 }
	 }

	
	 /**
	  * Test case for Delete Method
	  */
	 @Test
	 public void testDelete(){
		 try{
			 	Status status = BaseData.getStatus();
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(entityManager).remove(status);
			    statusDAO.delete(status);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	
	 /**
	  * TestCase for saveMethod DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testSaveExcep() throws  DataAcessException{
		 Status status = BaseData.getStatus();
		doThrow(RuntimeException.class).when(entityManager).persist(status);
		statusDAO.save(status);
	 }
	 
	 /**
	  * Test case for update Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testUpdateExcep() throws  DataAcessException{
		 Status status = BaseData.getStatus();
		when(entityManager.merge(status)).thenThrow(new RuntimeException());
		statusDAO.update(status);
	 }
	 
	 /**
	  * Test case for RetrieveById Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testRetrieveByIdExcep() throws  DataAcessException {
		when(entityManager.find(Status.class,3l)).thenThrow(new RuntimeException());
		statusDAO.retrieveById(3l);
	 }
	
	 /**
	  * Test case for Delete Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testDeleteExcep() throws  DataAcessException {
		 Status status = BaseData.getStatus();
		doThrow(RuntimeException.class).when(entityManager).remove(status);
		statusDAO.delete(status);
	}
	 
	 /**
	  * Test case for RetrieveAll DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testRetrieveAllExcep() throws  DataAcessException{
		Query mockQuery = mock(Query.class);
		when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
		when(mockQuery.getResultList()).thenThrow(new RuntimeException());
		statusDAO.retrieveAll();
	 }
}
