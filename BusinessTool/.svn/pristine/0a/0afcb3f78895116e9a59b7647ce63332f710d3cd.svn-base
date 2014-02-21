package com.bestbuy.search.merchandising.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class for the categoryNode as well as used by the JaxB transformation
 * for setting the CategoryId's and ITs parent Path
 * @author Lakshmi.Valluripalli
 */
@Entity
@Table(name = "CATEGORIES")
@XmlRootElement(name="Category")
public class Category {
	
	@Id
	@Column(name = "CATEGORY_NODE_ID", insertable = true, updatable = true,nullable=false,length=25)
	private String categoryNodeId;

	@Column(name = "CATEGORY_PATH", insertable = true, updatable = true,nullable=false,length=255)
	private String categoryPath;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_DATE", unique = false, nullable = true, insertable = true, updatable = true, length=7)
	private Date modifiedDate = new Date();
	
	@Column(name = "IS_ACTIVE", length=1)
	private String isActive= "Y"; 
	
	@Column(name="ALSO_FOUND", nullable = true,insertable = true, updatable = true)
	private String alsoFoundIn;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn (name = "CATEGORY_NODE_ID",referencedColumnName="CATEGORY_NODE_ID", insertable = false, updatable = false)
	private List<FacetsDisplayOrder> facetDisplayOrder = new ArrayList<FacetsDisplayOrder>();

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
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param to set the isActive 
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * gets the Unique categoryNode Id
	 * @return
	 */
	@XmlAttribute(name="id")
	public String getCategoryNodeId() {
		return categoryNodeId;
	}
	
	/**
	 * sets the unique category node from
	 * the JaxB transformation
	 * @param categoryNodeId
	 */
	public void setCategoryNodeId(String categoryNodeId) {
		this.categoryNodeId = categoryNodeId;
	}
	
	/**
	 * returns the categoryPath
	 * @return
	 */
	@XmlAttribute(name="path")
	public String getCategoryPath() {
		return categoryPath;
	}
	
	/**
	 * sets the category path from the JaxB transformation
	 * @param categoryPath
	 */
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	/**
	 * gets the date when the category is updated
	 * @return Date
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	/**
	 * sets the date when the path is updated
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getAlsoFoundIn() {
		return alsoFoundIn;
	}

	@XmlAttribute(name="alsoFoundIn")
	public void setAlsoFoundIn(String alsoFoundIn) {
		this.alsoFoundIn = alsoFoundIn;
	} 
}
