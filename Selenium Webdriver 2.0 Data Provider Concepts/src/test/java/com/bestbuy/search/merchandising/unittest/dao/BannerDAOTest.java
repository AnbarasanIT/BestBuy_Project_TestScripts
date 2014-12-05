package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.BannerDAO;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 * Test Case of Banner DAO
 */
public class BannerDAOTest {
	
	private final static Logger logger = Logger.getLogger(BannerDAOTest.class);
	
	BannerDAO bannerDAO = null;
	EntityManager entityManager = null;
	
	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerDAO=new BannerDAO();
		entityManager=mock(EntityManager.class);
		bannerDAO.setEntityManager(entityManager);
		
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		bannerDAO = null;
		entityManager = null;
	}
	
	/**
	  * TestCase for saveMethod
	 * @throws DataAcessException 
	  */
	 @Test
	 public void testSave() throws DataAcessException{
			 	Banner banner = BaseData.getBannerData();
			 	doAnswer(new Answer() {
			 	     public Object answer(InvocationOnMock invocation) {
			 	         Object[] args = invocation.getArguments();
			 	        EntityManager mock = (EntityManager) invocation.getMock();
			 	         return  BaseData.getBannerData();
			 	     }
			 	   }).when(entityManager).persist(banner);
			 	banner = bannerDAO.save(banner);
			 	assertNotNull(banner);
				assertTrue(banner.getBannerId() == 123l);
	 }
}
