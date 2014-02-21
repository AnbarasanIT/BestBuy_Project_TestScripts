/**
 * 
 */
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
import com.bestbuy.search.merchandising.dao.SynonymTypeDAO;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.unittest.common.BaseData;


/**
 * Junit test cases for SynonymTypeDao
 * @author Lakshmi Valluripalli  - 06 Sep 2012
 */
public class SynonymTypeDaoTest {

	private final static Logger logger = Logger.getLogger(SynonymTypeDaoTest.class);
	
	SynonymTypeDAO synonymTypeDao = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		synonymTypeDao=new SynonymTypeDAO();
		entityManager=mock(EntityManager.class);
		synonymTypeDao.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		synonymTypeDao = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			    SynonymType synonymType = BaseData.getSynonymListType();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return BaseData.getSynonymListType();
			 	     }
			 	   }).when(entityManager).persist(synonymType);
			 	synonymType = synonymTypeDao.save(synonymType);
				assertNotNull(synonymType);
				assertTrue(synonymType.getSynonymListType().equals("music"));
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
		    List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
		    synonymTypes.add(BaseData.getSynonymListType());
		 	doAnswer(new Answer() {
		 	     public Object answer(InvocationOnMock invocation) {
		 	         Object[] args = invocation.getArguments();
		 	        EntityManager mock = (EntityManager) invocation.getMock();
		 	        return null;
		 	     }
		 	    }).when(entityManager).persist(synonymTypes);
		 	synonymTypeDao.save(synonymTypes);
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
			 SynonymType synonymType = BaseData.getSynonymListType();
			 SynonymType synonymType1 = synonymType;
			    synonymType.setSynonymListType("Video");
				when(entityManager.merge(synonymType)).thenReturn(synonymType);
				synonymType = synonymTypeDao.update(synonymType);
				assertNotNull(synonymType);
				assertTrue(synonymType.getSynonymListType().equals("Video"));
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
			 List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
			    synonymTypes.add(BaseData.getSynonymListType());
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 		    return null;
			 	     }
			 	    }).when(entityManager).persist(synonymTypes);
				synonymTypeDao.update(synonymTypes);
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
			    SynonymType synonymType = BaseData.getSynonymListType();
				when(entityManager.find(SynonymType.class,1147192016834L)).thenReturn(synonymType);
				synonymType = synonymTypeDao.retrieveById(1147192016834L);
				assertNotNull(synonymType);
				assertTrue(synonymType.getSynonymListType().equals("music"));
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
			 	List<SynonymType> synonymTypes = new ArrayList<SynonymType>();
			    synonymTypes.add(BaseData.getSynonymListType());
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.getResultList()).thenReturn(synonymTypes);
				synonymTypes = (List<SynonymType>)synonymTypeDao.retrieveAll();
				assertNotNull(synonymTypes);
				assertTrue(synonymTypes.size() == 1);
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
			    SynonymType synonymType = BaseData.getSynonymListType();
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(entityManager).remove(synonymType);
			    synonymTypeDao.delete(synonymType);
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
		 SynonymType synonymType = BaseData.getSynonymListType();
		doThrow(RuntimeException.class).when(entityManager).persist(synonymType);
		synonymTypeDao.save(synonymType);
	 }
	 
	 /**
	  * Test case for update Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testUpdateExcep() throws  DataAcessException{
		 SynonymType synonymType = BaseData.getSynonymListType();
		when(entityManager.merge(synonymType)).thenThrow(new RuntimeException());
		synonymTypeDao.update(synonymType);
	 }
	 
	 /**
	  * Test case for RetrieveById Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testRetrieveByIdExcep() throws  DataAcessException {
		 when(entityManager.find(SynonymType.class,1147192016834L)).thenThrow(new RuntimeException());
		 synonymTypeDao.retrieveById(1147192016834L);
	 }
	 
	 /**
	  * Test case for Delete Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testDeleteExcep() throws  DataAcessException {
		SynonymType synonymType = BaseData.getSynonymListType();
		doThrow(RuntimeException.class).when(entityManager).remove(synonymType);
		synonymTypeDao.delete(synonymType);
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
			synonymTypeDao.retrieveAll();
	 }
}
