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
import com.bestbuy.search.merchandising.dao.BannerTemplateMetaDAO;
import com.bestbuy.search.merchandising.domain.BannerTemplateMeta;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class BannerTemplateDAOTest {
	private final static Logger logger = Logger.getLogger(BannerTemplateDAOTest.class);

	BannerTemplateMetaDAO bannerTemplateDAO = null;
	EntityManager entityManager = null;


	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		bannerTemplateDAO=new BannerTemplateMetaDAO();
		entityManager=mock(EntityManager.class);
		bannerTemplateDAO.setEntityManager(entityManager);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		bannerTemplateDAO = null;
		entityManager = null;
	}

	/**
	 * TestCase for saveMethod
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws DataAcessException{
		BannerTemplateMeta bannertemplate = BaseData.getBannerTemplateData();
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				EntityManager mock = (EntityManager) invocation.getMock();
				return  BaseData.getBannerTemplateData();
			}
		}).when(entityManager).persist(bannertemplate);
		bannertemplate = bannerTemplateDAO.save(bannertemplate);
		assertNotNull(bannertemplate);
		assertTrue(bannertemplate.getBannerTemplateId() == 1l);
	}
}
