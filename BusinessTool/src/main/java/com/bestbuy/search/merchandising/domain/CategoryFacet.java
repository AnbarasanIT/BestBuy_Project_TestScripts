package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bestbuy.search.merchandising.domain.common.DisplayEnum;

/**
 * @author Kalaiselvi Jaganathan
 * Entity for Cateogry Facet
 */

@Entity
@Table(name = "CATEGORY_FACET", uniqueConstraints = @UniqueConstraint(columnNames={"CATEGORY_NODE_ID","DEP_FACET_ID","FACET_ID"}))
@SequenceGenerator(sequenceName="CATG_FACET_SEQ",name="CATG_FACET_GEN")
public class CategoryFacet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CATG_FACET_GEN")
	@Column(name = "CAT_FACET_ID")
	private Long catgFacetId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "APPLY_SUB_CATEGORIES", length=1)
	private DisplayEnum applySubCategory;

	@Column(name = "DISPLAY_ORDER", length=8)
	private Long displayOrder;

	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY", length=1)
	private DisplayEnum display;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DEP_FACET_DISPLAY", length=1)
	private DisplayEnum depFacetDisplay;
	
	@OneToOne
	@JoinColumn(name="CATEGORY_NODE_ID", referencedColumnName="CATEGORY_NODE_ID")
	private Category categoryNode;
	
	@ManyToOne
	@JoinColumn (name = "DEP_FACET_ID",referencedColumnName="FACET_ID") 
	private Facet depFacet;

	@ManyToOne
	@JoinColumn (name = "FACET_ID",referencedColumnName="FACET_ID") 
	private Facet facet;

	@OneToOne
	@JoinColumn(name="DEP_FACET_ATTR_VALUE_ID", referencedColumnName="ATTRIBUTE_VALUE_ID")
	private AttributeValue attributeValue;
	
	@Column(name = "IS_ACTIVE", length=1)
	private String isActive;
	
	/**
	 * @return the catgFacetId
	 */
	public Long getCatgFacetId() {
		return catgFacetId;
	}

	/**
	 * @param To set the catgFacetId
	 */
	public void setCatgFacetId(Long catgFacetId) {
		this.catgFacetId = catgFacetId;
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
	 * @return the display
	 */
	public DisplayEnum getDisplay() {
		return display;
	}

	/**
	 * @param To set the display
	 */
	public void setDisplay(DisplayEnum display) {
		this.display = display;
	}
	
	/**
	 * 
	 * @return
	 */
	public DisplayEnum getDepFacetDisplay() {
		return depFacetDisplay;
	}

	public void setDepFacetDisplay(DisplayEnum depFacetDisplay) {
		this.depFacetDisplay = depFacetDisplay;
	}

	public Category getCategoryNode() {
		return categoryNode;
	}

	public void setCategoryNode(Category categoryNode) {
		this.categoryNode = categoryNode;
	}

	public Facet getDepFacet() {
		return depFacet;
	}

	public void setDepFacet(Facet depFacet) {
		this.depFacet = depFacet;
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

	public DisplayEnum getApplySubCategory() {
		return applySubCategory;
	}

	public void setApplySubCategory(DisplayEnum applySubCategory) {
		this.applySubCategory = applySubCategory;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
}
