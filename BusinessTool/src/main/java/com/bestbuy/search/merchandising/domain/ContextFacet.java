package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEXT_FACETS")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for Banner - Facets
 */
public class ContextFacet  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContextFacetPK contextFacetId;
	
	@Column(name = "IS_ACTIVE", length=1)
	private String isActive;

	public ContextFacetPK getContextFacetId() {
		return contextFacetId;
	}

	public void setContextFacetId(ContextFacetPK contextFacetId) {
		this.contextFacetId = contextFacetId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
