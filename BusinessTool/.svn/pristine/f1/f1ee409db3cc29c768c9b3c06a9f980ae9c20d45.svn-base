package com.bestbuy.search.merchandising.unittest.dao;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryFacetDAO;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.unittest.common.BaseData;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class CategoryFacetDAOTest {

	CategoryFacetDAO categoryFacetDAO = null;
	EntityManager entityManager = null;

	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		categoryFacetDAO=new CategoryFacetDAO();
		entityManager=mock(EntityManager.class);
		categoryFacetDAO.setEntityManager(entityManager);

	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		categoryFacetDAO = null;
		entityManager = null;
	}

	/**
	 * TestCase for saveMethod
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws DataAcessException{
		List<CategoryFacet> categoryFacetOrders = BaseData.getCategoryFacet();
		CategoryFacet categoryFacetOrder = categoryFacetOrders.get(0);
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				EntityManager mock = (EntityManager) invocation.getMock();
				return  BaseData.getCategoryFacet();
			}
		}).when(entityManager).persist(categoryFacetOrder);
		categoryFacetOrder = categoryFacetDAO.save(categoryFacetOrder);
		assertNotNull(categoryFacetOrder);
		assertTrue(categoryFacetOrder.getCatgFacetId()== 1L);
	}

	@Test @Ignore
	public void testLoadFacetForCatg() throws DataAcessException, ServiceException {
		SearchCriteria criteria = new SearchCriteria();
		List<String> catgIds = new ArrayList<String>();
		List<Long> status = new ArrayList<Long>();
		Map<String,Object>	inCriteria = criteria.getInCriteria();
		String catgId = "pmcat123";
		catgIds.add(catgId);
		status.add(APPROVE_STATUS);
		inCriteria.put("obj.categoryNode.categoryNodeId", catgIds);
		inCriteria.put("obj.facet.status.statusId", status);
		criteria.setOrderByCriteria("obj.facet.displayName asc");
		categoryFacetDAO.loadFacetForCatg(criteria);
	}

}
