package com.bestbuy.search.merchandising.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.domain.common.DisplayModeEnum;

/**
 * @author Kalaiselvi Jaganathan
 * Entity for facets
 */
@Entity
@Table(name = "FACETS")
@SequenceGenerator(sequenceName="FACET_SEQ",name="FACET_GEN")
public class Facet extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "FACET_GEN")
	@Column(name = "FACET_ID", nullable=false, length=19)
	private Long facetId;

	@Column(name = "SYSTEM_NAME", nullable=false, length=100)
	private String systemName;

	@Column(name = "DISPLAY_NAME", nullable=false, length=100)
	private String displayName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_MODE", length=15)
	private DisplayModeEnum displayMode;
	
	//To show in UI whether it is a displayed or Hidden Facet
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY", length=1)
	private DisplayEnum display;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_RECURRENCE", length=1)
	private DisplayEnum displayRecurrence;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_FACET_REMOVE_LNK", length=1)
	private DisplayEnum displayFacetRemoveLink;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_MOB_FACET", length=1)
	private DisplayEnum displayMobileFacet;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_MOB_FACET_REMOVE_LNK", length=1)
	private DisplayEnum displayMobileFacetRemoveLink;
	
	@Column(name = "GLOSSARY_ITEM", length=10)
	private Long glossaryItem;

	@Column(name = "MIN_ATTR_VALUE", nullable = true, length=2)
	private Long minAttrValue;

	@Column(name = "MAX_ATTR_VALUE", nullable = true, length=3)
	private Long maxAttrValue;

	@Column(name = "POSITION", length=10)
	private String position;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ATTRIBUTE_ID", referencedColumnName="ATTRIBUTE_ID")
	private Attribute attribute;

	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn (name = "FACET_ID",referencedColumnName="FACET_ID", insertable = false, updatable = false) 
	private List<FacetAttributeValueOrder> facetAttributeOrder = new ArrayList<FacetAttributeValueOrder> ();

	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)   
	@JoinColumn (name = "FACET_ID",referencedColumnName="FACET_ID", insertable = false, updatable = false) 
	private List<CategoryFacet> categoryFacet = new ArrayList<CategoryFacet>();
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn (name = "FACET_ID",referencedColumnName="FACET_ID", insertable = false, updatable = false)
	private List<FacetsDisplayOrder> facetDisplayOrder = new ArrayList<FacetsDisplayOrder>();
	
	@Column(name = "SORT_TYPE")
	private String attrValSortType;

	/**
	 * Method to get facet display order
	 * 
	 * @return List<FacetsDisplayOrder>
	 */
	public List<FacetsDisplayOrder> getFacetDisplayOrder() {
		return facetDisplayOrder;
	}

	/**
	 * Method to set facet display order
	 * 
	 * @param facetDisplayOrder
	 */
	public void setFacetDisplayOrder(List<FacetsDisplayOrder> facetDisplayOrder) {
		this.facetDisplayOrder = facetDisplayOrder;
	}

	/**
	 * @return the facetId
	 */
	public Long getFacetId() {
		return facetId;
	}

	/**
	 * @param To set the facetId
	 */
	public void setFacetId(Long facetId) {
		this.facetId = facetId;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param To set the systemName
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param To set the displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the displayMode
	 */
	public DisplayModeEnum getDisplayMode() {
		return displayMode;
	}

	/**
	 * @param To set the displayMode
	 */
	public void setDisplayMode(DisplayModeEnum displayMode) {
		this.displayMode = displayMode;
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
	 * @return the glossaryItem
	 */
	public Long getGlossaryItem() {
		return glossaryItem;
	}

	/**
	 * @param To set the glossaryItem
	 */
	public void setGlossaryItem(Long glossaryItem) {
		this.glossaryItem = glossaryItem;
	}

	/**
	 * @return the minAttrValue
	 */
	public Long getMinAttrValue() {
		return minAttrValue;
	}

	/**
	 * @param To set the minAttrValue
	 */
	public void setMinAttrValue(Long minAttrValue) {
		this.minAttrValue = minAttrValue;
	}

	/**
	 * @return the maxAttrValue
	 */
	public Long getMaxAttrValue() {
		return maxAttrValue;
	}

	/**
	 * @param To set the maxAttrValue
	 */
	public void setMaxAttrValue(Long maxAttrValue) {
		this.maxAttrValue = maxAttrValue;
	}
	
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param To set the position
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the facetAttributeOrder
	 */
	public List<FacetAttributeValueOrder> getFacetAttributeOrder() {
		return facetAttributeOrder;
	}

	/**
	 * @param To set the facetAttributeOrder
	 */
	public void setFacetAttributeOrder(
			List<FacetAttributeValueOrder> facetAttributeOrder) {
		this.facetAttributeOrder = facetAttributeOrder;
	}
	
	 public void addFacetAttributeOrder(
	      FacetAttributeValueOrder facetAttributeValueOrder) {
	   facetAttributeOrder.add(facetAttributeValueOrder);
	  }
	
	
	public void addFacetAttributeOrders(
      List<FacetAttributeValueOrder> facetAttributeOrders) {
    facetAttributeOrder.addAll(facetAttributeOrders);
  }
	

	/**
	 * @return the categoryFacet
	 */
	public List<CategoryFacet> getCategoryFacet() {
		return categoryFacet;
	}

	/**
	 * @param To set the categoryFacet
	 */
	public void setCategoryFacet(List<CategoryFacet> categoryFacet) {
		this.categoryFacet = categoryFacet;
	}
	
	/**
   * @param To set the categoryFacet
   */
  public void addCategoryFacets(List<CategoryFacet> categoryFacets) {
    this.categoryFacet.addAll(categoryFacets);
  }
	
	/**
	 * returns the Attribute Information
	 * @return
	 */
	public Attribute getAttribute() {
		return attribute;
	}
	
	/**
	 * sets the Attribute
	 * @param attribute
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}	
	
	/**
	 * get AttributeValue SortType
	 * @return
	 */
	public String getAttrValSortType() {
		return attrValSortType;
	}
	
	/**
	 * set AttrValueSort Type
	 * @param attrValSortType
	 */
	public void setAttrValSortType(String attrValSortType) {
		this.attrValSortType = attrValSortType;
	}
	
	/**
	 * get Display Recurrence
	 * @return
	 */
	public DisplayEnum getDisplayRecurrence() {
		return displayRecurrence;
	}
	
	/**
	 * set Display Recurrence
	 * @param displayRecurrence
	 */
	public void setDisplayRecurrence(DisplayEnum displayRecurrence) {
		this.displayRecurrence = displayRecurrence;
	}

	/**
	 * gets Display Facet Remove Link
	 * @return
	 */
	public DisplayEnum getDisplayFacetRemoveLink() {
		return displayFacetRemoveLink;
	}
	
	/**
	 * sets Display Facet Remove Link
	 * @param displayFacetRemoveLink
	 */
	public void setDisplayFacetRemoveLink(DisplayEnum displayFacetRemoveLink) {
		this.displayFacetRemoveLink = displayFacetRemoveLink;
	}

	/**
	 * gets Display MobileFacet
	 * @return
	 */
	public DisplayEnum getDisplayMobileFacet() {
		return displayMobileFacet;
	}
	
	/**
	 * sets DisplayMobile Facet
	 * @param displayMobileFacet
	 */
	public void setDisplayMobileFacet(DisplayEnum displayMobileFacet) {
		this.displayMobileFacet = displayMobileFacet;
	}
	
	/**
	 * gets DispalyMobile Facet Remove Link
	 * @return
	 */
	public DisplayEnum getDisplayMobileFacetRemoveLink() {
		return displayMobileFacetRemoveLink;
	}
	
	/**
	 * sets displayMobileFacetRemove Link
	 * @param displayMobileFacetRemoveLink
	 */
	public void setDisplayMobileFacetRemoveLink(
			DisplayEnum displayMobileFacetRemoveLink) {
		this.displayMobileFacetRemoveLink = displayMobileFacetRemoveLink;
	}
	
}
