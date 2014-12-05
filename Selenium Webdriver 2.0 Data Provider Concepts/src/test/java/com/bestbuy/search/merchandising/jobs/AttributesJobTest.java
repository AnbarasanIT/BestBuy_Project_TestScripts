package com.bestbuy.search.merchandising.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.AttributeDAO;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.service.AttributeService;
import com.bestbuy.search.merchandising.service.SolrSearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/it-applicationContext*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class AttributesJobTest extends BaseJobTest {
	@InjectMocks
	private AttributeService attributesService;
	
	@Mock
	private SolrSearchService solrSearchService;
	private AttributeDAO baseDAO;
	
	@Autowired
	private SharedEntityManagerContainer entityManagerContainer;
	
	/**
	 * Before method (runs before every test method)
	 * 
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void before() throws SchedulerException {
		MockitoAnnotations.initMocks(this);
		baseDAO = mock(AttributeDAO.class);
		Mockito.doCallRealMethod().when(baseDAO).setEntityManager(Mockito.any(EntityManager.class));
		Mockito.doCallRealMethod().when(baseDAO).setEntityClass(Mockito.any(Class.class));
		baseDAO.setEntityManager(entityManagerContainer.getEntityManager());
		baseDAO.setEntityClass(Attribute.class);
		attributesService.setDao(baseDAO);
	}
	
	/**
	 * Method to test execute
	 * 
	 */
	@Test
	public void testExecute_NoDataInDb() throws DataAcessException, ServiceException {
		QueryResponse response = mockSolrResponse_testExecute();
		mockAttributes_testExecute();
		attributesService.importSolrData();
		performAsserts_testExecute(response);
	}
	
	/**
	 * Method to test execute
	 * 
	 */
	@Test
	public void testExecute_ValidData() throws DataAcessException, ServiceException {
		mockSolrResponse_testExecute();
		Map<String, List<String>> attributesAndValues = mockAttributes_testExecute_ValidData();
		attributesService.importSolrData();
		performAsserts_testExecute_ValidData(attributesAndValues);
	}
	
	/**
	 * Method to test execute
	 * 
	 */
	@Test(expected=ServiceException.class)
	public void testExecute_NullSolrResponse() throws DataAcessException, ServiceException {
		mockSolrResponse_testExecute_NullSolrResponse();
		attributesService.importSolrData();
	}
	
	/**
	 * Method to perform asserts for testExecute_ValidData() method
	 * 
	 * @param attributesAndValues
	 */
	private void performAsserts_testExecute_ValidData(Map<String, List<String>> attributesAndValues) {
		List<String> categoryList = attributesAndValues.get("junitcategory_facet");
		List<String> brandList = attributesAndValues.get("junitbrand_facet");
		List<String> currentOffersList = attributesAndValues.get("junitcurrentoffers_facet");
		
		assertTrue(categoryList.size() == 1);
		assertTrue(brandList.size() == 1);
		assertTrue(currentOffersList.size() == 1500);
		
		assertEquals(categoryList.get(0), "Home");
		assertEquals(brandList.get(0), "Sony");
		assertEquals(currentOffersList, this.getDummyAttributeValueData());
	}
	
	/**
	 * Method to perform asserts for testExecute() method
	 * 
	 * @param response
	 * @throws ServiceException
	 */
	private void performAsserts_testExecute(QueryResponse response) throws ServiceException {
		List<FacetField> facetFields = response.getFacetFields();
		Map<String, String> facetFieldNameMap = new HashMap<String, String>();
		
		// Fetch attributes from db
		List<Attribute> attributesDB = (List<Attribute>) attributesService.retrieveAll();
		assertNotNull(attributesDB);
		
		for (FacetField ff : facetFields) {
			if (!ff.getName().equals("categories_facet")) {
				facetFieldNameMap.put(ff.getName(), ff.getName());
			}
		}
		
		for (Attribute attribute : attributesDB) {
			if (facetFieldNameMap.containsKey(attribute.getAttributeName())) {
				facetFieldNameMap.remove(facetFieldNameMap.get(attribute.getAttributeName()));
			}
		}
		assertTrue(facetFieldNameMap.size() == 0);
	}
	
	/**
	 * Method to mock attributes
	 * 
	 */
	private void mockAttributes_testExecute() throws DataAcessException {
		when(baseDAO.getFacetFieldsFromDatabase()).thenReturn(null);
		when(baseDAO.save(Mockito.any(Attribute.class))).thenCallRealMethod();
		when(baseDAO.retrieveAll()).thenCallRealMethod();
	}
	
	/**
	 * Method to mock attributes
	 * 
	 */
	private Map<String, List<String>> mockAttributes_testExecute_ValidData() throws DataAcessException {
		List<String> dbList = new ArrayList<String>();
		dbList.add("junitcategory_facet");
		dbList.add("junitbrand_facet");
		dbList.add("junitcurrentoffers_facet");
		when(baseDAO.getFacetFieldsFromDatabase()).thenReturn(dbList);
		when(baseDAO.getAttributeIdForFacetField("junitcategory_facet")).thenReturn(1L);
		when(baseDAO.getAttributeIdForFacetField("junitbrand_facet")).thenReturn(2L);
		when(baseDAO.getAttributeIdForFacetField("junitcurrentoffers_facet")).thenReturn(3L);
		
		List<String> a1List = new ArrayList<String>();
		a1List.add("Movies");
		a1List.add("Home");
		
		List<String> a2List = new ArrayList<String>();
		a2List.add("Agptek");
		a2List.add("Sony");
		
		List<String> a3List = new ArrayList<String>();
		a3List.add("On Sale");
		a3List.addAll(getDummyAttributeValueData());
		when(baseDAO.getFacetFieldValuesByFacetField(1L)).thenReturn(a1List);
		when(baseDAO.getFacetFieldValuesByFacetField(2L)).thenReturn(a2List);
		when(baseDAO.getFacetFieldValuesByFacetField(3L)).thenReturn(a3List);
		
		Map<String, List<String>> attributesAndValues = new HashMap<String, List<String>>();
		attributesAndValues.put("junitcategory_facet", a1List);
		attributesAndValues.put("junitbrand_facet", a2List);
		attributesAndValues.put("junitcurrentoffers_facet", a3List);
		return attributesAndValues;
	}
	
	/**
	 * Method to mock solr response
	 * 
	 * @return QueryResponse
	 * @throws ServiceException
	 */
	private QueryResponse mockSolrResponse_testExecute() throws ServiceException {
		QueryResponse response = this.getQueryResponse(false);
		when(solrSearchService.loadFacetFields()).thenReturn(response);
		return response;
	}
	
	/**
	 * Method to mock solr response with null response
	 * 
	 * @throws ServiceException
	 */
	private void mockSolrResponse_testExecute_NullSolrResponse() throws ServiceException {
		when(solrSearchService.loadFacetFields()).thenReturn(null);
	}
}
