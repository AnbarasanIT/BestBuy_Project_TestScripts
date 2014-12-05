package com.bestbuy.search.merchandising.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Kalaiselvi Jaganathan
 * Composite key for facetAttribute Value order entity
 */
public class FacetAttributeValueOrderPK implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="FACET_ID", referencedColumnName="FACET_ID")
	private Facet facet;
	
	@ManyToOne
	@JoinColumn(name ="ATTRIBUTE_VALUE_ID",referencedColumnName="ATTRIBUTE_VALUE_ID")
	private AttributeValue attributeValue;
	
	/**
	 * returns the facet
	 * @return
	 */
	public Facet getFacet() {
		return facet;
	}
	
	/**
	 * sets the facet
	 * @param facet
	 */
	public void setFacet(Facet facet) {
		this.facet = facet;
	}
	
	/**
	 * returns the attribute value
	 * @return
	 */
	public AttributeValue getAttributeValue() {
		return attributeValue;
	}
	
	/**
	 * sets the attribute value
	 * @param attributeValue
	 */
	public void setAttributeValue(AttributeValue attributeValue) {
		this.attributeValue = attributeValue;
	}
	


}
