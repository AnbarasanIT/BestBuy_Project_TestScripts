
package com.bestbuy.search.merchandising.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *  Defines the Category tree entity
 *  @author Lakshmi.Valluripalli
 */
@Entity
@Table(name = "CATEGORY_TREE")
public class CategoryTree {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_ID", insertable = true, updatable = true,nullable=false,length=25)
	private Long categoryId;
	
	@Lob
	@Column(name="CATEGORY_TREE")
    private String categoryTree;
	
	@Column(name = "VERSION", insertable = true, updatable = true,nullable=false,length=25)
	private Long version;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_DATE", unique = false, nullable = true, insertable = true, updatable = true, length=7)
	private Date modifiedDate;
	
	/**
	 * gets the category tree unique ID
	 * @return Long
	 */
	public Long getCategoryId() {
		return categoryId;
	}
	
	/**
	 * sets the unique id for the category tree
	 * @param categoryId
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * gets the category tree xml
	 * @return String
	 */
	public String getCategoryTree() {
		return categoryTree;
	}

	/**
	 * setter for category tree xml
	 * @param categoryTree
	 */
	public void setCategoryTree(String categoryTree) {
		this.categoryTree = categoryTree;
	}

	/**
	 * gets the version of the category tree
	 * @return Long
	 */
	public Long getVersion() {
		return version;
	}
	
	/**
	 * setter for the category tree version
	 * @param version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	
	/**
	 * gets the date of the category tree
	 * @return Date
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	/**
	 * sets the date category tree is received from the daas
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
