package com.bestbuy.search.merchandising.unittest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.FacetAttributeValueOrderDAO;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class FacetAttributeValueOrderDAOTest {

	FacetAttributeValueOrderDAO facetAttributeValueOrderDAO = null;
	EntityManager entityManager = null;
	SearchCriteria criteria = null;

	/**
	 * Before test case to create the mocks
	 * Creating the dao Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		facetAttributeValueOrderDAO=new FacetAttributeValueOrderDAO();
		entityManager=mock(EntityManager.class);
		facetAttributeValueOrderDAO.setEntityManager(entityManager);
		criteria = mock(SearchCriteria.class);
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		facetAttributeValueOrderDAO = null;
		entityManager = null;
		criteria=null;
	}

	/**
	 * TestCase for saveMethod
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws DataAcessException{
		List<FacetAttributeValueOrder> facetAttributeValueOrders = BaseData.getFacetAttributeValueOrder();
		FacetAttributeValueOrder facetAttributeValueOrder = facetAttributeValueOrders.get(0);
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				EntityManager mock = (EntityManager) invocation.getMock();
				return  BaseData.getAttribute();
			}
		}).when(entityManager).persist(facetAttributeValueOrder);
		facetAttributeValueOrder = facetAttributeValueOrderDAO.save(facetAttributeValueOrder);
		assertNotNull(facetAttributeValueOrder);
		assertTrue(facetAttributeValueOrder.getFacetAttributeValueOrderPK().getFacet().getFacetId() == 1l);
	}
	
//	@Test 
//	public void testLoad() throws ServiceException, DataAcessException{
//		List<FacetAttributeValueOrder> facetAttributeValueOrders = BaseData.getFacetAttributeValueOrder();
//		List<Long> facetIds = new ArrayList<Long>();
//		//Set the search criteria
//		SearchCriteria criteria = new SearchCriteria();
//		Map<String,Object>	inCriteria = criteria.getInCriteria();
//		facetIds.add(1L);
//		inCriteria.put("obj.facetAttributeValueOrderPK.facet.facetId", facetIds);
//		when(facetAttributeValueOrderDAO.executeQuery(criteria)).thenReturn(facetAttributeValueOrders);
//		facetAttributeValueOrderDAO.loadFacetValues(criteria);
//	}
}
