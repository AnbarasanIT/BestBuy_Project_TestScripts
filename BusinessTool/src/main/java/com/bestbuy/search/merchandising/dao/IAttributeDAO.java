package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.domain.Attribute;

/**
 * @author Kalaiselvi Jaganathan
 * DAO structure for AttributeDAO
 */
public interface IAttributeDAO extends IBaseDAO<Long,Attribute>{
	/**
	 * Method to get facet fields from database
	 * 
	 * @return List<String>
	 */
	public List<String> getFacetFieldsFromDatabase();
	
	/**
	 * Method to get attribute id for facet field
	 * 
	 * @param facetFieldName
	 * @return String facetFieldName
	 */
	public Long getAttributeIdForFacetField(String facetFieldName);
	
	/**
	 * Method to get facet field values by facet field
	 * 
	 * @param attributeId
	 * @return List<String>
	 */
	public List<String> getFacetFieldValuesByFacetField(final Long attributeId);
	
	/**
	 * Method to insert facet values
	 * 
	 * @param insertFacetFieldValues
	 * @param attributeId
	 */
	public void insertFacetValues(final List<String> insertFacetFieldValues,
			final Long attributeId);
	
	/**
	 * Method to update facet values
	 * 
	 * @param updateFacetFieldValues
	 * @param attributeId
	 */
	public void updateFacetValues(final List<String> updateFacetFieldValues, Long attributeId);
	
	/**
	 * Method to invalidate attribute values
	 * 
	 * @param attributesDB
	 */
	public void invalidateAttributeValues(List<String> attributesDB);
	
	/**
	 * This Method will invalidate the Category Dependent Facet if its AttrValue is Invalid
	 * 
	 */
	public void invalidateDepCategoryFacets();
	
	/**
	 * This Method will invalidate the Facets if all the Dependent Facet Values are Invalid
	 * 
	 */
	public void invalidateFacets();
	
	/**
	 * This method will invalidated the Banner Dependent facet if its facet Value is Invalid
	 * 
	 */
	public void invalidBannerFacets();
	
	/**
	 * This Method will invalidate the Banners if the Banner Dependent facet Values are invalid
	 * 
	 */
	public void invalidateBanners();
}
