package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
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
import com.bestbuy.search.merchandising.dao.PromoDAO;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.unittest.common.PromoTestData;


/**
 * Junit Test cases for PromoDao class
 * @author Chanchal.KUmari - 10th Oct 2012
 */
public class PromoDAOTest {

private final static Logger logger = Logger.getLogger(PromoDAOTest.class);
	
	PromoDAO promoDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		promoDAO=new PromoDAO();
		entityManager=mock(EntityManager.class);
		promoDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		promoDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	  */
	 @Test
	 public void testSave(){
		 try{
			 Promo promo = PromoTestData.getPromo();
			 doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return PromoTestData.getPromo();
			 	     }
			 	   }).when(entityManager).persist(promo);
			 Promo dbPromo = promoDAO.save(promo);
			assertNotNull(dbPromo);
			assertTrue(dbPromo.getPromoName().equals("test"));
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
			 Promo promo = PromoTestData.getPromo();
			 List<Promo> promos = new ArrayList<Promo>();
			 promos.add(promo);
		 	doAnswer(new Answer() {
		 	     public Object answer(InvocationOnMock invocation) {
		 	         Object[] args = invocation.getArguments();
		 	        EntityManager mock = (EntityManager) invocation.getMock();
		 	        return null;
		 	     }
		 	    }).when(entityManager).persist(promos);
		 	promoDAO.save(promos);
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
			 	Promo promo = PromoTestData.getPromo();
			    when(entityManager.merge(promo)).thenReturn(PromoTestData.getPromo());
			    promo = promoDAO.update(promo);
				assertNotNull(promo);
				assertTrue(promo.getPromoName().equals("test"));
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
			 Promo promo = PromoTestData.getPromo();
			 List<Promo> promos = new ArrayList<Promo>();
			 promos.add(promo);
				doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 		    return null;
			 	     }
			 	    }).when(entityManager).persist(promos);
				promoDAO.update(promos);
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
			 	Promo promo = PromoTestData.getPromo();
				when(entityManager.find(Promo.class,1l)).thenReturn(promo);
				promo = promoDAO.retrieveById(1l);
				assertNotNull(promo);
				assertTrue(promo.getPromoName().equals("test"));
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
			 	Promo promo = PromoTestData.getPromo();
			 	List<Promo> promos = new ArrayList<Promo>();
			 	promos.add(promo);
				Query mockQuery = mock(Query.class);
				when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
				when(mockQuery.getResultList()).thenReturn(promos);
				promos = (List<Promo>)promoDAO.retrieveAll();
				assertNotNull(promos);
				assertTrue(promos.size() == 1);
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
			 	Promo promo = PromoTestData.getPromo();
			    doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return null;
			 	      }
			 	    }).when(entityManager).remove(promo);
			    promoDAO.delete(promo);
		 }catch(DataAcessException se){
			 logger.error("error at service layer while testing delete:.",se);
		 }
	 }
}
