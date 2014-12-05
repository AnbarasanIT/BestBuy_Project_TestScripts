package com.bestbuy.search.merchandising.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bestbuy.search.merchandising.domain.common.BannerTemplateEnum;

@Entity
@Table(name = "BANNER_TEMPLATES")

/**
 * @author Kalaiselvi Jaganathan
 * Entity for Banner Template - HTML Only BANNER_HTML_TEMPLATE,BannerTemplate
 */
public class BannerTemplate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_ID", insertable = true, updatable = true,nullable=false,length=20)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TEMPLATE_TYPE", length=15)
	private BannerTemplateEnum templateType;
	
	@Column(name = "TEMPLATE_HEADER", length=15)
	private String templateHeader;
	
	@Column(name = "SKUS", length=255)
	private String sku;
	
	@Column(name = "SKU_POSITION", length=2)
	private Long skuPosition;
	
	@OneToOne
	@JoinColumn (name = "BANNER_ID",referencedColumnName="BANNER_ID") 
	private Banner banner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BannerTemplateEnum getTemplateType() {
		return templateType;
	}

	public void setTemplateType(BannerTemplateEnum templateType) {
		this.templateType = templateType;
	}

	public String getTemplateHeader() {
		return templateHeader;
	}

	public void setTemplateHeader(String templateHeader) {
		this.templateHeader = templateHeader;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public Long getSkuPosition() {
		return skuPosition;
	}

	public void setSkuPosition(Long skuPosition) {
		this.skuPosition = skuPosition;
	}
	
}
