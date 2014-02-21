package com.bestbuy.search.merchandising.wrapper;



/**
 * @author Lakshmi Valluripalli
 * Wrapper class for Context Facet entity
 */

public class ContextFacetWrapper extends ContextFacetBaseWrapper{
	
	private Long facetId;
	private Long attributeValueId;

	/**
	 * @return the facetId
	 */
	public Long getFacetId() {
		return facetId;
	}
	
	/**
	 * @param to set the facetId 
	 */
	public void setFacetId(Long facetId) {
		this.facetId = facetId;
	}
	
	/**
	 * returns the AttributeValueId
	 * @return
	 */
	public Long getAttributeValueId() {
		return attributeValueId;
	}
	
	/**
	 * sets the AttributeValueId
	 * @param attributeValueId
	 */
	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
}
