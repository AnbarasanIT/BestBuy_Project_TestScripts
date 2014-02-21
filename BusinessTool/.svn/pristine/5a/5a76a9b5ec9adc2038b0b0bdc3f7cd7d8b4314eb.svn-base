package com.bestbuy.search.merchandising.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryFacetDAO;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.unittest.common.BaseData;
import com.bestbuy.search.merchandising.unittest.common.FacetDisplayOrderTestHelper;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Unit test for category facet service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath*:/spring/it-applicationContext*.xml"})
public class CategoryFacetServiceTest {
	@InjectMocks
	private CategoryFacetService categoryFacetService;
	
	@Mock
	private CategoryFacetDAO categoryFacetDAO;
	
	@Mock
	private CategoryNodeService categoryNodeService;
	private FacetDisplayOrderTestHelper helper;	

	/**
	 * Before test case to create the mocks
	 * Creating the Service Layer and its dependent Mocks
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		categoryFacetService.setBaseDAO(categoryFacetDAO);
		helper = new FacetDisplayOrderTestHelper();
	}

	/**
	 * Clear all the Assigned Variables
	 */
	@After
	public void Destroy(){
		categoryFacetService = null;
		categoryFacetDAO = null;

	}

	/**
	 * TestCase for saveMethod
	 * @throws ServiceException
	 * @throws DataAcessException 
	 */
	@Test
	public void testSave() throws ServiceException, DataAcessException{
		CategoryFacet categoryFacet = BaseData.getCategoryFacet().get(0);
		when(categoryFacetService.save(categoryFacet)).thenReturn(categoryFacet);
		categoryFacet=categoryFacetDAO.save(categoryFacet);
		assertNotNull(categoryFacet);
	}

	/**
	 * Test load facet category method
	 * @throws ServiceException
	 * @throws DataAcessException
	 */
	@Test
	public void testLoadFacetCategory() throws ServiceException, DataAcessException{
		String catgId="Pmcat23";
		List<CategoryFacet> categoryFacets = BaseData.getCategoryFacet();
		when(categoryFacetDAO.loadFacetForCatg(Mockito.any(SearchCriteria.class))).thenReturn(categoryFacets);
		categoryFacetService.loadFacetCategory(catgId);
	}

	/**
	 * Test the data access exception
	 * @throws ServiceException
	 * @throws DataAcessException
	 */
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testLoadFacetCategoryDaoExcep() throws ServiceException, DataAcessException{
		when(categoryFacetDAO.loadFacetForCatg(Mockito.any(SearchCriteria.class))).thenThrow(DataAcessException.class);
		categoryFacetService.loadFacetCategory("Pmcat23");
	}

	
	@Test
	public void testInvalidateContextsForFacet() throws ServiceException, DataAcessException {
		Facet facet = helper.getFacet1();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		when(categoryFacetDAO.loadContextsForFacetForInvalidation(facet)).thenReturn(categoryFacets);
		
		for(CategoryFacet categoryFacet : categoryFacets) {
			when(categoryFacetDAO.update(categoryFacet)).thenReturn(categoryFacet);
		}
		
		categoryFacetService.invalidateContextsForFacet(facet);
		
		for(CategoryFacet categoryFacet : categoryFacets) {
			Assert.assertTrue(categoryFacet.getIsActive().equals("N"));
		}
	}
		
	@SuppressWarnings("unchecked")
	@Test(expected = ServiceException.class)
	public void testInvalidateContextsForFacetServiceExceptionDAOExcep() throws DataAcessException, ServiceException {
		Facet facet = helper.getFacet1();
		List<CategoryFacet> categoryFacets = helper.getCategoryFacets();
		when(categoryFacetDAO.loadContextsForFacetForInvalidation(facet)).thenThrow(ServiceException.class);
		
		for(CategoryFacet categoryFacet : categoryFacets) {
			when(categoryFacetDAO.update(categoryFacet)).thenReturn(categoryFacet);
		}
		
		categoryFacetService.invalidateContextsForFacet(facet);	
	}	
	
	@Test
	@SuppressWarnings("unchecked")
	public void testLoadDepFacetsForCategory() throws ServiceException {
		// Data setup
		List<CategoryFacet> categoryFacets = new ArrayList<CategoryFacet>();
		CategoryFacet categoryFacet1 = new CategoryFacet();
		categoryFacet1.setCatgFacetId(1L);
		categoryFacet1.setFacet(new Facet());
		categoryFacet1.getFacet().setFacetId(18100L);
		categoryFacet1.setCategoryNode(new Category());
		categoryFacet1.getCategoryNode().setCategoryNodeId("pcmcat249700050006");
		
		CategoryFacet categoryFacet2 = new CategoryFacet();
		categoryFacet2.setCatgFacetId(2L);
		categoryFacet2.setFacet(new Facet());
		categoryFacet2.getFacet().setFacetId(18150L);
		categoryFacet2.setCategoryNode(new Category());
		categoryFacet2.getCategoryNode().setCategoryNodeId("pcmcat249700050006");
		
		categoryFacets.add(categoryFacet1);
		categoryFacets.add(categoryFacet2);
		
		List<String> parentPaths = new ArrayList<String>();
		parentPaths.add("Best%20Buy|Home");
		parentPaths.add("Best%20Buy");
		when(categoryNodeService.getCategoryPath("pcmcat249700050006")).thenReturn("Best%20Buy|Home|Furniture");
		when(categoryFacetDAO.loadDepFacetsForCategory(Mockito.isA(String.class), Mockito.isA(List.class), Mockito.isA(String.class))).thenReturn(categoryFacets);
		
		List<IWrapper> result = categoryFacetService.loadDepFacetsForCategory("pcmcat249700050006", "18101");
		assertNotNull(result);
		assertTrue(result.size() == 2);
		int i=0;
		for (IWrapper wrapper : result) {
			CategoryFacetWrapper w = (CategoryFacetWrapper) wrapper;
			if (i == 0) {
				assertEquals(w.getFacetId().longValue(), 18100L);
			}
			if (i == 1) {
				assertEquals(w.getFacetId().longValue(), 18150L);
			}
			i++;
		}
	}
}
