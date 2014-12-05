package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.bestbuy.search.merchandising.domain.common.DisplayEnum;

@Entity
@Table(name = "FACET_ATTR_VALUE_ORDER")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for Facet Attribute Value Order
 */
public class FacetAttributeValueOrder  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacetAttributeValueOrderPK facetAttributeValueOrderPK;

	@Column(name = "DISPLAY_ORDER", length=8)
	@OrderBy(value="desc")
	private Long displayOrder;

	@Enumerated(EnumType.STRING)
	@Column(name = "DO_NOT_DISPLAY", length=1)
	private DisplayEnum facetDoNotInclude;
	
	

	/**
	 * @return the facetAttributeValueOrderPK
	 */
	public FacetAttributeValueOrderPK getFacetAttributeValueOrderPK() {
		return facetAttributeValueOrderPK;
	}

	/**
	 * @param To set the facetAttributeValueOrderPK
	 */
	public void setFacetAttributeValueOrderPK(
			FacetAttributeValueOrderPK facetAttributeValueOrderPK) {
		this.facetAttributeValueOrderPK = facetAttributeValueOrderPK;
	}

	/**
	 * @return the displayOrder
	 */
	public Long getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param To set the displayOrder
	 */
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the facetInclude
	 */
	public DisplayEnum getFacetDoNotInclude() {
		return facetDoNotInclude;
	}

	/**
	 * @param To set the facetInclude
	 */
	public void setFacetDoNotInclude(DisplayEnum facetDoNotInclude) {
		this.facetDoNotInclude = facetDoNotInclude;
	}
}
