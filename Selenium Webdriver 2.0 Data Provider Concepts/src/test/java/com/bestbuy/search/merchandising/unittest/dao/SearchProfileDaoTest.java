package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.search.merchandising.dao.SearchProfileDAO;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class SearchProfileDaoTest {
	private final static Logger logger = Logger.getLogger(SearchProfileDaoTest.class);

	SearchProfileDAO searchProfileDAO = null;
	EntityManager entityManager = null;

	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		searchProfileDAO=new SearchProfileDAO();
		entityManager=mock(EntityManager.class);
		searchProfileDAO.setEntityManager(entityManager);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		searchProfileDAO = null;
		entityManager = null;
	}
	
	/**
	 * Test Case for Equals Method
	 */
	@Test
	public void testSearchProfile(){
		SearchProfile searchProfile1 = BaseData.getSearchProfile();
		SearchProfile searchProfile2 = BaseData.getSearchProfile();
		searchProfile2.setSynonymListId(new SynonymType());
		assertFalse(searchProfile1.equals(searchProfile2));
		assertTrue(searchProfile1.equals(searchProfile1));
		assertTrue(searchProfile1.hashCode() != searchProfile2.hashCode());
		assertNotNull(searchProfile2.toString());
		assertEquals("Seach Profile ID Equals",true,searchProfile1.getSearchProfileId().equals(searchProfile2.getSearchProfileId()));
		assertTrue(searchProfile1.getCollections().equals(searchProfile2.getCollections()));
		assertTrue(searchProfile1.getDefaultPath().equals(searchProfile2.getDefaultPath()));
		assertTrue(searchProfile1.getLastModifiedID().equals(searchProfile2.getLastModifiedID()));
		assertTrue(searchProfile1.getModifiedDate().equals(searchProfile2.getModifiedDate()));
		assertTrue(searchProfile1.getPipelineName().equals(searchProfile2.getPipelineName()));
		assertTrue(searchProfile1.getProfileName().equals(searchProfile2.getProfileName()));
		assertTrue(searchProfile1.getRankProfileName().equals(searchProfile2.getRankProfileName()));
		assertTrue(searchProfile1.getSearchFieldName().equals(searchProfile2.getSearchFieldName()));
		assertTrue(searchProfile1.getSearchProfileId().equals(searchProfile2.getSearchProfileId()));
		assertFalse(searchProfile1.getSynonymListId().equals(searchProfile2.getSynonymListId()));
		assertTrue(searchProfile1.getTopCategoryId().equals(searchProfile2.getTopCategoryId()));
		assertFalse(searchProfile1.equals(null));
		searchProfile2 = new SearchProfile();
		assertFalse(searchProfile1.equals(searchProfile2));	
		assertFalse(searchProfile2.equals(searchProfile1));
		searchProfile1 = new SearchProfile();
		assertTrue(searchProfile1.equals(searchProfile2));
		assertFalse(searchProfile1.hashCode() != searchProfile2.hashCode());
	}
}
