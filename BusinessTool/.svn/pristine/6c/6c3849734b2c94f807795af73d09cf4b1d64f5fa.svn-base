package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BANNER_TEMPLATE_META")
/**
 * @author Kalaiselvi Jaganathan
 * Banner Templates entity mapping
 */
public class BannerTemplateMeta implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANNER_TEMPLATE_ID", insertable = true, updatable = true,nullable=false,length=19)
	private Long bannerTemplateId;
	
	@Column(name = "BANNER_TEMPLATE_NAME", unique = true, nullable = false, insertable = true, updatable = true, length=255)
	private String bannerTemplateName;
	
	@Column(name = "BANNER_TEMPLATE_FIELD_LIST", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private String bannerTemplateFieldList;
	
	@Column(name = "BANNER_TEMPLATE_HEADER_COUNT", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private Long bannerTemplateHeaderCount;
	
	@Column(name = "BANNER_TEMPLATE_SLOT_COUNT", unique = false, nullable = false, insertable = true, updatable = true, length=255)
	private Long bannerTemplateSlotCount;

	/**
	 * @return the bannerTemplateId
	 */
	public Long getBannerTemplateId() {
		return bannerTemplateId;
	}

	/**
	 * @param To set the bannerTemplateId
	 */
	public void setBannerTemplateId(Long bannerTemplateId) {
		this.bannerTemplateId = bannerTemplateId;
	}

	/**
	 * @return the bannerTemplateName
	 */
	public String getBannerTemplateName() {
		return bannerTemplateName;
	}

	/**
	 * @param To set the bannerTemplateName
	 */
	public void setBannerTemplateName(String bannerTemplateName) {
		this.bannerTemplateName = bannerTemplateName;
	}

	/**
	 * @return the bannerTemplateFieldList
	 */
	public String getBannerTemplateFieldList() {
		return bannerTemplateFieldList;
	}

	/**
	 * @param To set the bannerTemplateFieldList
	 */
	public void setBannerTemplateFieldList(String bannerTemplateFieldList) {
		this.bannerTemplateFieldList = bannerTemplateFieldList;
	}

	/**
	 * @return the bannerTemplateSlotCount
	 */
	public Long getBannerTemplateSlotCount() {
		return bannerTemplateSlotCount;
	}

	/**
	 * @param To set the bannerTemplateSlotCount
	 */
	public void setBannerTemplateSlotCount(Long bannerTemplateSlotCount) {
		this.bannerTemplateSlotCount = bannerTemplateSlotCount;
	}
	
	/**
	 * @return the bannerTemplateHeaderCount
	 */
	public Long getBannerTemplateHeaderCount() {
		return bannerTemplateHeaderCount;
	}

	/**
	 * @param bannerTemplateHeaderCount the bannerTemplateHeaderCount to set
	 */
	public void setBannerTemplateHeaderCount(Long bannerTemplateHeaderCount) {
		this.bannerTemplateHeaderCount = bannerTemplateHeaderCount;
	}
}
