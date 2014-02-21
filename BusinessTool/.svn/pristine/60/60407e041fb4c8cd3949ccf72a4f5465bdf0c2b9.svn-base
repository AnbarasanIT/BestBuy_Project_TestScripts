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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BANNERS")
/**
 * @author Kalaiselvi Jaganathan 
 * Entity for Banners
 */
public class Banner  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANNER_ID", insertable = true, updatable = true,nullable=false,length=19)
	private Long bannerId;

	@Column(name = "BANNER_NAME", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String bannerName;
	
	@Column(name = "BANNER_TEMPLATE", unique = false, nullable = true, insertable = true, updatable = true, length=255)
	private String bannerTemplate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "BANNER_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length=10)
	private String bannerType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "BANNER_PRIORITY", unique = false, nullable = true, insertable = true, updatable = true, length=10)
	private Long bannerPriority;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "BANNER_CONTEXTS",joinColumns = {@JoinColumn(name="BANNER_ID") },inverseJoinColumns = {@JoinColumn(name="CONTEXT_ID")})
	private List <Context> contexts = new ArrayList <Context>(0);
	
	@OneToMany(mappedBy="banner", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<BannerTemplate> bannerTemplates;

	/**
	 * @return the bannerId
	 */
	public Long getBannerId() {
		return bannerId;
	}

	/**
	 * @param To set the bannerId
	 */
	public void setBannerId(Long bannerId) {
		this.bannerId = bannerId;
	}

	/**
	 * @return the bannerName
	 */
	public String getBannerName() {
		return bannerName;
	}

	/**
	 * @param To set the bannerName
	 */
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	/**
	 * @return the bannerTemplate
	 */
	public String getBannerTemplate() {
		return bannerTemplate;
	}

	/**
	 * @param To set the bannerTemplate
	 */
	public void setBannerTemplate(String bannerTemplate) {
		this.bannerTemplate = bannerTemplate;
	}

	/**
	 * @return the bannerType
	 */
	public String getBannerType() {
		return bannerType;
	}

	/**
	 * @param To set the bannerType
	 */
	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}

	/**
	 * @return the bannerPriority
	 */
	public Long getBannerPriority() {
		return bannerPriority;
	}

	/**
	 * @param To set the bannerPriority
	 */
	public void setBannerPriority(Long bannerPriority) {
		this.bannerPriority = bannerPriority;
	}

	/**
	 * @return the contexts
	 */
	public List<Context> getContexts() {
		return contexts;
	}

	/**
	 * @param To set the contexts
	 */
	public void setContexts(List<Context> contexts) {
		this.contexts = contexts;
	}

	/**
	 * gets the bannerTemplate
	 * @return
	 */
	public List<BannerTemplate> getBannerTemplates() {
		return bannerTemplates;
	}
	
	/**
	 * sets the bannerTemplate
	 * @param bannerTemplates
	 */
	public void setBannerTemplates(List<BannerTemplate> bannerTemplates) {
		this.bannerTemplates = bannerTemplates;
	}
}
