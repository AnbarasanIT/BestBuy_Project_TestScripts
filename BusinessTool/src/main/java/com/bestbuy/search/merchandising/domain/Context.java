package com.bestbuy.search.merchandising.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEXTS")
/**
 * @author Kalaiselvi Jaganathan
 * Entity for the context category
 */
public class Context implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONTEXT_ID", insertable = true, updatable = true,nullable=false)
	private Long contextId;
	
	@Column(name = "INHERITABLE", unique = false, nullable = true, insertable = true, updatable = true, length=1)
	private Long inheritable;
	
	@OneToOne
	@JoinColumn(name="SEARCH_PROFILE_ID", referencedColumnName="SEARCH_PROFILE_ID")
	private SearchProfile searchProfile;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinTable(name = "BANNER_CONTEXTS",joinColumns = {@JoinColumn(name="CONTEXT_ID") },inverseJoinColumns = {@JoinColumn(name="BANNER_ID")})
	private Banner banners;

	@OneToMany(mappedBy="contextFacetId.context",cascade=CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)
	private List<ContextFacet> contextFacet = new ArrayList<ContextFacet>();
	
	@OneToMany(mappedBy="contextKeyword",cascade = {CascadeType.ALL},fetch = FetchType.LAZY,orphanRemoval=true)
	private List<ContextKeyword> contextKeyword = new ArrayList<ContextKeyword>();
	
	@OneToOne
	@JoinColumn(name="CATEGORY_NODE_ID", referencedColumnName="CATEGORY_NODE_ID")
	private Category categoryNode;
	
	@Column(name = "IS_ACTIVE", length=1)
	private String isActive= "Y"; 

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
	 * @return the contextId
	 */
	public Long getContextId() {
		return contextId;
	}

	/**
	 * @param To set the contextId
	 */
	public void setContextId(Long contextId) {
		this.contextId = contextId;
	}

	/**
	 * @return the inheritable
	 */
	public Long getInheritable() {
		return inheritable;
	}

	/**
	 * @param To set the inheritable
	 */
	public void setInheritable(Long inheritable) {
		this.inheritable = inheritable;
	}

	/**
	 * @return the searchProfile
	 */
	public SearchProfile getSearchProfile() {
		return searchProfile;
	}

	/**
	 * @param To set the searchProfile
	 */
	public void setSearchProfile(SearchProfile searchProfile) {
		this.searchProfile = searchProfile;
	}

	
	/**
	 * @return the contextFacet
	 */
	public List<ContextFacet> getContextFacet() {
		return contextFacet;
	}

	/**
	 * @param To set the contextFacet
	 */
	public void setContextFacet(List<ContextFacet> contextFacet) {
		this.contextFacet = contextFacet;
	}

	/**
	 * @return the contextKeyword
	 */
	public List<ContextKeyword> getContextKeyword() {
		return contextKeyword;
	}

	/**
	 * @param To set the contextKeyword
	 */
	public void setContextKeyword(List<ContextKeyword> contextKeyword) {
		this.contextKeyword = contextKeyword;
	}
	
	/**
	 * get the Banner corresponding to this context
	 * @return
	 */
	public Banner getBanners() {
		return banners;
	}
	
	/**
	 * set the banner to this context
	 * @param banners
	 */
	public void setBanners(Banner banners) {
		this.banners = banners;
	}
	
	/**
	 * get the category to this context
	 * @return
	 */
	public Category getCategoryNode() {
		return categoryNode;
	}
	
	/**
	 * set the category to this context
	 * @param categoryNode
	 */
	public void setCategoryNode(Category categoryNode) {
		this.categoryNode = categoryNode;
	}
}
