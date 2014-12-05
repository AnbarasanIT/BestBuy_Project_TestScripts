/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.ErrorType.ATTRIBUTES_JOB;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.MAX_EXPECTED_VARIANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.SortOrder;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.AttributeDAO;
import com.bestbuy.search.merchandising.dao.IAttributeValueDAO;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.AttributeValue;

/**
 * Service layer for the attribute Entity
 * @author Lakshmi.Valluripalli
 */
public class AttributeService extends BaseService<Long,Attribute> implements IAttributeService {
	private final static BTLogger logger = BTLogger.getBTLogger(AttributeService.class.getName());
	
	/**
	 * Wires the instance of AttributeMetaDAO
	 * @param dao
	 */
	@Autowired
	public void setDao(AttributeDAO dao) {
		this.baseDAO = dao;
	}
	
	@Autowired
	private IAttributeValueDAO attributeValueDAO;
	
	@Autowired
	private SolrSearchService solrSearchService;
	
	
	/**
	 * Method to load attributes
	 * 
	 * @return List<Attribute>
	 * @throws ServiceException
	 */
	public List<Attribute> loadAttributes() throws ServiceException {
		List<Attribute> attributes = null;
		try {
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
			Map<String, SortOrder> orderCriteria = criteria.getOrderCriteria();
			notEqCriteria.put("isActive", "N");
			orderCriteria.put("attributeName", SortOrder.ASC);
			criteria.setPaginationRequired(false);
			attributes = (List<Attribute>) ((AttributeDAO) baseDAO).executeQueryWithCriteria(criteria);
		} catch (DataAcessException dae) {
			throw new ServiceException("Error while loading Attribute Names",
					dae);
		}
		return attributes;
	}
	
	/**
	 * Method to save attribute
	 * 
	 * @param Attribute attribute
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void saveAttribute(Attribute attribute) throws ServiceException {
		try {
			((AttributeDAO)baseDAO).save(attribute);
		} catch(DataAcessException dae) {
			throw new ServiceException("Error while Saving the Attribute ",dae);
		}
	}
	
	/**
	 * Method to do a partial match on attribute
	 * 
	 * @param String attributeName
	 * @return List<Attribute>
	 * @throws ServiceException
	 */
	public List<Attribute> partialMatchAttribute(String attributeName) throws ServiceException {
		List<Attribute> attributes = null;
		try {
			SearchCriteria criteria = new SearchCriteria();
			Map<String, Object> likeCriteria = criteria.getLikeCriteria();
			likeCriteria.put("obj.attributeName", attributeName);
			Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
			notEqCriteria.put("obj.isActive", "N");
			attributes = (List<Attribute>)((AttributeDAO)baseDAO).executeQuery(criteria);
		} catch(DataAcessException dae) {
			throw new ServiceException("Error while Loading the attribute "+attributeName,dae);
		}
		return attributes;
	}
	
	/**
	 * Load AttributeValues for a specific Attribute
	 * 
	 * @param attributeId
	 * @return List<AttributeValue>
	 * @throws ServiceException
	 */
	public List<AttributeValue> loadAttributeValues(Long attributeId) throws ServiceException{
		List<AttributeValue> attributeValues = null;
		try {
			SearchCriteria criteria = new SearchCriteria();
			Map<String, Object> whereCriteria = criteria.getSearchTerms();
			whereCriteria.put("obj.attribute.attributeId", attributeId);
			Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
			notEqCriteria.put("obj.isActive", "N");
			criteria.getOrderCriteria().put("obj.attributeValue", SortOrder.ASC);
			attributeValues = (List<AttributeValue>)attributeValueDAO.executeQuery(criteria);
		} catch(DataAcessException dae) {
			throw new ServiceException("Error while Loading the attribute "+attributeId,dae);
		}
		return attributeValues;
	}
	
	/**
	 * Method to import solr data
	 * 
	 * @throws ServiceException
	 */
	public void importSolrData() throws ServiceException {
		QueryResponse queryResponse = solrSearchService.loadFacetFields();
		logger.info("****** Query response for attributes from solr ********"+ queryResponse);
		if (queryResponse != null && queryResponse.getFacetFields() != null) {
			// Get facet fields from database
			List<String> attributesDB = ((AttributeDAO)baseDAO).getFacetFieldsFromDatabase();
			int DbAttributeCount = 0;
			if(attributesDB != null){
				DbAttributeCount = attributesDB.size() ;
			}
			int intersectionCount = 0;
			
			for (FacetField facetField : queryResponse.getFacetFields()) {
				String facetFieldName = facetField.getName();
				// Not processing categories_facet
				if (facetFieldName.equalsIgnoreCase("categories_facet")) {
					continue;
				}
				
				// Check if the solr facet field is available in db
				if (attributesDB != null && attributesDB.contains(facetFieldName)) {
					intersectionCount++;
					attributesDB.remove(facetFieldName);
					Long attributeId = ((AttributeDAO)baseDAO).getAttributeIdForFacetField(facetField.getName());
					
					// Get facet field values for facet field id
					List<String> attributeValuesDB = ((AttributeDAO)baseDAO).getFacetFieldValuesByFacetField(attributeId);
					List<String> insertFacetFieldValues = new ArrayList<String>();
					List<String> solrFacetFieldValues = new ArrayList<String>();
					if (facetField.getValues() != null && facetField.getValues().size() > 0) {
						for (Count count : facetField.getValues()) {
							insertFacetFieldValues.add(count.getName().trim());
							solrFacetFieldValues.add(count.getName().trim());
						}
						if (attributeValuesDB != null && attributeValuesDB.size() > 0) {
							insertFacetFieldValues.removeAll(attributeValuesDB);
							attributeValuesDB.removeAll(solrFacetFieldValues);
						}
					}
					
					// Insert facet values into database
					((AttributeDAO)baseDAO).insertFacetValues(insertFacetFieldValues, attributeId);
					if (attributeValuesDB != null && attributeValuesDB.size() > 0) {
						if(attributeValuesDB.size() > 1000){
							List<String> subListAttributeValuesDBs = null;
							int iterations = new Double(Math.ceil(new Double(attributeValuesDB.size()) / new Double(1000))).intValue();
							for (int i = 0 ; i < iterations ; i++) {
								int start = i * 1000;
								int end = start + 1000;
								if (end > attributeValuesDB.size()) {
									end = attributeValuesDB.size();
								}
								subListAttributeValuesDBs = attributeValuesDB.subList(start, end);
								((AttributeDAO)baseDAO).updateFacetValues(subListAttributeValuesDBs, attributeId);
							}
						} else {
							((AttributeDAO)baseDAO).updateFacetValues(attributeValuesDB, attributeId);
						}
					}
				} else {
					saveFacetFields(facetField);
				}
			}
			/* Splunking if more than Expected variance found between incoming
					solr response and existing DB */

			this.splunkUnexpectedFacetFieldVariation(queryResponse
					.getFacetFields().size(), intersectionCount,
					attributesDB, DbAttributeCount);

			((AttributeDAO)baseDAO).invalidateAttributeValues(attributesDB);
			// Update the Category_Facet Table
			((AttributeDAO)baseDAO).invalidateDepCategoryFacets();
			// invalidate the banners
			((AttributeDAO)baseDAO).invalidateFacets();
			// Update the Context_Facets Table
			((AttributeDAO)baseDAO).invalidBannerFacets();
			// invalidate the facets
			((AttributeDAO)baseDAO).invalidateBanners();
		} else {
			throw new ServiceException("Solr Response is empty");
		}
	}
	
	/**
	 * Method to save facet fields to the database
	 * 
	 * @param facetField
	 * @throws ServiceException
	 */
	private void saveFacetFields(FacetField facetField) throws ServiceException {
		Attribute attribute = new Attribute();
		attribute.setAttributeName(facetField.getName());
		List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
		for (Count count : facetField.getValues()) {
			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setAttribute(attribute);
			attributeValue.setAttributeValue(count.getName());
			attributeValues.add(attributeValue);
		}
		attribute.setAttributeValue(attributeValues);
		this.saveAttribute(attribute);
	}
	
	/**
	 * Method to add splunk alert for more than 10% variation between incoming
	 * solr response and existing DB Facet fields
	 * @author chanchal.kumari
	 */

	private void splunkUnexpectedFacetFieldVariation(int solrCount,
			int intersectionCount, List<String> extraDBFacetFieldList, int DbCount) {
		int extraDBAttributeCount = 0;
		if(extraDBFacetFieldList != null){
			extraDBAttributeCount = extraDBFacetFieldList.size();
		}
		int extraSolrCount = solrCount - intersectionCount;
		double percentageDifference = ((new Double(extraSolrCount
				+ extraDBAttributeCount)).doubleValue() / (new Double(solrCount
				+ DbCount)).doubleValue()) * 100;
		if (percentageDifference > MAX_EXPECTED_VARIANCE) {
			logger.error(
					"AttributesJob",
					ATTRIBUTES_JOB,
					"More than "+ MAX_EXPECTED_VARIANCE +"% variance found between incoming " +
							"solr response and existing DB facet fields");
		}
	}
}
