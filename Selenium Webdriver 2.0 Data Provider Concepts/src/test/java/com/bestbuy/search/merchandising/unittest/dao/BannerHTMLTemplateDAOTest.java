package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.dao.BannerTemplateDAO;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 * Unit testing for BannerURLListDAO class
 */
public class BannerHTMLTemplateDAOTest {

	BannerTemplateDAO bannerURLListDAO = null;
	EntityManager entityManager = null;


	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerURLListDAO=new BannerTemplateDAO();
		entityManager=mock(EntityManager.class);
		bannerURLListDAO.setEntityManager(entityManager);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		bannerURLListDAO = null;
		entityManager = null;
	}

	/**
	 * TestCase for saveMethod
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws DataAcessException{
		BannerTemplate bannerUrlList = BaseData.getBannerUrlList();
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				EntityManager mock = (EntityManager) invocation.getMock();
				return  BaseData.getBannerUrlList();
			}
		}).when(entityManager).persist(bannerUrlList);
		bannerUrlList = bannerURLListDAO.save(bannerUrlList);
		assertNotNull(bannerUrlList);
		assertTrue(bannerUrlList.getId()==1);
		assertTrue(bannerUrlList.getBanner().getBannerId()==123);
	}
	
}
