package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.FacetAttributeValueOrderDAO;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.service.FacetAttributeValueOrderService;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test case for FacetAttributeValueOrderService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class FacetAttributeValueOrderServiceTest {
	FacetAttributeValueOrderDAO facetAttributeValueOrderDAO;
	FacetAttributeValueOrderService facetAttributeValueOrderService;

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		facetAttributeValueOrderService = new FacetAttributeValueOrderService();
		facetAttributeValueOrderDAO = mock(FacetAttributeValueOrderDAO.class);
		facetAttributeValueOrderService.setDao(facetAttributeValueOrderDAO);
		facetAttributeValueOrderService.setFacetAttributeValueOrderDAO(facetAttributeValueOrderDAO);
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		facetAttributeValueOrderService = null;
		facetAttributeValueOrderDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException 
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		FacetAttributeValueOrder facetAttributeValueOrder = BaseData.getFacetAttributeValueOrder().get(0);
		when(facetAttributeValueOrderService.save(facetAttributeValueOrder)).thenReturn(facetAttributeValueOrder);
		facetAttributeValueOrder=facetAttributeValueOrderDAO.save(facetAttributeValueOrder);
		assertNotNull(facetAttributeValueOrder);
	}

	/**
	 * Test load method
	 * @throws ServiceException
	 */
	@Test
	public void testload() throws ServiceException{
		Long facetId=1L;
		List<IWrapper> attributeValueWrappers = facetAttributeValueOrderService.loadFacetValues(facetId);
		assertNotNull(attributeValueWrappers);
	}
	
	/**
	 * Test the data access exception
	 * @throws ServiceException
	 * @throws DataAcessException
	 */
	@Test(expected = DataAcessException.class)
	@SuppressWarnings("unchecked")
	public void testLoadFacetCategoryDaoExcep() throws ServiceException, DataAcessException{
		Long facetId=1L;
		when(facetAttributeValueOrderDAO.loadFacetValues(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		facetAttributeValueOrderService.loadFacetValues(facetId);
	}
}
