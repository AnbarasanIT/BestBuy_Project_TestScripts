
package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertFalse;
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
import com.bestbuy.search.merchandising.dao.SynonymGroupDAO;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * Junit Test cases for SynonymGroupDao class
 * @author Lakshmi.Valluripalli - 06 Sep 2012
 */
public class SynonymGroupDaoTest {
	
	private final static Logger logger = Logger.getLogger(SynonymGroupDaoTest.class);
	
	SynonymGroupDAO synonymGroupDao = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		synonymGroupDao=new SynonymGroupDAO();
		entityManager=mock(EntityManager.class);
		synonymGroupDao.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		synonymGroupDao = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 SynonymType synonymType = BaseData.getSynonymListType();
			 Synonym synonym = BaseData.getSynonymGroup(synonymType);
			 doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return BaseData.getSynonymGroup(BaseData.getSynonymListType());
			 	     }
			 	   }).when(entityManager).persist(synonym);
			    Synonym dbSynonym = synonymGroupDao.save(synonym);
				assertNotNull(dbSynonym);
				assertTrue(synonym.getPrimaryTerm().equals("testCreate"));
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
			 SynonymType synonymType = BaseData.getSynonymListType();
			 Synonym synonymGroup = BaseData.getSynonymGroup(synonymType);
			 List<Synonym> synonymGroups = new ArrayList<Synonym>();
			 synonymGroups.add(synonymGroup);
		 	doAnswer(new Answer() {
		 	     public Object answer(InvocationOnMock invocation) {
		 	         Object[] args = invocation.getArguments();
		 	        EntityManager mock = (EntityManager) invocation.getMock();
		 	        return null;
		 	     }
		 	    }).when(entityManager).persist(synonymGroups);
		 	synonymGroupDao.save(synonymGroups);
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
			    Synonym synonym = BaseData.getSynonymGroup(synonymType);
			    when(entityManager.merge(synonym)).thenReturn(BaseData.getSavedSynonymGroup(BaseData.getSynonymListType()));
			    synonym = synonymGroupDao.update(synonym);
				assertNotNull(synonym);
				assertTrue(synonym.getPrimaryTerm().equals("testCreate"));
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
			 SynonymType synonymType = BaseData.getSynonymListType();
			 Synonym synonym = BaseData.getSynonymGroup(synonymType);
			 List<Synonym> synonyms = new ArrayList<Synonym>();
			 synonyms.add(synonym);
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 		    return null;
			 	     }
			 	    }).when(entityManager).persist(synonyms);
				synonymGroupDao.update(synonyms);
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
			 	Synonym synonymGroup = BaseData.getSavedSynonymGroup(synonymType);
				when(entityManager.find(Synonym.class,2l)).thenReturn(synonymGroup);
				synonymGroup = synonymGroupDao.retrieveById(2l);
				assertNotNull(synonymGroup);
				assertTrue(synonymGroup.getPrimaryTerm().equals("testCreate"));
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
				SynonymType synonymType = BaseData.getSynonymListType();
				Synonym synonymGroup = BaseData.getSynonymGroup(synonymType);
				List<Synonym> synonymGroups = new ArrayList<Synonym>();
				synonymGroups.add(synonymGroup);
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.getResultList()).thenReturn(synonymGroups);
				synonymGroups = (List<Synonym>)synonymGroupDao.retrieveAll();
				assertNotNull(synonymGroups);
				assertTrue(synonymGroups.size() == 1);
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
			 	Synonym synonym = BaseData.getSavedSynonymGroup(synonymType);
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(entityManager).remove(synonym);
			    synonymGroupDao.delete(synonym);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
	 
	 
	 /**
	  * Test case for deleteAllById
	  */
	 @Test 
	 public void testDeleteAllById(){
		 try{
				SynonymType synonymType = BaseData.getSynonymListType();
				Synonym synonym = BaseData.getSynonymGroup(synonymType);
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.executeUpdate()).thenReturn(1);
				synonymGroupDao.deleteAllById(synonym.getId());
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing retrieveAll:.",se);
		 }
	 }
	
	 /**
	  * TestCase for saveMethod DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testSaveExcep() throws DataAcessException{
		 SynonymType synonymType = BaseData.getSynonymListType();
		 Synonym synonym = BaseData.getSavedSynonymGroup(synonymType);
		doThrow(RuntimeException.class).when(entityManager).persist(synonym);
		synonymGroupDao.save(synonym);
	 }
	 
	 /**
	  * Test case for update Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testUpdateExcep() throws DataAcessException{
		 SynonymType synonymType = BaseData.getSynonymListType();
		 Synonym synonym = BaseData.getSavedSynonymGroup(synonymType);
		when(entityManager.merge(synonym)).thenThrow(new RuntimeException());
		synonymGroupDao.update(synonym);
	 }
	 
	 /**
	  * Test case for RetrieveById Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testRetrieveByIdExcep() throws DataAcessException {
		 when(entityManager.find(Synonym.class,2l)).thenThrow(new RuntimeException());
		 synonymGroupDao.retrieveById(2l);
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
			synonymGroupDao.retrieveAll();
	 }
	 
	 /**
	  * Test case for Delete Method DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testDeleteExcep() throws DataAcessException {
		 SynonymType synonymType = BaseData.getSynonymListType();
		 Synonym synonymGroup = BaseData.getSavedSynonymGroup(synonymType);
		doThrow(RuntimeException.class).when(entityManager).remove(synonymGroup);
		synonymGroupDao.delete(synonymGroup);
	}
	 
	 /**
	  * Test case for deleteAllById DataAcessException
	  * @throws DataAcessException
	  */
	 @Test(expected = DataAcessException.class)
	 public void testDeleteAllByIdExcep() throws DataAcessException{
				SynonymType synonymType = BaseData.getSynonymListType();
				Synonym synonym = BaseData.getSynonymGroup(synonymType);
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.executeUpdate()).thenThrow(new RuntimeException());
				synonymGroupDao.deleteAllById(synonym.getId());
	 }
	 
	 /**
	  * Test case to test the SynonymGroup Domain Object
	  */
	 @Test
	 public void testSynonymGroup(){
		 SynonymType synonymType = BaseData.getSynonymListType();
		 Synonym synonym1 = BaseData.getSavedSynonymGroup(synonymType);
		 //synonymGroup1.setAsset(BaseData.getAssets());
		 Synonym synonym2 = new Synonym();
		 synonymType = new SynonymType();
		 synonymType.setSynonymListId(1l);
		 synonym2.setSynListId(synonymType);
		 synonym2.setId(synonym1.getId());
		 //synonymGroup2.setAssets(synonymGroup2.getSynonymAsset());
		// synonymGroup2.setSynonymAsset(synonymGroup2.getSynonymAsset());
		 synonym2.setDirectionality(synonym1.getDirectionality());
		 synonym2.setExactMatch(synonym1.getExactMatch());
		 synonym2.setSynonymGroupTerms(synonym1.getSynonymGroupTerms());
		 synonym2.setPrimaryTerm(synonym1.getPrimaryTerm());
		 assertFalse(synonym1.equals(synonym2));
		 assertNotNull(synonym1.toString());
		
		//Equal Objects
		synonym1 = synonym2;
		assertTrue(synonym1.equals(synonym2));
		assertFalse(synonym1.hashCode() != synonym2.hashCode() );

		//objct with null
		assertFalse(synonym2.equals(null));
		assertFalse( synonym2.hashCode() != synonym1.hashCode() );

		
		
		//two empty objects
		synonym2 = new Synonym();
		synonym1 = new Synonym();
		assertTrue(synonym2.equals(synonym1));
		assertFalse(synonym2.hashCode() != synonym1.hashCode() );
	}
	 
}
