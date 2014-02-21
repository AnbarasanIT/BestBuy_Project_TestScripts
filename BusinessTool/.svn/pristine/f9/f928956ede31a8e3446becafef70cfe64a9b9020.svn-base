package com.bestbuy.search.merchandising.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Kalaiselvi Jaganathan
 * Entity for Banner - Facets
 */
@Embeddable
public class ContextFacetPK implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn (name = "CONTEXT_ID",referencedColumnName="CONTEXT_ID") 
	private Context context;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FACET_ID", referencedColumnName="FACET_ID")
	private Facet facet;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name ="ATTRIBUTE_VALUE_ID",referencedColumnName="ATTRIBUTE_VALUE_ID")
	private AttributeValue attributeValue;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Facet getFacet() {
		return facet;
	}

	public void setFacet(Facet facet) {
		this.facet = facet;
	}
	
	public AttributeValue getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(AttributeValue attributeValue) {
		this.attributeValue = attributeValue;
	}

	
}
